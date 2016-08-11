package com.chinamobile.iot.service;

import com.chinamobile.iot.model.business.Module;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
public interface ModuleService {
    public Module getModuleBySn(String sn);

    public List getFields(String type);
}
