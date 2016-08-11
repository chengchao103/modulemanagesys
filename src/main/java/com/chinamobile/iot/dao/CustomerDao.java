package com.chinamobile.iot.dao;

import com.chinamobile.iot.model.business.CustomerDeviceListEntity;
import com.chinamobile.iot.model.business.CustomerListEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
public interface CustomerDao {
    List<CustomerListEntity> getCustomerList(@Param("customer_name") String customer_name,@Param("industry") String industry,@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);
    CustomerListEntity getCustomerDetail(@Param("customer_name") String customer_name);

    List<CustomerDeviceListEntity> getCustomerDeviceList(@Param("customer_name") String customer_name, @Param("imei") String imei, @Param("comm_sys") String comm_sys, @Param("combo_name") String combo_name, @Param("separate") String separate, @Param("app_ver") String app_ver, @Param("pageStart") Integer pageStart, @Param("pageSize") Integer pageSize);

    int getCustomerListSize(@Param("customer_name") String customer_name,@Param("industry") String industry);

    int getCustomerDeviceListSize(@Param("customer_name") String customer_name, @Param("imei") String imei, @Param("comm_sys") String comm_sys, @Param("combo_name") String combo_name, @Param("separate") String separate, @Param("app_ver") String app_ver);
}
