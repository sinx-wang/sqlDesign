package com.sqlDesign.serviceImpl;

import com.sqlDesign.service.UserService;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Mr.Wang
 * @version 2018/10/27
 * @program hibernate
 * @description
 */
public class UserServiceImpl implements UserService {
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
        return 0;
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
        return 0;
    }

    /**
     * 月账单
     *
     * @param cid   客户编号
     * @param month 月份
     * @return double
     * @author Mr.Wang
     */
    @Override
    public double chargeOfMonth(int cid, int month) {
        return 0;
    }
}
