package com.chinamobile.iot.model.business;

/**
 * Created by buchan on 2016/6/3 0003.
 */
public class Password {
    private String  user_id;
    private String  user_name  ;
    private String  oldPwd  ;
    private String  newPwd  ;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    @Override
    public String toString() {
        return "Password{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", oldPwd='" + oldPwd + '\'' +
                ", newPwd='" + newPwd + '\'' +
                '}';
    }
}
