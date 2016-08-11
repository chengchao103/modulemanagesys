package com.chinamobile.iot.model.business.statistics;

/**
 * Created by Jolin on 2016/6/8.
 */
public class SepCardCustomerStatEntity {
    private int customer_count;//机卡分离客户总数量
    private int customer_add_count;//机卡分离客户当日新增总数量

    public int getCustomer_count() {
        return customer_count;
    }

    public void setCustomer_count(int customer_count) {
        this.customer_count = customer_count;
    }

    public int getCustomer_add_count() {
        return customer_add_count;
    }

    public void setCustomer_add_count(int customer_add_count) {
        this.customer_add_count = customer_add_count;
    }
}
