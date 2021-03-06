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
@Table(name = "product_history", schema = "hibernate")
public class ProductHistoryEntity {
    private int phid;
    private int cid;
    private Integer pid;
    private Integer pNextId;
    private int month;
    private int beUsing;

    public ProductHistoryEntity(int cid, int pid, int pNextId, int month, int beUsing) {
        this.cid = cid;
        this.pid = pid;
        this.pNextId = pNextId;
        this.month = month;
        this.beUsing = beUsing;
    }

    @Id
    @Column(name = "phid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getPhid() {
        return phid;
    }

    public void setPhid(int phid) {
        this.phid = phid;
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
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "p_next_id")
    public Integer getpNextId() {
        return pNextId;
    }

    public void setpNextId(Integer pNextId) {
        this.pNextId = pNextId;
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
    @Column(name = "be_using", columnDefinition = "Integer default 1")
    public int getBeUsing() {
        return beUsing;
    }

    public void setBeUsing(int beUsing) {
        this.beUsing = beUsing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductHistoryEntity that = (ProductHistoryEntity) o;

        if (phid != that.phid) return false;
        if (cid != that.cid) return false;
        if (month != that.month) return false;
        if (beUsing != that.beUsing) return false;
        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (pNextId != null ? !pNextId.equals(that.pNextId) : that.pNextId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phid;
        result = 31 * result + cid;
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (pNextId != null ? pNextId.hashCode() : 0);
        result = 31 * result + month;
        result = 31 * result + beUsing;
        return result;
    }
}
