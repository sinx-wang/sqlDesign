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
@Table(name = "product", schema = "hibernate", catalog = "")
public class ProductEntity {
    private int pid;
    private String pname;
    private Integer callId;
    private Integer smsId;
    private Integer flowId;
    private Double base;

    public ProductEntity(String pname, int callId, int smsId, int flowId, double base) {
        this.pname = pname;
        this.callId = callId;
        this.smsId = smsId;
        this.flowId = flowId;
        this.base = base;
    }

    @Id
    @Column(name = "pid")
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "pname")
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Basic
    @Column(name = "call_id")
    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }

    @Basic
    @Column(name = "sms_id")
    public Integer getSmsId() {
        return smsId;
    }

    public void setSmsId(Integer smsId) {
        this.smsId = smsId;
    }

    @Basic
    @Column(name = "flow_id")
    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    @Basic
    @Column(name = "base")
    public Double getBase() {
        return base;
    }

    public void setBase(Double base) {
        this.base = base;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (pid != that.pid) return false;
        if (pname != null ? !pname.equals(that.pname) : that.pname != null) return false;
        if (callId != null ? !callId.equals(that.callId) : that.callId != null) return false;
        if (smsId != null ? !smsId.equals(that.smsId) : that.smsId != null) return false;
        if (flowId != null ? !flowId.equals(that.flowId) : that.flowId != null) return false;
        if (base != null ? !base.equals(that.base) : that.base != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pid;
        result = 31 * result + (pname != null ? pname.hashCode() : 0);
        result = 31 * result + (callId != null ? callId.hashCode() : 0);
        result = 31 * result + (smsId != null ? smsId.hashCode() : 0);
        result = 31 * result + (flowId != null ? flowId.hashCode() : 0);
        result = 31 * result + (base != null ? base.hashCode() : 0);
        return result;
    }
}
