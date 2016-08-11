package com.chinamobile.iot.model.business;

import java.util.List;

/**
 * Created by buchan on 2016/5/19 0019.
 */
public class Role {
    private String id;
    private String role_code;
    private String role_name;
    private String create_user;
    private String create_datetime;
    private String update_user;
    private String update_datetime;

    private List<String> resource_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role_code) {
        this.role_code = role_code;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(String create_datetime) {
        this.create_datetime = create_datetime;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getUpdate_datetime() {
        return update_datetime;
    }

    public void setUpdate_datetime(String update_datetime) {
        this.update_datetime = update_datetime;
    }

    public List<String> getResource_id() {
        return resource_id;
    }

    public void setResource_id( List<String> resource_id) {
        this.resource_id = resource_id;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", role_code='" + role_code + '\'' +
                ", role_name='" + role_name + '\'' +
                ", create_user='" + create_user + '\'' +
                ", create_datetime='" + create_datetime + '\'' +
                ", update_user='" + update_user + '\'' +
                ", update_datetime='" + update_datetime + '\'' +
                '}';
    }
}
