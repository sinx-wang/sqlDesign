package com.sqlDesign.dao;

import lombok.AllArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Mr.Wang
 * @version 2018/10/26
 * @program hibernate
 * @description
 */
@AllArgsConstructor
@Entity
@Table(name = "sms_history", schema = "hibernate", catalog = "")
public class SmsHistoryEntity {
    private int cid;
    private Integer allNum;
    private Integer freeNum;
    private Double money;

    @Basic
    @Column(name = "cid")
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "all_num")
    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
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

        if (cid != that.cid) return false;
        if (allNum != null ? !allNum.equals(that.allNum) : that.allNum != null) return false;
        if (freeNum != null ? !freeNum.equals(that.freeNum) : that.freeNum != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cid;
        result = 31 * result + (allNum != null ? allNum.hashCode() : 0);
        result = 31 * result + (freeNum != null ? freeNum.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
