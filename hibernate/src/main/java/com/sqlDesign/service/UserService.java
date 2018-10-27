package com.sqlDesign.service;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Mr.Wang
 * @version 2018/10/27
 * @program hibernate
 * @description 用户资费生成（含月账单）
 */
public interface UserService {

    /**
     * 一次通话资费生成
     * @author Mr.Wang
     * @param cid 客户编号
     * @param createdTime 通话开始时间
     * @param endTime 通话结束时间
     * @return double
     */
    public double chargeOfCall(int cid, Timestamp createdTime, Timestamp endTime);

    /**
     * 一段流量资费生成
     * @author Mr.Wang
     * @param cid 客户编号
     * @param num 流量数额
     * @param isLocal 是否本地
     * @return double
     */
    public double chargeOfFlow(int cid, double num, boolean isLocal);

    /**
     * 月账单
     * @author Mr.Wang
     * @param cid 客户编号
     * @param month 月份
     * @return double
     */
    public double chargeOfMonth(int cid, int month);
}
