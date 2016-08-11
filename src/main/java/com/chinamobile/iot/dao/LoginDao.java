package com.chinamobile.iot.dao;

import com.chinamobile.iot.model.business.SmsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

/**
 * Created by Jolin on 2016/5/19.
 */
public interface LoginDao {
    String getPasswordByName(@Param("user_name") String user_name);

    String getMobileByName(@Param("user_name") String user_name);

    SmsInfo getSmsInfo(@Param("user_name") String user_name);

    void saveSmsInfo(Map updateMap);
}
