package com.sqlDesign.serviceImpl;

import com.sqlDesign.entity.ProductEntity;
import com.sqlDesign.entity.ProductHistoryEntity;
import com.sqlDesign.service.ProductService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.ArrayList;
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

    /**
     * 套餐的查询（包括历史记录）
     * @param cid 客户编号
     * @return ArrayList<ProductEntity>
     * @author Mr.Wang
     */
    @Override
    public ArrayList<ProductEntity> queryProducts(int cid) {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();

        //根据时间由近到远查询用户订购过的套餐
        String relationSql = "from ProductHistoryEntity where cid =: cid order by phid desc";
        Query query = session.createQuery(relationSql);
        query.setParameter("cid", cid);
        List<ProductHistoryEntity> list = ((org.hibernate.query.Query) query).list();
        ArrayList<ProductEntity> products = new ArrayList<>();
        if (list.size() > 0) {
            int pid = 1;
            for(ProductHistoryEntity entity:list) {
                pid = entity.getPid();
                //基本资费等于没有套餐
                if (pid != 1) {
                    ProductEntity product = session.get(ProductEntity.class, pid);
                    products.add(product);
                }
            }
        }
        session.close();
        sessionFactory.close();
        return products;
    }

    /**
     * 订购套餐立即生效
     *
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return boolean
     * @author Mr.Wang
     */
    @Override
    public boolean orderProductNow(int cid, int pid) {
        
        return false;
    }

    /**
     * 订购套餐下月生效
     *
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return boolean
     * @author Mr.Wang
     */
    @Override
    public boolean orderProductNext(int cid, int pid) {
        return false;
    }

    /**
     * 退订套餐立即生效
     *
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return boolean
     * @author Mr.Wang
     */
    @Override
    public boolean cancelProductNow(int cid, int pid) {
        return false;
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
        return false;
    }
}
