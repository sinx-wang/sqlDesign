package com.sqlDesign.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Mr.Wang
 * @version 2018/10/27
 * @program hibernate
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sms_history", schema = "hibernate")
public class SmsHistoryEntity {
    private int shid;
    private int cid;
    private int pid;
    private int sendNum;
    private Integer freeNum;
    private Double money;

    public SmsHistoryEntity(int cid, int pid, int sendNum, int freeNum, double money) {
        this.cid = cid;
        this.pid = pid;
        this.sendNum = sendNum;
        this.freeNum = freeNum;
        this.money = money;
    }

    @Id
    @Column(name = "shid")
    public int getShid() {
        return shid;
    }

    public void setShid(int shid) {
        this.shid = shid;
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
    @Column(name = "send_num")
    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }

    @Basic
    @Column(name = "free_num")
    public Integer getFreeNum() {
        return freeNum;
    }

    public void setFreeNum(Integer freeNum) {
        this.freeNum = freeNum;
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

        SmsHistoryEntity that = (SmsHistoryEntity) o;

        if (shid != that.shid) return false;
        if (cid != that.cid) return false;
        if (pid != that.pid) return false;
        if (sendNum != that.sendNum) return false;
        if (freeNum != null ? !freeNum.equals(that.freeNum) : that.freeNum != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shid;
        result = 31 * result + cid;
        result = 31 * result + pid;
        result = 31 * result + sendNum;
        result = 31 * result + (freeNum != null ? freeNum.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
