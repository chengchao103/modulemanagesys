package com.chinamobile.iot.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinamobile.iot.model.business.Credentials;
import com.chinamobile.iot.model.business.SingleUser;
import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.model.response.SimpleResp;
import com.chinamobile.iot.service.AuthenticationService;
import com.chinamobile.iot.service.SmsService;
import com.chinamobile.iot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jolin on 2016/5/19.
 */
@RestController
public class LoginAndLogoutController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAndLogoutController.class);
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    private SimpleResp doLogin(@RequestBody String requestBody) {
        SimpleResp resp = new SimpleResp();
        JSONObject loginInfo = new JSONObject();
        try {
            loginInfo = JSONObject.parseObject(requestBody);
        } catch (com.alibaba.fastjson.JSONException e) {
            resp.setErrno(1);
            resp.setError("requestBody格式有误");
            return resp;
        }
        String user_name = loginInfo.get("user_name") == null ? "" : loginInfo.get("user_name").toString();
        String password = loginInfo.get("password") == null ? "" : loginInfo.get("password").toString();
        String sms_code = loginInfo.get("sms_code") == null ? "" : loginInfo.get("sms_code").toString();
        LOGGER.info("=====doLogin====user_name:" + user_name);

        CommonResult cmrt = smsService.validateSmsCode(user_name, sms_code);
        if (cmrt.getRet() != 0) {
            resp.setErrno(1);
            resp.setError(cmrt.getMsg());
            return resp;
        }
        Credentials credentials = authenticationService.authenticate(user_name, password);
        if (null == credentials.getAccess_token()) {
            resp.setErrno(1);
            resp.setError("用户名或密码不正确");
            return resp;
        }

        SingleUser singleUser= userService.getSingleUserByName(user_name);
        resp.setData(singleUser);
        LOGGER.info("===loginResp.getErrno==" + resp.getErrno());
        return resp;

    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    private SimpleResp doLogout(@RequestParam("user_name") String user_name) {
        LOGGER.info("=====doLogout====user_name:" + user_name);
        SimpleResp resp = new SimpleResp();
        CommonResult cmrt = authenticationService.logout(user_name);
        if (cmrt.getRet() != 0) {
            resp.setErrno(1);
            resp.setError(cmrt.getMsg());
            return resp;
        }
        return resp;

    }
}
