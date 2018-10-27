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
@Table(name = "flow_history", schema = "hibernate")
public class FlowHistoryEntity {
    private int fhid;
    private int cid;
    private int pid;
    private double consumeLocal;
    private double consumeOther;
    private Double freeLocal;
    private Double freeOther;
    private Double money;

    public FlowHistoryEntity(int cid, int pid, double consumeLocal, double consumeOther, double freeLocal, double freeOther, double money) {
        this.cid = cid;
        this.pid = pid;
        this.consumeLocal = consumeLocal;
        this.consumeOther = consumeOther;
        this.freeLocal = freeLocal;
        this.freeOther = freeOther;
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
    @Column(name = "pid")
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "consume_local")
    public double getConsumeLocal() {
        return consumeLocal;
    }

    public void setConsumeLocal(double consumeLocal) {
        this.consumeLocal = consumeLocal;
    }

    @Basic
    @Column(name = "consume_other")
    public double getConsumeOther() {
        return consumeOther;
    }

    public void setConsumeOther(double consumeOther) {
        this.consumeOther = consumeOther;
    }

    @Basic
    @Column(name = "free_local")
    public Double getFreeLocal() {
        return freeLocal;
    }

    public void setFreeLocal(Double freeLocal) {
        this.freeLocal = freeLocal;
    }

    @Basic
    @Column(name = "free_other")
    public Double getFreeOther() {
        return freeOther;
    }

    public void setFreeOther(Double freeOther) {
        this.freeOther = freeOther;
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
        if (pid != that.pid) return false;
        if (Double.compare(that.consumeLocal, consumeLocal) != 0) return false;
        if (Double.compare(that.consumeOther, consumeOther) != 0) return false;
        if (freeLocal != null ? !freeLocal.equals(that.freeLocal) : that.freeLocal != null) return false;
        if (freeOther != null ? !freeOther.equals(that.freeOther) : that.freeOther != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = fhid;
        result = 31 * result + cid;
        result = 31 * result + pid;
        temp = Double.doubleToLongBits(consumeLocal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(consumeOther);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (freeLocal != null ? freeLocal.hashCode() : 0);
        result = 31 * result + (freeOther != null ? freeOther.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
