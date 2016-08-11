package com.chinamobile.iot.model.onenet.response;

/**
 * Created by dds on 2016/6/16.
 */
public class SearchProductsRsp {
    private String id;
    private String name;
    private String device_current_count;
    private String device_limit_count;
    private String group_count;
    private String description;
    private String status;
    private String type;
    private String process;
    private String created_time;
    private String favourite;
    private String created_user_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDevice_current_count() {
        return device_current_count;
    }

    public void setDevice_current_count(String device_current_count) {
        this.device_current_count = device_current_count;
    }

    public String getDevice_limit_count() {
        return device_limit_count;
    }

    public void setDevice_limit_count(String device_limit_count) {
        this.device_limit_count = device_limit_count;
    }

    public String getGroup_count() {
        return group_count;
    }

    public void setGroup_count(String group_count) {
        this.group_count = group_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getCreated_user_id() {
        return created_user_id;
    }

    public void setCreated_user_id(String created_user_id) {
        this.created_user_id = created_user_id;
    }
}
