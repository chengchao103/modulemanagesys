package com.chinamobile.iot.model.business;

/**
 * Created by Jolin on 2016/5/17.
 */
public class Sim {
    private String iccid;
    private String imsi;
    private String customer_name;
    //运营商
    private String carrier;
    //在网时间
    private String online_time;
    //套餐名称
    private String combo_name;
    //套餐总量
    private String combo_sum;
    //套餐用量
    private String combo_useage;
    //套餐剩余量
    private String combo_remain;

    //状态
    private String status;

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getOnline_time() {
        return online_time;
    }

    public void setOnline_time(String online_time) {
        this.online_time = online_time;
    }

    public String getCombo_name() {
        return combo_name;
    }

    public void setCombo_name(String combo_name) {
        this.combo_name = combo_name;
    }

    public String getCombo_sum() {
        return combo_sum;
    }

    public void setCombo_sum(String combo_sum) {
        this.combo_sum = combo_sum;
    }

    public String getCombo_useage() {
        return combo_useage;
    }

    public void setCombo_useage(String combo_useage) {
        this.combo_useage = combo_useage;
    }

    public String getCombo_remain() {
        return combo_remain;
    }

    public void setCombo_remain(String combo_remain) {
        this.combo_remain = combo_remain;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
