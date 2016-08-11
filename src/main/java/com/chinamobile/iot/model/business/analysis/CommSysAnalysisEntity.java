package com.chinamobile.iot.model.business.analysis;

/**
 * Created by Jolin on 2016/6/8.
 */
public class CommSysAnalysisEntity {
    private String comm_sys;//通讯制式
    private int module_count;//该通讯制式设备总数量
    private int percentage;//该通讯制式占全部通讯制式比重

    public String getComm_sys() {
        return comm_sys;
    }

    public void setComm_sys(String comm_sys) {
        this.comm_sys = comm_sys;
    }

    public int getModule_count() {
        return module_count;
    }

    public void setModule_count(int module_count) {
        this.module_count = module_count;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
