package com.sqlDesign.dao;

import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Mr.Wang
 * @version 2018/10/26
 * @program hibernate
 * @description
 */
@NoArgsConstructor
@Entity
@Table(name = "sms", schema = "hibernate", catalog = "")
public class SmsEntity {
    private int smsId;
    private Integer freeNum;
    private Double standard;

    public SmsEntity(int freeNum, double standard) {
        this.freeNum = freeNum;
        this.standard = standard;
    }

    @Id
    @Column(name = "sms_id")
    public int getSmsId() {
        return smsId;
    }

    public void setSmsId(int smsId) {
        this.smsId = smsId;
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
    @Column(name = "standard")
    public Double getStandard() {
        return standard;
    }

    public void setStandard(Double standard) {
        this.standard = standard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsEntity smsEntity = (SmsEntity) o;

        if (smsId != smsEntity.smsId) return false;
        if (freeNum != null ? !freeNum.equals(smsEntity.freeNum) : smsEntity.freeNum != null) return false;
        if (standard != null ? !standard.equals(smsEntity.standard) : smsEntity.standard != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = smsId;
        result = 31 * result + (freeNum != null ? freeNum.hashCode() : 0);
        result = 31 * result + (standard != null ? standard.hashCode() : 0);
        return result;
    }
}
