package com.chinamobile.iot.service;

import com.chinamobile.iot.model.business.analysis.CarrierAnalysisEntity;
import com.chinamobile.iot.model.business.analysis.CommSysAnalysisEntity;
import com.chinamobile.iot.model.business.analysis.IndustryAnalysisEntity;
import com.chinamobile.iot.model.business.location.DeviceLocationInfo;
import com.chinamobile.iot.model.business.statistics.CustomerStatEntity;
import com.chinamobile.iot.model.business.statistics.ModuleStatEntity;
import com.chinamobile.iot.model.business.statistics.SepCardCustomerStatEntity;
import com.chinamobile.iot.model.business.statistics.SepCardModuleStatEntity;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
public interface AnalysisStatisticsService {

    CustomerStatEntity getCustomerStat();

    ModuleStatEntity getModuleStat(String customer_name);

    SepCardModuleStatEntity getSepCardModuleStat(String customer_name);

    SepCardCustomerStatEntity getSepCardCustomerStat();

    List<IndustryAnalysisEntity> getIndustryAnal();

    List<CommSysAnalysisEntity> getCommSysAnal(String customer_name);

    List<CarrierAnalysisEntity> getCarrierAnal(String customer_name);

    List<DeviceLocationInfo> getModuleTypeLoc(String province, String customer_name);

    List<DeviceLocationInfo> getIndustryLoc(String province);

    List<DeviceLocationInfo> getCommSysLoc(String province, String customer_name);

    List<DeviceLocationInfo> getCustomerValueLoc(String province);
}
