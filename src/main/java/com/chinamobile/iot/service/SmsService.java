package com.chinamobile.iot.service;

import com.chinamobile.iot.model.common.CommonResult;

/**
 * Created by Jolin on 2016/5/17.
 */
public interface SmsService {
    public void sendMessage(String user_name,CommonResult cmrt);

    public CommonResult validateSmsCode(String user_name, String sms_code);
}
