package com.chinamobile.iot.model.business;


/**
 * Created by Jolin on 2016/5/13.
 */
public class CustomerListEntity {
    private String customer_name;   //客户名
    private String industry;   //行业
    private String province;   //省份
    private String type_num;   //型号数量
    private String module_num;   //模组数量
    private String comm_sys;    //通讯制式（所有）
    private String carrier;  //运营商（所有）
    private String separate_num;  //机卡分离数
    private String sim_num;  //Sim卡数

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

    public String getType_num() {
        return type_num;
    }

    public void setType_num(String type_num) {
        this.type_num = type_num;
    }

    public String getModule_num() {
        return module_num;
    }

    public void setModule_num(String module_num) {
        this.module_num = module_num;
    }

    public String getComm_sys() {
        return comm_sys;
    }

    public void setComm_sys(String comm_sys) {
        this.comm_sys = comm_sys;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getSeparate_num() {
        return separate_num;
    }

    public void setSeparate_num(String separate_num) {
        this.separate_num = separate_num;
    }

    public String getSim_num() {
        return sim_num;
    }

    public void setSim_num(String sim_num) {
        this.sim_num = sim_num;
    }
}