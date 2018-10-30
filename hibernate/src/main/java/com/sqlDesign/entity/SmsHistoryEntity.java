package com.sqlDesign.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Mr.Wang
 * @version 2018/10/28
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
    private int month;
    private int sendNumAll;
    private Double money;
    private Double moneyThisTime;

    public SmsHistoryEntity(int cid, int month, int sendNumAll, double money, double moneyThisTime) {
        this.cid = cid;
        this.month = month;
        this.sendNumAll = sendNumAll;
        this.money = money;
        this.moneyThisTime = moneyThisTime;
    }

    @Id
    @Column(name = "shid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "month")
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Basic
    @Column(name = "send_num_all")
    public int getSendNumAll() {
        return sendNumAll;
    }

    public void setSendNumAll(int sendNumAll) {
        this.sendNumAll = sendNumAll;
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
        if (month != that.month) return false;
        if (sendNumAll != that.sendNumAll) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;
        if (moneyThisTime != null ? !moneyThisTime.equals(that.moneyThisTime) : that.moneyThisTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shid;
        result = 31 * result + cid;
        result = 31 * result + month;
        result = 31 * result + sendNumAll;
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (moneyThisTime != null ? moneyThisTime.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "money_this_time")
    public Double getMoneyThisTime() {
        return moneyThisTime;
    }

    public void setMoneyThisTime(Double moneyThisTime) {
        this.moneyThisTime = moneyThisTime;
    }
}
