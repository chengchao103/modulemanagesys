package com.chinamobile.iot.model.business.analysis;

/**
 * Created by Jolin on 2016/6/8.
 */
public class IndustryAnalysisEntity {
    private String industry;//行业
    private int module_count;//该行业设备总数量
    private int customer_count;//该行业客户总数量
    private int percentage;//该行业占全部行业比重

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getModule_count() {
        return module_count;
    }

    public void setModule_count(int module_count) {
        this.module_count = module_count;
    }

    public int getCustomer_count() {
        return customer_count;
    }

    public void setCustomer_count(int customer_count) {
        this.customer_count = customer_count;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
