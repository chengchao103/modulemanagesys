package com.chinamobile.iot.model.business.analysis;

import java.util.List;

/**
 * Created by Jolin on 2016/6/8.
 */
public class AnalEntity {
    private List<IndustryAnalysisEntity> industryAnalysisEntityList;
    private List<CommSysAnalysisEntity> commSysAnalysisEntityList;
    private List<CarrierAnalysisEntity> carrierAnalysisEntityList;

    public List<IndustryAnalysisEntity> getIndustryAnalysisEntityList() {
        return industryAnalysisEntityList;
    }

    public void setIndustryAnalysisEntityList(List<IndustryAnalysisEntity> industryAnalysisEntityList) {
        this.industryAnalysisEntityList = industryAnalysisEntityList;
    }

    public List<CommSysAnalysisEntity> getCommSysAnalysisEntityList() {
        return commSysAnalysisEntityList;
    }

    public void setCommSysAnalysisEntityList(List<CommSysAnalysisEntity> commSysAnalysisEntityList) {
        this.commSysAnalysisEntityList = commSysAnalysisEntityList;
    }

    public List<CarrierAnalysisEntity> getCarrierAnalysisEntityList() {
        return carrierAnalysisEntityList;
    }

    public void setCarrierAnalysisEntityList(List<CarrierAnalysisEntity> carrierAnalysisEntityList) {
        this.carrierAnalysisEntityList = carrierAnalysisEntityList;
    }
}
