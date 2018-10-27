package com.sqlDesign.serviceImpl;

import com.sqlDesign.entity.ProductEntity;
import com.sqlDesign.entity.ProductHistoryEntity;
import com.sqlDesign.service.ProductService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
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

    /**
     * 套餐的查询（包括历史记录）
     * @param cid 客户编号
     * @return ArrayList<ProductEntity>
     * @author Mr.Wang
     */
    @Override
    public ArrayList<ProductEntity> queryProducts(int cid) {
        init(false);

        //根据时间由近到远查询用户订购过的套餐
        String relationSql = "from ProductHistoryEntity where cid =: cid order by phid desc";
        Query query = session.createQuery(relationSql);
        query.setParameter("cid", cid);
        List<ProductHistoryEntity> list = ((org.hibernate.query.Query) query).list();
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
        end(false);
        return products;
    }

    /**
     * 订购套餐立即生效
     * 默认创建用户的时候已经创建了基本资费
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
        ProductHistoryEntity productHistoryEntity = new ProductHistoryEntity(cid, 1, pNextId, month, 0);
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
}
