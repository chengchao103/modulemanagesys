package com.chinamobile.iot.dao;

import com.chinamobile.iot.model.business.Sim;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
public interface SimDao {
    Sim getSimByIccid(@Param("iccid") String iccid);
}
