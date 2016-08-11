package com.chinamobile.iot.model.business;


/**
 * Created by Jolin on 2016/5/13.
 */
public class Module {
    //SN码(Serial Number) 产品序列号
    private String sn;
    //IMEI（International Mobile Equipment Identity）是国际移动设备标识的缩写，IMEI由15位数字(英文字母)组成。
    private String imei;
    /*
    ICCID：Integrate circuit card identity 集成电路卡识别码（固化在SIM卡中） ICCID为IC卡的唯一识别号码，共有20位数字组成，其编
    码格式为：XXXXXX 0MFSS YYGXX XXXXX。分别介绍如下： 前六位运营商代码：中国移动的为：898600；898602 ，中国联通的为：898601、
    898606、898609，中国电信898603
     */
    private String iccid;
    //通讯制式
    private String comm_sys;

    private String module_type;    //模组型号
    //固件版本
    private String firmware_ver;
    //应用版本
    private String app_ver;
    //小区id
    private String communityid;
    private String customer_name;   //客户名
    //信号强度
    private String signal_strength;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getComm_sys() {
        return comm_sys;
    }

    public void setComm_sys(String comm_sys) {
        this.comm_sys = comm_sys;
    }



    public String getModule_type() {
        return module_type;
    }

    public void setModule_type(String module_type) {
        this.module_type = module_type;
    }

    public String getFirmware_ver() {
        return firmware_ver;
    }

    public void setFirmware_ver(String firmware_ver) {
        this.firmware_ver = firmware_ver;
    }

    public String getApp_ver() {
        return app_ver;
    }

    public void setApp_ver(String app_ver) {
        this.app_ver = app_ver;
    }

    public String getCommunityid() {
        return communityid;
    }

    public void setCommunityid(String communityid) {
        this.communityid = communityid;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getSignal_strength() {
        return signal_strength;
    }

    public void setSignal_strength(String signal_strength) {
        this.signal_strength = signal_strength;
    }
}