package com.example.projectone.measureExp;

import java.sql.Date;

public class measureBean {
//     orderid, mobile, dress, pic, dodel, qty, bill, measurements, doorder
    Integer orderid;
    String mobile;
    String dress;
    Date dodel;
    String worker;
    Integer workstatus;

    public measureBean() {
    }

    public measureBean(Integer orderid, String mobile, String dress, Date dodel, String worker, Integer workstatus) {
        this.orderid = orderid;
        this.mobile = mobile;
        this.dress = dress;
        this.dodel = dodel;
        this.worker = worker;
        this.workstatus = workstatus;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress;
    }

    public Date getDodel() {
        return dodel;
    }

    public void setDodel(Date dodel) {
        this.dodel = dodel;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public Integer getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(Integer workstatus) {
        this.workstatus = workstatus;
    }
}
