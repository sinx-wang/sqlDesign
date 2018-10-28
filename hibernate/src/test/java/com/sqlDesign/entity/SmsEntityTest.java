package com.sqlDesign.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * @author Mr.Wang
 * @version 2018/10/27
 * @program hibernate
 * @description 测试
 */
public class SmsEntityTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @Test
    public void testSms() {
//        SmsEntity smsEntity = new SmsEntity(100, 0.1);
//        session.save(smsEntity);
//        Calendar cal = Calendar.getInstance();
//        int month = cal.get(Calendar.MONTH) + 1;
//        System.out.println(month);

    }

    @Test
    public void queryPlan() {
        String relationSql = "from ProductHistoryEntity where cid =: cid order by phid desc";
        Query query = session.createQuery(relationSql);
        query.setParameter("cid", 1);
        List<ProductHistoryEntity> list = ((org.hibernate.query.Query) query).list();
        int pid = 1;
        ArrayList<ProductEntity> products = new ArrayList<>();
        for(ProductHistoryEntity entity:list) {
            pid = entity.getPid();
            ProductEntity product = session.get(ProductEntity.class, pid);
            products.add(product);
        }
        for (ProductEntity productEntity:products) {
            System.out.println(productEntity.getPname());
        }
    }

    @Test
    public void queryCustomer() {
//        String relationSql = "select pNextId from ProductHistoryEntity where cid =: cid order by phid desc";
//        Query query = session.createQuery(relationSql);
//        query.setParameter("cid", 1);
//        List<Integer> list = ((org.hibernate.query.Query) query).list();
//
//        Calendar cal = Calendar.getInstance();
//        int month = cal.get(Calendar.MONTH) + 1;
//        int pNextId = 0;
//        pNextId = list.get(0);
//        System.out.println(pNextId);

//        Calendar cal = Calendar.getInstance();
//        int month = cal.get(Calendar.MONTH) + 1;
//        String relationSql = "update ProductHistoryEntity p set p.beUsing =: now where p.cid =: cid and p.pid =: pid and p.month =: month and p.beUsing =: beUsing";
//        Query query = session.createQuery(relationSql);
//        query.setParameter("now", 1);
//        query.setParameter("cid", 1);
//        query.setParameter("pid", 2);
//        query.setParameter("month", month);
//        query.setParameter("beUsing", 0);
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String relationSql = "update ProductHistoryEntity p set p.pNextId =: now where p.cid =: cid and p.pNextId =: pid and p.month =: month";
        Query query = session.createQuery(relationSql);
        query.setParameter("now", 6);
        query.setParameter("cid", 2);
        query.setParameter("pid", 1);
        query.setParameter("month", month);

        query.executeUpdate();
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
