package com.chinamobile.iot.service.Impl;

import com.chinamobile.iot.dao.AnalysisStatisticsDao;
import com.chinamobile.iot.model.business.analysis.CarrierAnalysisEntity;
import com.chinamobile.iot.model.business.analysis.CommSysAnalysisEntity;
import com.chinamobile.iot.model.business.analysis.IndustryAnalysisEntity;
import com.chinamobile.iot.model.business.location.DeviceLocationInfo;
import com.chinamobile.iot.model.business.statistics.CustomerStatEntity;
import com.chinamobile.iot.model.business.statistics.ModuleStatEntity;
import com.chinamobile.iot.model.business.statistics.SepCardCustomerStatEntity;
import com.chinamobile.iot.model.business.statistics.SepCardModuleStatEntity;
import com.chinamobile.iot.service.AnalysisStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
@Service("statisticsSevice")
public class AnalysisStatisticsServiceImpl implements AnalysisStatisticsService {
    @Autowired
    private AnalysisStatisticsDao AnalysisStatisticsrDao;

    @Override
    public CustomerStatEntity getCustomerStat() {
        return AnalysisStatisticsrDao.getCustomerStat();
    }

    @Override
    public ModuleStatEntity getModuleStat(String customer_name) {
        return AnalysisStatisticsrDao.getModuleStat(customer_name);
    }

    @Override
    public SepCardModuleStatEntity getSepCardModuleStat(String customer_name) {
        return AnalysisStatisticsrDao.getSepCardModuleStat(customer_name);
    }

    @Override
    public SepCardCustomerStatEntity getSepCardCustomerStat() {
        return AnalysisStatisticsrDao.getSepCardCustomerStat();
    }

    @Override
    public List<IndustryAnalysisEntity> getIndustryAnal() {
        return AnalysisStatisticsrDao.getIndustryAnal();
    }

    @Override
    public List<CommSysAnalysisEntity> getCommSysAnal(String customer_name) {
        return AnalysisStatisticsrDao.getCommSysAnal(customer_name);
    }

    @Override
    public List<CarrierAnalysisEntity> getCarrierAnal(String customer_name) {
        return AnalysisStatisticsrDao.getCarrierAnal(customer_name);
    }

    @Override
    public List<DeviceLocationInfo> getModuleTypeLoc(String province, String customer_name) {
        return AnalysisStatisticsrDao.getModuleTypeLoc(province,customer_name);
    }

    @Override
    public List<DeviceLocationInfo> getIndustryLoc(String province) {
    return AnalysisStatisticsrDao.getIndustryLoc(province);
    }

    @Override
    public List<DeviceLocationInfo> getCommSysLoc(String province, String customer_name) {
        return AnalysisStatisticsrDao.getCommSysLoc(province,customer_name);
    }

    @Override
    public List<DeviceLocationInfo> getCustomerValueLoc(String province) {
        return AnalysisStatisticsrDao.getCustomerValueLoc(province);
    }
}
