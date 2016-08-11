package com.chinamobile.iot.model.business.location;

/**
 * Created by Jolin on 2016/6/8.
 */
public class DeviceLocationInfo {
    private String identifier;//标识符
    private String province;//省份
    private String city;//城市
    private int module_count;//该通讯制式该省份模组总数量

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getModule_count() {
        return module_count;
    }

    public void setModule_count(int module_count) {
        this.module_count = module_count;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
