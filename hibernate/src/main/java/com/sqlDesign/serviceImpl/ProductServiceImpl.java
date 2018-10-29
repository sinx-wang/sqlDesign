package com.sqlDesign.serviceImpl;

import com.sqlDesign.entity.*;
import com.sqlDesign.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

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

    private ArrayList<ProductEntity> getProducts(List<ProductHistoryEntity> list) {
        ArrayList<ProductEntity> products = new ArrayList<>();
        if (list.size() > 0) {
            for(ProductHistoryEntity entity:list) {
                int pid = entity.getPid();
                //基本资费等于没有套餐
                if (pid != 1) {
                    ProductEntity product = session.get(ProductEntity.class, pid);
                    products.add(product);
                }
            }
        }
        return products;
    }

    /**
     * 套餐的查询（包括历史记录）
     * @param cid 客户编号
     * @return ArrayList<ProductEntity>
     * @author Mr.Wang
     */
    @Override
    public ArrayList<ProductEntity> queryAllProducts(int cid) {
        init(false);

        //根据时间由近到远查询用户订购过的套餐
        String relationSql = "from ProductHistoryEntity where cid =: cid order by phid desc";
        Query query = session.createQuery(relationSql);
        query.setParameter("cid", cid);
        List<ProductHistoryEntity> list = ((org.hibernate.query.Query) query).list();
        ArrayList<ProductEntity> products = getProducts(list);
        end(false);
        return products;
    }

    /**
     * 查询正在使用的套餐
     *
     * @param cid 客户编号
     * @return ArrayList<ProductEntity>
     * @author Mr.Wang
     */
    @Override
    public ArrayList<ProductEntity> queryUsingProducts(int cid) {
        init(false);

        String relationSql = "from ProductHistoryEntity where cid =: cid and beUsing = 1 order by phid desc";
        Query query = session.createQuery(relationSql);
        query.setParameter("cid", cid);
        List<ProductHistoryEntity> list = ((org.hibernate.query.Query) query).list();
        ArrayList<ProductEntity> products = getProducts(list);

        end(false);
        return products;
    }

    /**
     * 查询下月的套餐
     *
     * @param cid 客户编号
     * @return ArrayList<ProductEntity>
     * @author Mr.Wang
     */
    @Override
    public ArrayList<ProductEntity> queryNextProducts(int cid) {
        init(false);

        String relationSql = "from ProductHistoryEntity where cid =: cid and beUsing = 1 order by phid desc";
        Query query = session.createQuery(relationSql);
        query.setParameter("cid", cid);
        List<ProductHistoryEntity> list = ((org.hibernate.query.Query) query).list();
        ArrayList<ProductEntity> products = new ArrayList<>();
        if (list.size() > 0) {
            for(ProductHistoryEntity entity:list) {
                int nextPid = entity.getpNextId();
                //基本资费等于没有套餐
                if (nextPid != 1) {
                    ProductEntity product = session.get(ProductEntity.class, nextPid);
                    products.add(product);
                }
            }
        }

        end(false);
        return products;
    }

    /**
     * 订购套餐立即生效
     * 默认创建用户的时候已经创建了基本资费
     * TODO: 在新订套餐里把consume_all变为和原有的免费额度等同，free_all变为总——如果已经超过免费额度的话。money变为单次
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return boolean
     * @author Mr.Wang
     */
    @Override
    public boolean orderProductNow(int cid, int pid) {
        init(true);

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        ProductEntity product = session.get(ProductEntity.class, pid);
        if (product.getCallId() != 1) {
            changeConsumeOfCall(cid);
        }
        if (product.getFlowId() != 1) {
            changeConsumeOfFlow(cid);
        }
        if (product.getSmsId() != 1) {
            changeConsumeOfSms(cid);
        }
        ProductHistoryEntity productHistoryEntity = new ProductHistoryEntity(cid, pid, pid, month, 1);
        session.save(productHistoryEntity);

        end(true);
        return true;
    }

    /**
     * 订购套餐下月生效
     * @param cid 客户编号
     * @param pNextId 套餐编号
     * @return boolean
     * @author Mr.Wang
     */
    @Override
    public boolean orderProductNext(int cid, int pNextId) {
        init(true);

        Calendar cal = Calendar.getInstance();
        //仍然是当前月份
        int month = cal.get(Calendar.MONTH) + 1;
        ProductHistoryEntity productHistoryEntity = new ProductHistoryEntity(cid, 1, pNextId, month, 1);
        session.save(productHistoryEntity);

        end(true);
        return true;
    }

    /**
     * 退订套餐立即生效
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return boolean
     * @author Mr.Wang
     */
    @Override
    public boolean cancelProductNow(int cid, int pid) {
        init(true);

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String relationSql = "update ProductHistoryEntity p set p.beUsing =: now where p.cid =: cid and p.pid =: pid and p.month =: month and p.beUsing =: beUsing";
        Query query = session.createQuery(relationSql);
        query.setParameter("now", 0);
        query.setParameter("cid", cid);
        query.setParameter("pid", pid);
        query.setParameter("month", month);
        query.setParameter("beUsing", 1);

        query.executeUpdate();
        end(true);
        return true;
    }

    /**
     * 退订套餐下月生效
     *
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return boolean
     * @author Mr.Wang
     */
    @Override
    public boolean cancelProductNext(int cid, int pid) {
        init(true);

        //仍然是当前月份
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String relationSql = "update ProductHistoryEntity p set p.pNextId =: now where p.cid =: cid and p.pNextId =: pid and p.month =: month";
        Query query = session.createQuery(relationSql);
        query.setParameter("now", 1);
        query.setParameter("cid", cid);
        query.setParameter("pid", pid);
        query.setParameter("month", month);

        query.executeUpdate();
        end(true);
        return true;
    }

    private void changeConsumeOfCall(int cid) {
        //找到之前的所有套餐的免费时长之和
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String relationSql = "select pid from ProductHistoryEntity where cid =: cid and month =: month and beUsing =: isUsing order by phid asc";
        Query query = session.createQuery(relationSql);
        query.setParameter("cid", cid);
        query.setParameter("month", month);
        query.setParameter("isUsing", 1);
        List<Integer> planList = ((org.hibernate.query.Query) query).list();

        double free_time_all = 0;
        for (Integer productId:planList) {
            ProductEntity productEntity = session.get(ProductEntity.class, productId);
            int callId = productEntity.getCallId();
            if (callId != 1) {
                CallEntity callEntity = session.get(CallEntity.class, callId);
                free_time_all += callEntity.getFreeTime();
            }
        }
        //找到之前的所有用过的套餐时长之和并判断
        if (free_time_all > 0) {
            Date date = new Date();
            String timeStr = String.valueOf(year) + "/" + String.valueOf(month) + "/1 0:0:0";
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
            if (callList.size() > 0) {
                if (callList.get(0).getAllTime() > free_time_all) {
                    //新增记录
                    CallHistoryEntity callHistoryEntity = new CallHistoryEntity(cid, free_time_all);
                    session.save(callHistoryEntity);
                }
            }
        }
    }

    private void changeConsumeOfFlow(int cid) {
        //找到之前的所有套餐的免费流量之和
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String relationSql = "select pid from ProductHistoryEntity where cid =: cid and month =: month and beUsing =: isUsing order by phid asc";
        Query query = session.createQuery(relationSql);
        query.setParameter("cid", cid);
        query.setParameter("month", month);
        query.setParameter("isUsing", 1);
        List<Integer> planList = ((org.hibernate.query.Query) query).list();

        double free_local_all = 0;
        double free_other_all = 0;
        for (Integer planId:planList) {
            ProductEntity productEntity = session.get(ProductEntity.class, planId);
            int flowId = productEntity.getFlowId();
            if (flowId != 1) {
                FlowEntity flowEntity = session.get(FlowEntity.class, flowId);
                free_local_all += flowEntity.getLocalFreeNum();
                free_other_all += flowEntity.getOtherFreeNum();
            }
        }

        //找到之前的所有用过的套餐时长之和并判断
        if (free_local_all > 0 || free_other_all > 0) {
            String flowNumSql = "from FlowHistoryEntity where cid =: cid and month =: month order by fhid desc";
            Query query1 = session.createQuery(flowNumSql);
            query1.setParameter("cid", cid);
            query1.setParameter("month", month);
            List<FlowHistoryEntity> flowList = ((org.hibernate.query.Query) query1).list();
            if (flowList.size() > 0) {
                double consume_local = flowList.get(0).getConsumeLocalAll();
                double consume_other = flowList.get(0).getConsumeOtherAll();
                if (consume_local > free_local_all) {
                    consume_local = free_local_all;
                }
                if (consume_other > free_other_all) {
                    consume_other = free_other_all;
                }
                FlowHistoryEntity flowHistoryEntity = new FlowHistoryEntity(cid, month, consume_local, consume_other, -1);
                session.save(flowHistoryEntity);
            }
        }
    }

    private void changeConsumeOfSms(int cid) {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String relationSql = "select pid from ProductHistoryEntity where cid =: cid and month =: month and beUsing =: isUsing order by phid asc";
        Query query = session.createQuery(relationSql);
        query.setParameter("cid", cid);
        query.setParameter("month", month);
        query.setParameter("isUsing", 1);
        List<Integer> planList = ((org.hibernate.query.Query) query).list();

        int free_num = 0;
        for (Integer planId:planList) {
            ProductEntity productEntity = session.get(ProductEntity.class, planId);
            int smsId = productEntity.getSmsId();
            if (smsId != 1) {
                SmsEntity smsEntity = session.get(SmsEntity.class, smsId);
                free_num += smsEntity.getFreeNum();
            }
        }

        if (free_num > 0) {
            String smsNumSql = "from SmsHistoryEntity where cid =: cid and month =: month order by shid desc";
            Query query1 = session.createQuery(smsNumSql);
            query1.setParameter("cid", cid);
            query1.setParameter("month", month);
            List<SmsHistoryEntity> smsList = ((org.hibernate.query.Query) query1).list();
            if (smsList.size() > 0) {
                if (smsList.get(0).getSendNumAll() > free_num) {
                    SmsHistoryEntity smsHistoryEntity = new SmsHistoryEntity(cid, month, free_num, -1);
                    session.save(smsHistoryEntity);
                }
            }
        }
    }

}
