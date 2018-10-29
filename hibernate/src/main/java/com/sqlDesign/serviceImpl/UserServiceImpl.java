package com.sqlDesign.serviceImpl;

import com.sqlDesign.entity.*;
import com.sqlDesign.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.Wang
 * @version 2018/10/27
 * @program hibernate
 * @description
 */
public class UserServiceImpl implements UserService {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    private void init(boolean useTransaction) {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        if(useTransaction) {
            transaction = session.beginTransaction();
        }
    }

    private void end(boolean useTransaction) {
        if (useTransaction) {
            transaction.commit();
        }
        session.close();
        sessionFactory.close();
    }

    private List<Integer> getPlanList(int cid, int month, int isUsing) {
        String relationSql = "select pid from ProductHistoryEntity where cid =: cid and month =: month and beUsing =: isUsing order by phid asc";
        Query query = session.createQuery(relationSql);
        query.setParameter("cid", cid);
        query.setParameter("month", month);
        query.setParameter("isUsing", isUsing);
        return ((org.hibernate.query.Query) query).list();
    }

    private double getFreeNum(List<Integer> planList, String type) {
        double free_num = 0;
        switch (type) {
            case "call":
                for (Integer productId:planList) {
                    ProductEntity productEntity = session.get(ProductEntity.class, productId);
                    if (productEntity.getCallId() != 1) {
                        CallEntity callEntity = session.get(CallEntity.class, productEntity.getCallId());
                        free_num += callEntity.getFreeTime();
                    }
                }
                break;
            case "flow_local":
                for (Integer planId:planList) {
                    ProductEntity productEntity = session.get(ProductEntity.class, planId);
                    int flowId = productEntity.getFlowId();
                    if (flowId != 1) {
                        FlowEntity flowEntity = session.get(FlowEntity.class, flowId);
                        free_num += flowEntity.getLocalFreeNum();
                    }
                }
                break;
            case "flow_other":
                for (Integer planId:planList) {
                    ProductEntity productEntity = session.get(ProductEntity.class, planId);
                    int flowId = productEntity.getFlowId();
                    if (flowId != 1) {
                        FlowEntity flowEntity = session.get(FlowEntity.class, flowId);
                        free_num += flowEntity.getOtherFreeNum();
                    }
                }
                break;
            case "sms":
                for (Integer planId:planList) {
                    ProductEntity productEntity = session.get(ProductEntity.class, planId);
                    if (productEntity.getSmsId() != 1) {
                        SmsEntity smsEntity = session.get(SmsEntity.class, productEntity.getSmsId());
                        free_num += smsEntity.getFreeNum();
                    }
                }
                break;
        }
        return free_num;
    }
    /**
     * 一次通话资费生成
     *
     * @param cid         客户编号
     * @param createdTime 通话开始时间
     * @param endTime     通话结束时间
     * @return double
     * @author Mr.Wang
     */
    @Override
    public double chargeOfCall(int cid, Timestamp createdTime, Timestamp endTime) {
        init(true);

        //1. 检索用了什么含有通话的套餐，得出总免费时长
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        List<Integer> planList = getPlanList(cid, month, 1);

        double free_time_all;
        double consume_time_all = 0;
        double standard = session.get(CallEntity.class, 1).getStandard();
        free_time_all = getFreeNum(planList, "call");
        //2. 统计总套餐内通话时间--通过一类“抹消超出套餐的时间”的记录来实现
        Date date = new Date();
        String timeStr = String.valueOf(year) + "/" + String.valueOf(month) + "/1 00:00:01";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            date = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp ts = new Timestamp(date.getTime());

        String callTimeSql = "from CallHistoryEntity where cid =: cid and createdTime >=: monthStart order by createdTime desc";
        Query query1 = session.createQuery(callTimeSql);
        query1.setParameter("cid", cid);
        query1.setParameter("monthStart", ts);
        List<CallHistoryEntity> callList = ((org.hibernate.query.Query) query1).list();

        long from = createdTime.getTime();
        long to = endTime.getTime();
        int minutes = (int)((to - from) / (1000 * 60));
        if (callList.size() == 0) {
            //这个月还没打过电话
            consume_time_all += minutes;
        } else {
            //以前的all_time + 现在的
            consume_time_all = callList.get(0).getAllTime() + minutes;
        }
        //3. money为超出时长计费
        double surplus = free_time_all - consume_time_all;
        double money = 0;
        if (surplus < 0) {
            //超出套餐部分
            money = -surplus * standard;
        }

        CallHistoryEntity newHistory = new CallHistoryEntity(cid, createdTime, endTime, consume_time_all, money);
        session.save(newHistory);

        end(true);
        return money;
    }

