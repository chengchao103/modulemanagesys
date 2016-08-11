package com.chinamobile.iot.dao;

import com.chinamobile.iot.model.business.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Jolin on 2016/5/17.
 */
public interface ModuleDao {
    Module getModuleBySn(@Param("sn") String sn);

    List<Module> getAllModulesByCusId(@Param("customer_id") String customer_id);

    List<Module> getAllModulesByGroup( Map<String, Object> param);

    int getModulesCountByGroup( Map<String, Object> param);

    List getFields(String type);
}
