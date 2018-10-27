package com.sqlDesign.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mr.Wang
 * @version 2018/10/27
 * @program hibernate
 * @description
 */
public class AddData {
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
    public void addCallEntityData() {
        CallEntity callEntity0 = new CallEntity( 0.0, 0.5);
        session.save(callEntity0);
//        transaction.commit();
        CallEntity callEntity1 = new CallEntity(100.0, 0.5);
        session.save(callEntity1);
//        transaction.commit();
        CallEntity callEntity2 = new CallEntity(50.0, 0.25);
        session.save(callEntity2);
//        transaction.commit();
    }

    @Test
    public void addSmsEntityData() {
        SmsEntity smsEntity0 = new SmsEntity(0,0.1);
        session.save(smsEntity0);
        SmsEntity smsEntity1 = new SmsEntity(200, 0.1);
        session.save(smsEntity1);
        SmsEntity smsEntity2 = new SmsEntity(100, 0.1);
        session.save(smsEntity2);
    }

    @Test
    public void addFlowEntityData() {
        FlowEntity flowEntity0 = new FlowEntity(0, 0, 2, 5);
        session.save(flowEntity0);
        FlowEntity flowEntity1 = new FlowEntity(2048, 0, 3, 3);
        session.save(flowEntity1);
        FlowEntity flowEntity2 = new FlowEntity(0, 2048, 3, 3);
        session.save(flowEntity2);
        FlowEntity flowEntity3 = new FlowEntity(5120, 4096, 3, 3);
        session.save(flowEntity3);
    }

    @Test
    public void addProductEntity() {
        ProductEntity productEntity0 = new ProductEntity( "base_plan", 1, 1, 1, 0.0);
        session.save(productEntity0);
        ProductEntity productEntity1 = new ProductEntity( "call_plan", 2, 1, 1, 20.0);
        session.save(productEntity1);
        ProductEntity productEntity2 = new ProductEntity("sms_plan", 1, 2, 1, 10.0);
        session.save(productEntity2);
        ProductEntity productEntity3 = new ProductEntity("local_data_plan", 1, 1, 2, 20.0);
        session.save(productEntity3);
        ProductEntity productEntity4 = new ProductEntity( "other_data_plan", 1, 1, 3, 30.0);
        session.save(productEntity4);
        ProductEntity productEntity5 = new ProductEntity( "mix_plan", 3, 3, 4, 50.0);
        session.save(productEntity5);
    }

    @Test
    public void addCustomerEntity() {
        CustomerEntity customerEntity0 = new CustomerEntity(1, "ZhangSan");
        session.save(customerEntity0);
        CustomerEntity customerEntity1 = new CustomerEntity(2, "LiSi");
        session.save(customerEntity1);
        CustomerEntity customerEntity2 = new CustomerEntity(3, "WangWu");
        session.save(customerEntity2);
    }

    @Test
    public void addProductHistoryEntity() {
        //取消套餐就是找到对应的cid和pid然后把cid设为1
        ProductHistoryEntity productHistoryEntity0 = new ProductHistoryEntity(1, 2, 2, 10, 1);
        session.save(productHistoryEntity0);
        ProductHistoryEntity productHistoryEntity1 = new ProductHistoryEntity(1, 3, 1, 10, 1);
        session.save(productHistoryEntity1);
        ProductHistoryEntity productHistoryEntity2 = new ProductHistoryEntity(1, 4, 4, 10, 1);
        session.save(productHistoryEntity2);
        ProductHistoryEntity productHistoryEntity3 = new ProductHistoryEntity(2, 6, 6, 10, 1);
        session.save(productHistoryEntity3);
        ProductHistoryEntity productHistoryEntity4 = new ProductHistoryEntity(2, 4, 6, 10, 1);
        session.save(productHistoryEntity4);
        ProductHistoryEntity productHistoryEntity5 = new ProductHistoryEntity(3, 6, 6, 10, 1);
        session.save(productHistoryEntity5);
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
