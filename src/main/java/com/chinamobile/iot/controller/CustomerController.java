package com.chinamobile.iot.controller;

import com.chinamobile.iot.model.business.CustomerDeviceListEntity;
import com.chinamobile.iot.model.business.CustomerListEntity;
import com.chinamobile.iot.model.response.SimpleResp;
import com.chinamobile.iot.service.CustomerService;
import com.chinamobile.iot.service.ModuleService;
import com.chinamobile.iot.service.SimService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jolin on 2016/5/13.
 */
@RestController
public class CustomerController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    /**
     * 获取客户列表
     *
     * @return SimpleResp
     */
    @RequestMapping(value = "/customers/customerList", method = RequestMethod.GET)
    private SimpleResp getCustomerList(
            @RequestParam(value = "customer_name", required = false) String customer_name,
            @RequestParam(value = "industry", required = false) String industry,
            @RequestParam(value = "page", required = false) Integer pageNo,
            @RequestParam(value = "pagesize", required = false) Integer pageSize) {
        LOGGER.info("=====getCustomerList Request====");
        SimpleResp resp = new SimpleResp();
        Integer pageStart;
        if (pageNo != null && pageSize != null) {
            pageStart = (pageNo - 1) * pageSize;
        } else {
            // 如果没有设置，就默认为10行
            pageNo = 1;
            pageSize = 10;
            pageStart = 0;
        }
        List<CustomerListEntity> list = customerService.getCustomerList(customer_name, industry, pageStart, pageSize);
        int size = customerService.getCustomerListSize(customer_name, industry);
        Map dataMap = new HashMap<>();
        dataMap.put("total_size",size);
        dataMap.put("list",list);
        resp.setData(dataMap);
        LOGGER.info("===getCustomerList data==" + resp.getData().toString());
        return resp;
    }

    /**
     * 获取客户详情
     *
     * @return SimpleResp
     */
    @RequestMapping(value = "/customers/customerDetail", method = RequestMethod.GET)
    private SimpleResp getCustomerDetail(@RequestParam("customer_name") String customer_name) {
        LOGGER.info("=====getCustomerDetail Request====");
        SimpleResp resp = new SimpleResp();
        CustomerListEntity customerListEntity = customerService.getCustomerDetail(customer_name);
        resp.setData(customerListEntity);
        LOGGER.info("===getCustomerDetail data==" + resp.getData().toString());
        return resp;
    }

    /**
     * 获取客户设备列表
     *
     * @return SimpleResp
     */
    @RequestMapping(value = "/customers/customerDeviceList", method = RequestMethod.GET)
    private SimpleResp getCustomerDeviceList(@RequestParam(value = "customer_name") String customer_name,
                                             @RequestParam(value = "imei", required = false) String imei,
                                             @RequestParam(value = "comm_sys", required = false) String comm_sys,
                                             @RequestParam(value = "combo_name", required = false) String combo_name,
                                             @RequestParam(value = "separate", required = false) String separate,
                                             @RequestParam(value = "app_ver", required = false) String app_ver,
                                             @RequestParam(value = "page", required = false) Integer pageNo,
                                             @RequestParam(value = "pagesize", required = false) Integer pageSize) {
        LOGGER.info("=====getCustomerDeviceList Request====");
        SimpleResp resp = new SimpleResp();
        Integer pageStart;
        if (pageNo != null && pageSize != null) {
            pageStart = (pageNo - 1) * pageSize;
        } else {
            // 如果没有设置，就默认为10行
            pageNo = 1;
            pageSize = 10;
            pageStart = 0;
        }
        List<CustomerDeviceListEntity> list = customerService.getCustomerDeviceList(customer_name,imei,comm_sys,combo_name,separate,app_ver,pageStart, pageSize);
        int size = customerService.getCustomerDeviceListSize(customer_name,imei,comm_sys,combo_name,separate,app_ver);
        Map dataMap = new HashMap<>();
        dataMap.put("total_size",size);
        dataMap.put("list",list);
        resp.setData(dataMap);
        LOGGER.info("===getCustomerDeviceList data==" + resp.getData().toString());
        return resp;
    }


}