    /**
     * 一段流量资费生成
     *
     * @param cid     客户编号
     * @param num     流量数额
     * @param isLocal 是否本地
     * @return double
     * @author Mr.Wang
     */
    @Override
    public double chargeOfFlow(int cid, double num, boolean isLocal) {
        init(true);

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        List<Integer> planList = getPlanList(cid, month, 1);

        double free_local_all;
        double consume_local_all;
        double free_other_all;
        double consume_other_all;
        double local_standard = session.get(FlowEntity.class, 1).getLocalStandard();
        double other_standard = session.get(FlowEntity.class, 1).getOtherStandard();
        free_local_all = getFreeNum(planList, "free_local");
        free_other_all = getFreeNum(planList, "free_other");

        String flowNumSql = "from FlowHistoryEntity where cid =: cid and month =: month order by fhid desc";
        Query query1 = session.createQuery(flowNumSql);
        query1.setParameter("cid", cid);
        query1.setParameter("month", month);
        List<FlowHistoryEntity> flowList = ((org.hibernate.query.Query) query1).list();
        double consume_local_once = isLocal ? num : 0;
        double consume_other_once = isLocal ? 0 : num;
        if (flowList.size() == 0) {
            consume_local_all = consume_local_once;
            consume_other_all = consume_other_once;
        } else {
            consume_local_all = flowList.get(0).getConsumeLocalAll() + consume_local_once;
            consume_other_all = flowList.get(0).getConsumeOtherAll() + consume_other_once;
        }

        double surplus_local = free_local_all - consume_local_all;
        double surplus_other = free_other_all - consume_other_all;
        double money = 0;
        if (surplus_local < 0) {
            money = -surplus_local * local_standard;
        }
        if (surplus_other < 0) {
            money += -surplus_other * other_standard;
        }

        FlowHistoryEntity flowHistoryEntity = new FlowHistoryEntity(cid, month, consume_local_all, consume_other_all, money);
        session.save(flowHistoryEntity);

        end(true);
        return money;
    }

    /**
     * 一次短信资费生成
     * @param cid 客户编号
     * @param num 短信条数
     * @return double
     * @author Mr.Wang
     */
    @Override
    public double chargeOfSms(int cid, int num) {
        init(true);

        //订购了哪些套餐
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        List<Integer> planList = getPlanList(cid, month, 1);

        int free_num;
        int consume_all;
        double standard = session.get(SmsEntity.class, 1).getStandard();
        free_num = (int)getFreeNum(planList, "sms");

        //查阅以往消费
        //在新订套餐里把consume_all变为和原有的免费额度等同？——如果已经超过免费额度的话。money最好重新变为单次
        String smsNumSql = "from SmsHistoryEntity where cid =: cid and month =: month order by shid desc";
        Query query1 = session.createQuery(smsNumSql);
        query1.setParameter("cid", cid);
        query1.setParameter("month", month);
        List<SmsHistoryEntity> smsList = ((org.hibernate.query.Query) query1).list();
        if (smsList.size() == 0) {
            consume_all = num;
        } else {
            consume_all = smsList.get(0).getSendNumAll() + num;
        }
        double surplus = free_num - consume_all;
        double money = 0;
        if (surplus < 0) {
            money = -surplus * standard;
        }

        SmsHistoryEntity smsHistoryEntity = new SmsHistoryEntity(cid, month, consume_all, money);
        session.save(smsHistoryEntity);

        end(true);
        return money;
    }

    /**
     * 月账单
     * 不含基准费
     * @param cid   客户编号
     * @param month 月份
     * @return ArrayList<String>
     * @author Mr.Wang
     */
    @Override
    public ArrayList<String> chargeOfMonth(int cid, int month) {
        init(false);
        ArrayList<String> result = new ArrayList<>();
        List<Integer> planList = getPlanList(cid, month, 1);
        for (Integer pid:planList) {
            ProductEntity productEntity = session.get(ProductEntity.class, pid);
            String planStr = productEntity.getPname() + " " + String.valueOf(productEntity.getBase());
            result.add(planStr);
        }

        String callSql = "from CallHistoryEntity where cid =: cid and createdTime >=: monthStart order by createdTime desc";
        Query query = session.createQuery(callSql);
        List<CallHistoryEntity> calls = ((org.hibernate.query.Query) query).list();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (CallHistoryEntity call:calls) {
            //去掉新订购套餐记录
            if (!call.getCreatedTime().equals(call.getEndTime())) {
                String callHistory = sdf.format(call.getCreatedTime()) + "->"
                        + sdf.format(call.getEndTime()) + " "
                        + String.valueOf(call.getMoney());
                result.add(callHistory);
            }
        }

        String smsSql = "from SmsHistoryEntity where cid =: cid and month =: month order by shid desc";
        Query query1 = session.createQuery(smsSql);
        List<SmsHistoryEntity> smss = ((org.hibernate.query.Query) query1).list();
        double allSmsMoney = 0;
        for (SmsHistoryEntity sms:smss) {
            if (sms.getMoney() > 0) {
                allSmsMoney += sms.getMoney();
            }
        }
        result.add("messages: " + String.valueOf(allSmsMoney));

        String flowSql = "from FlowHistoryEntity where cid =: cid and month =: month order by fhid desc";
        Query query2 = session.createQuery(flowSql);
        List<FlowHistoryEntity> flows = ((org.hibernate.query.Query) query2).list();
        double allFlowMoney = 0;
        for (FlowHistoryEntity flow:flows) {
            if (flow.getMoney() > 0) {
                allFlowMoney += flow.getMoney();
            }
        }
        result.add("data: " + String.valueOf(allFlowMoney));
        end(false);
        return result;
    }
}
