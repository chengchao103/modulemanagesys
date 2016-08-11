package com.chinamobile.iot.dao;

import com.chinamobile.iot.model.business.analysis.CarrierAnalysisEntity;
import com.chinamobile.iot.model.business.analysis.CommSysAnalysisEntity;
import com.chinamobile.iot.model.business.analysis.IndustryAnalysisEntity;
import com.chinamobile.iot.model.business.location.DeviceLocationInfo;
import com.chinamobile.iot.model.business.statistics.CustomerStatEntity;
import com.chinamobile.iot.model.business.statistics.ModuleStatEntity;
import com.chinamobile.iot.model.business.statistics.SepCardCustomerStatEntity;
import com.chinamobile.iot.model.business.statistics.SepCardModuleStatEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
public interface AnalysisStatisticsDao {

    CustomerStatEntity getCustomerStat();

    ModuleStatEntity getModuleStat(@Param(value = "customer_name") String customer_name);

    SepCardModuleStatEntity getSepCardModuleStat(@Param(value = "customer_name") String customer_name);

    SepCardCustomerStatEntity getSepCardCustomerStat();

    List<IndustryAnalysisEntity> getIndustryAnal();

    List<CommSysAnalysisEntity> getCommSysAnal(@Param(value = "customer_name") String customer_name);

    List<CarrierAnalysisEntity> getCarrierAnal(@Param(value = "customer_name") String customer_name);

    List<DeviceLocationInfo> getModuleTypeLoc(@Param(value = "province") String province, @Param(value = "customer_name") String customer_name);

    List<DeviceLocationInfo> getIndustryLoc(@Param(value = "province") String province);

    List<DeviceLocationInfo> getCommSysLoc(@Param(value = "province") String province, @Param(value = "customer_name") String customer_name);

    List<DeviceLocationInfo> getCustomerValueLoc(@Param(value = "province") String province);
}
