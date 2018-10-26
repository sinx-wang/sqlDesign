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
@Table(name = "customer", schema = "hibernate", catalog = "")
public class CustomerEntity {
    private int cid;
    private String cname;
    private Integer pid;
    private Integer pidNext;

    public CustomerEntity(String cname, int pid, int pidNext) {
        this.cname = cname;
        this.pid = pid;
        this.pidNext = pidNext;
    }

    @Id
    @Column(name = "cid")
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "cname")
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
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
    @Column(name = "pid_next")
    public Integer getPidNext() {
        return pidNext;
    }

    public void setPidNext(Integer pidNext) {
        this.pidNext = pidNext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (cid != that.cid) return false;
        if (cname != null ? !cname.equals(that.cname) : that.cname != null) return false;
        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (pidNext != null ? !pidNext.equals(that.pidNext) : that.pidNext != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cid;
        result = 31 * result + (cname != null ? cname.hashCode() : 0);
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (pidNext != null ? pidNext.hashCode() : 0);
        return result;
    }
}
