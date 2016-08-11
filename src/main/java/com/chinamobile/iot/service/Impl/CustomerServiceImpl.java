package com.chinamobile.iot.service.Impl;

import com.chinamobile.iot.dao.CustomerDao;
import com.chinamobile.iot.model.business.CustomerDeviceListEntity;
import com.chinamobile.iot.model.business.CustomerListEntity;
import com.chinamobile.iot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<CustomerListEntity> getCustomerList(String customer_name, String industry, Integer pageStart, Integer pageSize) {
        return customerDao.getCustomerList(customer_name, industry, pageStart, pageSize);
    }

    @Override
    public CustomerListEntity getCustomerDetail(String customer_name) {
        return customerDao.getCustomerDetail(customer_name);
    }


    @Override
    public List<CustomerDeviceListEntity> getCustomerDeviceList(String customer_name, String imei, String comm_sys, String combo_name, String separate, String app_ver, Integer pageStart, Integer pageSize) {
        return customerDao.getCustomerDeviceList(customer_name, imei, comm_sys, combo_name, separate, app_ver, pageStart, pageSize);
    }

    @Override
    public int getCustomerListSize(String customer_name, String industry) {
        return customerDao.getCustomerListSize(customer_name, industry);
    }

    @Override
    public int getCustomerDeviceListSize(String customer_name, String imei, String comm_sys, String combo_name, String separate, String app_ver) {
        return customerDao.getCustomerDeviceListSize(customer_name, imei, comm_sys, combo_name, separate, app_ver);
    }

}
