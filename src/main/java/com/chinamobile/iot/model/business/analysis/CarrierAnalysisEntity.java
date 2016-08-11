package com.chinamobile.iot.model.business.analysis;

/**
 * Created by Jolin on 2016/6/8.
 */
public class CarrierAnalysisEntity {
    private String carrier;//运营商
    private int module_count;//该运营商设备总数量
    private int percentage;//该运营商占全部运营商比重

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public int getModule_count() {
        return module_count;
    }

    public void setModule_count(int module_count) {
        this.module_count = module_count;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
