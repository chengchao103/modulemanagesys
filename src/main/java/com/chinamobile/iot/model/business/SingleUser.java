package com.chinamobile.iot.model.business;

/**
 * Created by Jolin on 2016/6/21.
 */
public class SingleUser {
    private String  user_name  ;
    private String  mobile  ;
    private int  role_id;

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

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
