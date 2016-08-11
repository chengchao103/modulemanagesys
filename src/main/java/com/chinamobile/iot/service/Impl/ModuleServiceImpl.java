package com.chinamobile.iot.service.Impl;

import com.chinamobile.iot.dao.ModuleDao;
import com.chinamobile.iot.model.business.Module;
import com.chinamobile.iot.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;

    @Override
    public Module getModuleBySn(String sn) {
        return moduleDao.getModuleBySn(sn);
    }

    @Override
    public List getFields(String type) {
        return  moduleDao.getFields(type);
    }

}
