package com.sqlDesign.service;

import com.sqlDesign.entity.ProductEntity;

import java.util.ArrayList;

/**
 * @author Mr.Wang
 * @version 2018/10/27
 * @program hibernate
 * @description 对某个用户进行套餐的查询（包括历史记录）、订购、退订（考虑立即生效和次月生效）操作
 */
public interface ProductService {

    /**
     * 所有套餐的查询（包括历史记录）
     * @author Mr.Wang
     * @param cid 客户编号
     * @return ArrayList<ProductEntity>
     */
    public ArrayList<ProductEntity> queryAllProducts(int cid);

    /**
     * 查询正在使用的套餐
     * @author Mr.Wang
     * @param cid 客户编号
     * @return ArrayList<ProductEntity>
     */
    public ArrayList<ProductEntity> queryUsingProducts(int cid);

    /**
     * 查询下月的套餐
     * @author Mr.Wang
     * @param cid 客户编号
     * @return ArrayList<ProductEntity>
     */
    public ArrayList<ProductEntity> queryNextProducts(int cid);

    /**
     * 订购套餐立即生效
     * @author Mr.Wang
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return boolean
     */
    public boolean orderProductNow(int cid, int pid);

    /**
     * 订购套餐下月生效
     * @author Mr.Wang
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return boolean
     */
    public boolean orderProductNext(int cid, int pid);

    /**
     * 退订套餐立即生效
     * @author Mr.Wang
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return boolean
     */
    public boolean cancelProductNow(int cid, int pid);

    /**
     * 退订套餐下月生效
     * @author Mr.Wang
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return boolean
     */
    public boolean cancelProductNext(int cid, int pid);
}
