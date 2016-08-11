package com.chinamobile.iot.model.business;


/**
 * Created by Jolin on 2016/5/13.
 */
public class CustomerDeviceListEntity {
    private String customer_name;   //客户名
    private String module_type;   //模组型号
    private String imei;   //模组IMEI号
    private String sn;   //模组SN号
    private String comm_sys;   //通讯制式
    private String app_ver;    //应用版本
    private String iccid;  //ICCID号
    private String carrier;  //运营商
    private String combo_name;  //套餐名字
    private float combo_remain;  //套餐剩余量
    private String status;  //状态

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getModule_type() {
        return module_type;
    }

    public void setModule_type(String module_type) {
        this.module_type = module_type;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getComm_sys() {
        return comm_sys;
    }

    public void setComm_sys(String comm_sys) {
        this.comm_sys = comm_sys;
    }

    public String getApp_ver() {
        return app_ver;
    }

    public void setApp_ver(String app_ver) {
        this.app_ver = app_ver;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getCombo_name() {
        return combo_name;
    }

    public void setCombo_name(String combo_name) {
        this.combo_name = combo_name;
    }

    public float getCombo_remain() {
        return combo_remain;
    }

    public void setCombo_remain(float combo_remain) {
        this.combo_remain = combo_remain;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}