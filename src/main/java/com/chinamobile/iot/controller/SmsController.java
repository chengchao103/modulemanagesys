package com.chinamobile.iot.controller;

import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.model.response.SimpleResp;
import com.chinamobile.iot.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jolin on 2016/5/26.
 */
@RestController
public class SmsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportController.class);

    @Autowired
    private SmsService smsService;

    /**
     * 发送短信，同时存入数据库
     * @param user_name
     * @param
     * @return
     */
    @RequestMapping(value = "/getValidateCode", method = RequestMethod.GET)
    private SimpleResp send(@RequestParam("user_name") String user_name) {
        LOGGER.info("=====getValidateCode request====user_name:" + user_name);
        SimpleResp resp = new SimpleResp();
        CommonResult cmrt = new CommonResult();
        smsService.sendMessage(user_name, cmrt);
        if (cmrt.getRet() != 0) {
            resp.setErrno(1);
            resp.setError(cmrt.getMsg());
        }
        LOGGER.info("=====getValidateCode request end====user_name:" + user_name);
        return resp;
    }

}
