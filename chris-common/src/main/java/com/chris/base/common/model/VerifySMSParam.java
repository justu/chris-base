package com.chris.base.common.model;

import java.io.Serializable;
import java.util.Date;

public class VerifySMSParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mobile;

    private String code;

    private Date sendDate;

    private String ipAddr;

    public VerifySMSParam(String mobile, String code, Date sendDate, String ipAddr) {
        this.mobile = mobile;
        this.code = code;
        this.sendDate = sendDate;
        this.ipAddr = ipAddr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
}
