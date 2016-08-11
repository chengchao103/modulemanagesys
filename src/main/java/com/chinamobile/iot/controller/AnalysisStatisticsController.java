package com.chinamobile.iot.controller;

import com.chinamobile.iot.model.business.analysis.AnalEntity;
import com.chinamobile.iot.model.business.analysis.CarrierAnalysisEntity;
import com.chinamobile.iot.model.business.analysis.CommSysAnalysisEntity;
import com.chinamobile.iot.model.business.analysis.IndustryAnalysisEntity;
import com.chinamobile.iot.model.business.location.*;
import com.chinamobile.iot.model.business.statistics.*;
import com.chinamobile.iot.model.response.SimpleResp;
import com.chinamobile.iot.service.AnalysisStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Jolin on 2016/5/13.
 */
@RestController
public class AnalysisStatisticsController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisStatisticsController.class);
    @Autowired
    private AnalysisStatisticsService analysisStatisticsService;

    /**
     * 获取客户、设备统计信息
     *
     * @param customer_name 如果有customer_name，则不返回客户数统计及机卡分离客户数统计。同时返回的设备统计、机卡分离设备统计都是该客户的
     * @return SimpleResp
     */
    @RequestMapping(value = "/customerAndDeviceStat", method = RequestMethod.GET)
    private SimpleResp getCustomerAndDeviceStat(@RequestParam(value = "customer_name", required = false) String customer_name) {
        LOGGER.info("=====getCustomerAndDeviceStat Request====");
        SimpleResp resp = new SimpleResp();
        StatEntity statEntity = new StatEntity();
        if (null == customer_name) {
            CustomerStatEntity customerStatEntity = analysisStatisticsService.getCustomerStat();
            SepCardCustomerStatEntity sepCardCustomerStatEntity = analysisStatisticsService.getSepCardCustomerStat();
            statEntity.setCustomerStatEntity(customerStatEntity);
            statEntity.setSepCardCustomerStatEntity(sepCardCustomerStatEntity);
        }
        ModuleStatEntity moduleStatEntity = analysisStatisticsService.getModuleStat(customer_name);
        SepCardModuleStatEntity sepCardModuleStatEntity = analysisStatisticsService.getSepCardModuleStat(customer_name);
        statEntity.setModuleStatEntity(moduleStatEntity);
        statEntity.setSepCardModuleStatEntity(sepCardModuleStatEntity);
        resp.setData(statEntity);
        LOGGER.info("===getCustomerAndDeviceStat data==" + resp.getData().toString());
        return resp;
    }


    /**
     * 获取行业、通讯制式、运营商等的分析信息
     *
     * @param type          必须,  0: 所有, 1: 行业分析,2: 通信制式分析 3: 运营商分析
     * @param customer_name 可选，若有，则不返回行业分析
     * @return SimpleResp
     */
    @RequestMapping(value = "/customerAndDeviceAnal", method = RequestMethod.GET)
    private SimpleResp getCustomerAndDeviceAnal(@RequestParam(value = "type", required = true) String type, @RequestParam(value = "customer_name", required = false) String customer_name) {
        LOGGER.info("=====getCustomerAndDeviceAnal Request====");
        SimpleResp resp = new SimpleResp();
        AnalEntity analEntity = new AnalEntity();
        if (null != type && type.equals("0")) {
            if(null==customer_name){
                List<IndustryAnalysisEntity> industryAnalysisEntityList = analysisStatisticsService.getIndustryAnal();
                analEntity.setIndustryAnalysisEntityList(industryAnalysisEntityList);
            }
            List<CommSysAnalysisEntity> commSysAnalysisEntityList = analysisStatisticsService.getCommSysAnal(customer_name);
            analEntity.setCommSysAnalysisEntityList(commSysAnalysisEntityList);
            List<CarrierAnalysisEntity> carrierAnalysisEntityList = analysisStatisticsService.getCarrierAnal(customer_name);
            analEntity.setCarrierAnalysisEntityList(carrierAnalysisEntityList);
        } else if (null != type && type.equals("1")) {
            List<IndustryAnalysisEntity> industryAnalysisEntityList = analysisStatisticsService.getIndustryAnal();
            analEntity.setIndustryAnalysisEntityList(industryAnalysisEntityList);
        } else if (null != type && type.equals("2")) {
            List<CommSysAnalysisEntity> commSysAnalysisEntityList = analysisStatisticsService.getCommSysAnal(customer_name);
            analEntity.setCommSysAnalysisEntityList(commSysAnalysisEntityList);
        } else if (null != type && type.equals("3")) {
            List<CarrierAnalysisEntity> carrierAnalysisEntityList = analysisStatisticsService.getCarrierAnal(customer_name);
            analEntity.setCarrierAnalysisEntityList(carrierAnalysisEntityList);
        } else {
            resp.setErrno(1);
            resp.setError("不支持该type的查询");
            return resp;
        }
        resp.setData(analEntity);
        LOGGER.info("===getCustomerAndDeviceAnal data==" + resp.getData().toString());
        return resp;
    }

    /**
     * 获取设备位置分布，按行业、通讯制式、客户价值、设备型号分类获取
     *
     * @param type          必选; 统计维度类型,  1: 设备型号 2: 行业类型 3: 通信制式 4:客户价值
     * @param customer_name 可选；表明值查询某个客户的设备位置分布
     * @param province      可选；表明查询某特定地区的设备位置分布
     *                      customer_name为null表明查询所有customer的设备分布情况，province为null表明查询全国设备分布情况
     * @return SimpleResp
     */
    @RequestMapping(value = "/deviceLocation", method = RequestMethod.GET)
    private SimpleResp getDeviceLocation(@RequestParam(value = "type", required = true) String type, @RequestParam(value = "customer_name", required = false) String customer_name, @RequestParam(value = "province", required = false) String province) {
        LOGGER.info("=====getDeviceLocation Request====");
        SimpleResp resp = new SimpleResp();
        LocationEntity locationEntity = new LocationEntity();
        if (null != type && type.equals("1")) {
            List<DeviceLocationInfo> moduleTypeLocationEntityList = analysisStatisticsService.getModuleTypeLoc(province, customer_name);
            locationEntity.setType(1);
            locationEntity.setDevices(moduleTypeLocationEntityList);
        } else if (null != type && type.equals("2")) {
            List<DeviceLocationInfo> industryLocationEntityList = analysisStatisticsService.getIndustryLoc(province);
            locationEntity.setType(2);
            locationEntity.setDevices(industryLocationEntityList);
        } else if (null != type && type.equals("3")) {
            List<DeviceLocationInfo> commSysLocationEntityList = analysisStatisticsService.getCommSysLoc(province, customer_name);
            locationEntity.setType(3);
            locationEntity.setDevices(commSysLocationEntityList);
        } else if (null != type && type.equals("4")) {
            List<DeviceLocationInfo> customerValueLocationEntityList = analysisStatisticsService.getCustomerValueLoc(province);
            locationEntity.setType(4);
            locationEntity.setDevices(customerValueLocationEntityList);
        } else {
            resp.setErrno(1);
            resp.setError("不支持该type的查询");
            return resp;
        }
        locationEntity.arrange();
        resp.setData(locationEntity);
        LOGGER.info("===getDeviceLocation data==" + resp.getData().toString());
        return resp;
    }


}
