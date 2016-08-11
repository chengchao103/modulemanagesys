package com.chinamobile.iot.service;

import com.chinamobile.iot.model.business.CustomerDeviceListEntity;
import com.chinamobile.iot.model.business.CustomerListEntity;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
public interface CustomerService {
    public CustomerListEntity getCustomerDetail(String customer_name);

    public List<CustomerListEntity> getCustomerList(String customer_name, String industry, Integer pageStart, Integer pageSize);

    public List<CustomerDeviceListEntity> getCustomerDeviceList(String customer_name, String imei, String comm_sys, String combo_name, String separate, String app_ver, Integer pageStart, Integer pageSize);

    public int getCustomerListSize(String customer_name, String industry);

    public int getCustomerDeviceListSize(String customer_name, String imei, String comm_sys, String combo_name, String separate, String app_ver);
}
