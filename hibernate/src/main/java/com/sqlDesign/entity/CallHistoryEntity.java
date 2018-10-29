package com.sqlDesign.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Mr.Wang
 * @version 2018/10/28
 * @program hibernate
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "call_history", schema = "hibernate")
public class CallHistoryEntity {
    //设计一类记录，create, end都是新订套餐时间，allTime为旧套餐满额
    private int chid;
    private int cid;
    private Timestamp createdTime;
    private Timestamp endTime;
    private double allTime;
    private Double money;

    public CallHistoryEntity(int cid, Timestamp createdTime, Timestamp endTime, double allTime, double money) {
        this.cid = cid;
        this.createdTime = createdTime;
        this.endTime = endTime;
        this.allTime = allTime;
        this.money = money;
    }

    public CallHistoryEntity(int cid, double allTime) {
        this.cid = cid;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.createdTime = ts;
        this.endTime = ts;
        this.allTime = allTime;
        this.money = 0.0;
    }

    @Id
    @Column(name = "chid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getChid() {
        return chid;
    }

    public void setChid(int chid) {
        this.chid = chid;
    }

    @Basic
    @Column(name = "cid")
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "created_time")
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "all_time")
    public double getAllTime() {
        return allTime;
    }

    public void setAllTime(double allTime) {
        this.allTime = allTime;
    }

    @Basic
    @Column(name = "money")
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CallHistoryEntity that = (CallHistoryEntity) o;

        if (chid != that.chid) return false;
        if (cid != that.cid) return false;
        if (Double.compare(that.allTime, allTime) != 0) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = chid;
        result = 31 * result + cid;
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        temp = Double.doubleToLongBits(allTime);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
