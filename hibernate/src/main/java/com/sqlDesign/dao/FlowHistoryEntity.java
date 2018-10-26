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
@Table(name = "flow_history", schema = "hibernate", catalog = "")
public class FlowHistoryEntity {
    private int cid;
    private Double consumeLocal;
    private Double consumeOther;
    private Double freeLocal;
    private Double freeOther;
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
    @Column(name = "consume_local")
    public Double getConsumeLocal() {
        return consumeLocal;
    }

    public void setConsumeLocal(Double consumeLocal) {
        this.consumeLocal = consumeLocal;
    }

    @Basic
    @Column(name = "consume_other")
    public Double getConsumeOther() {
        return consumeOther;
    }

    public void setConsumeOther(Double consumeOther) {
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

        if (cid != that.cid) return false;
        if (consumeLocal != null ? !consumeLocal.equals(that.consumeLocal) : that.consumeLocal != null) return false;
        if (consumeOther != null ? !consumeOther.equals(that.consumeOther) : that.consumeOther != null) return false;
        if (freeLocal != null ? !freeLocal.equals(that.freeLocal) : that.freeLocal != null) return false;
        if (freeOther != null ? !freeOther.equals(that.freeOther) : that.freeOther != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cid;
        result = 31 * result + (consumeLocal != null ? consumeLocal.hashCode() : 0);
        result = 31 * result + (consumeOther != null ? consumeOther.hashCode() : 0);
        result = 31 * result + (freeLocal != null ? freeLocal.hashCode() : 0);
        result = 31 * result + (freeOther != null ? freeOther.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
