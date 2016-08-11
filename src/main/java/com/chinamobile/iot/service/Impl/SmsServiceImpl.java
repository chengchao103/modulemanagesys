package com.chinamobile.iot.service.Impl;

import com.chinamobile.iot.dao.LoginDao;
import com.chinamobile.iot.model.business.Credentials;
import com.chinamobile.iot.model.business.SmsInfo;
import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.service.AuthenticationService;
import com.chinamobile.iot.service.SmsService;
import com.chinamobile.iot.utils.AuthenticationUtil;
import com.chinamobile.iot.utils.MainConfig;
import com.chinamobile.iot.utils.SMSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jolin on 2016/5/26.
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsService.class);
    private static String tempId = MainConfig.getStringProperty("sms.tempId") == null ? "" : MainConfig.getStringProperty("sms.tempId");
    private static int sms_life = MainConfig.getStringProperty("sms.life") == null ? 60 : Integer.parseInt(MainConfig.getStringProperty("sms.life"));
    //private static String suffix = MainConfig.getStringProperty("sms.suffix") == null ? "_sms" : MainConfig.getStringProperty("sms.suffix");
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private LoginDao loginDao;

    @Override
    public void sendMessage(String user_name, CommonResult cmrt) {
        //获取用户手机号
        String mobile = authenticationService.getUserMobile(user_name);
        if (null == mobile || "".equals(mobile)) {
            cmrt.setRet(1);
            cmrt.setMsg("用户未绑定手机号或用户不存在");
            return;
        }
        //产生随机数
        String sms_code = AuthenticationUtil.generateSmsCode();
        //存入数据库
        Date sms_create_time = new Date();
        Map updateMap = new HashMap();
        updateMap.put("user_name",user_name);
        updateMap.put("sms_code",sms_code);
        updateMap.put("sms_create_time",sms_create_time);
        loginDao.saveSmsInfo(updateMap);
       /* SmsInfo smsInfo = new SmsInfo(user_name, mobile, sms_code, now);
        RedisClient.set(user_name + suffix, smsInfo);
        LOGGER.info("======RedisClient cached smsInfo====" + user_name);*/
        //发送短信
        cmrt = SMSUtil.send(mobile, tempId, "code=" + sms_code);
        return;

    }

    @Override
    public CommonResult validateSmsCode(String user_name, String sms_code) {
        CommonResult cmrt = new CommonResult();
        LOGGER.info("=========start to validate {}'s sms_code", user_name);
        SmsInfo smsInfoDB = loginDao.getSmsInfo(user_name);
//        SmsInfo smsInfoCached = RedisClient.get(user_name + suffix, SmsInfo.class);
        if (null == smsInfoDB || "".equals(smsInfoDB.toString())) {
            cmrt.setRet(1);
            cmrt.setMsg("服务器无短信验证码信息");
            return cmrt;
        }
        Date now = new Date();
        Date create_time = smsInfoDB.getSms_create_time();
        if (now.getTime() - create_time.getTime() > sms_life * 1000) {
            cmrt.setRet(1);
            cmrt.setMsg("短信验证码已过期");
            return cmrt;
        }
        String sms_codeCached = smsInfoDB.getSms_code() == null ? "" : smsInfoDB.getSms_code();
        if (!sms_code.equals(sms_codeCached)) {
            cmrt.setRet(1);
            cmrt.setMsg("短信验证码错误");
            return cmrt;
        }
        return cmrt;
    }

}
