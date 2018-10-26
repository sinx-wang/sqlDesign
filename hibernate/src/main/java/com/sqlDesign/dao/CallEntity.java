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
@Table(name = "call", schema = "hibernate", catalog = "")
public class CallEntity {
    private int callId;
    private Double freeTime;
    private Double standard;

    public CallEntity(double freeTime, double standard) {
        this.freeTime = freeTime;
        this.standard = standard;
    }

    @Id
    @Column(name = "call_id")
    public int getCallId() {
        return callId;
    }

    public void setCallId(int callId) {
        this.callId = callId;
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

        CallEntity that = (CallEntity) o;

        if (callId != that.callId) return false;
        if (freeTime != null ? !freeTime.equals(that.freeTime) : that.freeTime != null) return false;
        if (standard != null ? !standard.equals(that.standard) : that.standard != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = callId;
        result = 31 * result + (freeTime != null ? freeTime.hashCode() : 0);
        result = 31 * result + (standard != null ? standard.hashCode() : 0);
        return result;
    }
}
