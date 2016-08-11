package com.chinamobile.iot.model.business;

/**
 * Created by Jolin on 2016/5/19.
 */
public class Credentials {
    private String user_name;
    private String access_token;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "user_name='" + user_name + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }

}
