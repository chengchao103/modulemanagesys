package com.chinamobile.iot.model.business.statistics;

/**
 * Created by Jolin on 2016/6/8.
 */
public class ModuleStatEntity {
    private int module_count;//设备总数量
    private int module_add_count;//设备当日新增总数量

    public int getModule_count() {
        return module_count;
    }

    public void setModule_count(int module_count) {
        this.module_count = module_count;
    }

    public int getModule_add_count() {
        return module_add_count;
    }

    public void setModule_add_count(int module_add_count) {
        this.module_add_count = module_add_count;
    }
}
