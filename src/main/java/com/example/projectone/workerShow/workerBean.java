package com.example.projectone.workerShow;

public class workerBean {
//    wname, address, mobile, splz

    String wname;
    String address;
    String mobile;
    String splz;

    public workerBean(String wname, String address, String mobile, String splz) {
        this.wname = wname;
        this.address = address;
        this.mobile = mobile;
        this.splz = splz;
    }

    public workerBean() {
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSplz() {
        return splz;
    }

    public void setSplz(String splz) {
        this.splz = splz;
    }
}
