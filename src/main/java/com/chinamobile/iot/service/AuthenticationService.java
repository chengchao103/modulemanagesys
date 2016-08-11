package com.chinamobile.iot.service;

import com.chinamobile.iot.model.business.Credentials;
import com.chinamobile.iot.model.common.CommonResult;

/**
 * Created by Jolin on 2016/5/19.
 */
public interface AuthenticationService {
    public Credentials authenticate(String user_name,String password);

    public String getUserMobile(String user_name);

    public CommonResult logout(String user_name);
}
