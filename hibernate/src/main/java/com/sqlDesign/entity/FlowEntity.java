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
@Table(name = "flow", schema = "hibernate")
public class FlowEntity {
    private int flowId;
    private Double localFreeNum;
    private Double otherFreeNum;
    private Double localStandard;
    private Double otherStandard;

    public FlowEntity(double localFreeNum, double otherFreeNum, double localStandard, double otherStandard) {
        this.localFreeNum = localFreeNum;
        this.otherFreeNum = otherFreeNum;
        this.localStandard = localStandard;
        this.otherStandard = otherStandard;
    }

    @Id
    @Column(name = "flow_id")
    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    @Basic
    @Column(name = "local_free_num")
    public Double getLocalFreeNum() {
        return localFreeNum;
    }

    public void setLocalFreeNum(Double localFreeNum) {
        this.localFreeNum = localFreeNum;
    }

    @Basic
    @Column(name = "other_free_num")
    public Double getOtherFreeNum() {
        return otherFreeNum;
    }

    public void setOtherFreeNum(Double otherFreeNum) {
        this.otherFreeNum = otherFreeNum;
    }

    @Basic
    @Column(name = "local_standard")
    public Double getLocalStandard() {
        return localStandard;
    }

    public void setLocalStandard(Double localStandard) {
        this.localStandard = localStandard;
    }

    @Basic
    @Column(name = "other_standard")
    public Double getOtherStandard() {
        return otherStandard;
    }

    public void setOtherStandard(Double otherStandard) {
        this.otherStandard = otherStandard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlowEntity that = (FlowEntity) o;

        if (flowId != that.flowId) return false;
        if (localFreeNum != null ? !localFreeNum.equals(that.localFreeNum) : that.localFreeNum != null) return false;
        if (otherFreeNum != null ? !otherFreeNum.equals(that.otherFreeNum) : that.otherFreeNum != null) return false;
        if (localStandard != null ? !localStandard.equals(that.localStandard) : that.localStandard != null)
            return false;
        if (otherStandard != null ? !otherStandard.equals(that.otherStandard) : that.otherStandard != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = flowId;
        result = 31 * result + (localFreeNum != null ? localFreeNum.hashCode() : 0);
        result = 31 * result + (otherFreeNum != null ? otherFreeNum.hashCode() : 0);
        result = 31 * result + (localStandard != null ? localStandard.hashCode() : 0);
        result = 31 * result + (otherStandard != null ? otherStandard.hashCode() : 0);
        return result;
    }
}
