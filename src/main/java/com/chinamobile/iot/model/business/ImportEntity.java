package com.chinamobile.iot.model.business;


import java.util.Date;

/**
 * Created by Jolin on 2016/5/13.
 */
public class ImportEntity {
    private String sn;  //SN码(Serial Number) 产品序列号
    private String customer_name;   //客户名
    private String industry;   //行业
    private String province;   //省份
    private String imei;    //IMEI（International Mobile Equipment Identity）是国际移动设备标识的缩写，IMEI由15位数字(英文字母)组成。
    private String module_type;  //模组型号
    private String comm_sys;  //通讯制式
    private String app_ver;  //应用版本
    /*
    ICCID：Integrate circuit card identity 集成电路卡识别码（固化在SIM卡中） ICCID为IC卡的唯一识别号码，共有20位数字组成，其编
    码格式为：XXXXXX 0MFSS YYGXX XXXXX。分别介绍如下： 前六位运营商代码：中国移动的为：898600；898602 ，中国联通的为：898601、
    898606、898609，中国电信898603
    */
    private String iccid;
    private String combo_name;  //套餐名称
    private String prom_online_time;  //承诺在网时间
    private Date import_time; //导入时间

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getModule_type() {
        return module_type;
    }

    public void setModule_type(String module_type) {
        this.module_type = module_type;
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

    public String getCombo_name() {
        return combo_name;
    }

    public void setCombo_name(String combo_name) {
        this.combo_name = combo_name;
    }

    public String getProm_online_time() {
        return prom_online_time;
    }

    public void setProm_online_time(String prom_online_time) {
        this.prom_online_time = prom_online_time;
    }

    public Date getImport_time() {
        return import_time;
    }

    public void setImport_time(Date import_time) {
        this.import_time = import_time;
    }

    @Override
    public String toString() {
        return "ImportEntity{" +
                "sn='" + sn + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", industry='" + industry + '\'' +
                ", province='" + province + '\'' +
                ", imei='" + imei + '\'' +
                ", module_type='" + module_type + '\'' +
                ", comm_sys='" + comm_sys + '\'' +
                ", app_ver='" + app_ver + '\'' +
                ", iccid='" + iccid + '\'' +
                ", combo_name='" + combo_name + '\'' +
                ", prom_online_time='" + prom_online_time + '\'' +
                ", import_time=" + import_time +
                '}';
    }
}