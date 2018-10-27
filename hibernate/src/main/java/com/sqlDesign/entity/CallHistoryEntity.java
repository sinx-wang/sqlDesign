package com.sqlDesign.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Mr.Wang
 * @version 2018/10/27
 * @program hibernate
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "call_history", schema = "hibernate")
public class CallHistoryEntity {
    private int chid;
    private int cid;
    private int pid;
    private Timestamp createdTime;
    private Timestamp endTime;
    private Double freeTime;
    private Double money;

    public CallHistoryEntity(int cid, int pid, Timestamp createdTime, Timestamp endTime, double freeTime, double money) {
        this.cid = cid;
        this.pid = pid;
        this.createdTime = createdTime;
        this.endTime = endTime;
        this.freeTime = freeTime;
        this.money = money;
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
    @Column(name = "pid")
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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
    @Column(name = "free_time")
    public Double getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(Double freeTime) {
        this.freeTime = freeTime;
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
        if (pid != that.pid) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (freeTime != null ? !freeTime.equals(that.freeTime) : that.freeTime != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = chid;
        result = 31 * result + cid;
        result = 31 * result + pid;
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (freeTime != null ? freeTime.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
