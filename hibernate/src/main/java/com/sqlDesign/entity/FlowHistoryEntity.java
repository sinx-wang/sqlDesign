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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flow_history", schema = "hibernate")
public class FlowHistoryEntity {
    private int fhid;
    private int cid;
    private int month;
    private double consumeLocalAll;
    private double consumeOtherAll;
    private Double money;

    public FlowHistoryEntity(int cid, int month, double consumeLocalAll, double consumeOtherAll, double money) {
        this.cid = cid;
        this.month = month;
        this.consumeLocalAll = consumeLocalAll;
        this.consumeOtherAll = consumeOtherAll;
        this.money = money;
    }

    @Id
    @Column(name = "fhid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getFhid() {
        return fhid;
    }

    public void setFhid(int fhid) {
        this.fhid = fhid;
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
    @Column(name = "consume_local_all")
    public double getConsumeLocalAll() {
        return consumeLocalAll;
    }

    public void setConsumeLocalAll(double consumeLocalAll) {
        this.consumeLocalAll = consumeLocalAll;
    }

    @Basic
    @Column(name = "consume_other_all")
    public double getConsumeOtherAll() {
        return consumeOtherAll;
    }

    public void setConsumeOtherAll(double consumeOtherAll) {
        this.consumeOtherAll = consumeOtherAll;
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

        FlowHistoryEntity that = (FlowHistoryEntity) o;

        if (fhid != that.fhid) return false;
        if (cid != that.cid) return false;
        if (month != that.month) return false;
        if (Double.compare(that.consumeLocalAll, consumeLocalAll) != 0) return false;
        if (Double.compare(that.consumeOtherAll, consumeOtherAll) != 0) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = fhid;
        result = 31 * result + cid;
        result = 31 * result + month;
        temp = Double.doubleToLongBits(consumeLocalAll);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(consumeOtherAll);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
