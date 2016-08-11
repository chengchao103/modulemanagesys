package com.chinamobile.iot.model.business;


import java.util.Date;

/**
 * Created by Jolin on 2016/5/26.
 */
public class SmsInfo {
    private String user_name;
    private String mobile;
    private String sms_code;
    private Date sms_create_time;

    public SmsInfo() {
    }

    public SmsInfo(String user_name, String mobile, String sms_code, Date sms_create_time) {
        this.user_name = user_name;
        this.mobile = mobile;
        this.sms_code = sms_code;
        this.sms_create_time = sms_create_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSms_code() {
        return sms_code;
    }

    public void setSms_code(String sms_code) {
        this.sms_code = sms_code;
    }

    public Date getSms_create_time() {
        return sms_create_time;
    }

    public void setSms_create_time(Date sms_create_time) {
        this.sms_create_time = sms_create_time;
    }

    @Override
    public String toString() {
        return "SmsInfo{" +
                "user_name='" + user_name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sms_code='" + sms_code + '\'' +
                ", create_time=" + sms_create_time +
                '}';
    }
}
