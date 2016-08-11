package com.chinamobile.iot.service.Impl;

import com.chinamobile.iot.dao.LoginDao;
import com.chinamobile.iot.model.business.Credentials;
import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.service.AuthenticationService;
import com.chinamobile.iot.utils.AuthenticationUtil;
import com.chinamobile.iot.utils.MainConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jolin on 2016/5/19.
 */
@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private static String suffix = MainConfig.getStringProperty("credentials.suffix") == null ? "_credentials" : MainConfig.getStringProperty("credentials.suffix");
    @Autowired
    private LoginDao loginDao;

    /**
     * 用户名密码认证，如果认证通过则产生access_token返回，同时保存在缓存
     *
     * @param user_name
     * @param password
     * @return Credentials
     */
    @Override
    public Credentials authenticate(String user_name, String password) {
        Credentials credentials = new Credentials();
        credentials.setUser_name(user_name);
        String passwordDB = loginDao.getPasswordByName(user_name);
        if (null != passwordDB && passwordDB.equals(password)) {
            String access_token = AuthenticationUtil.generateAT(user_name);
            credentials.setAccess_token(access_token);
/*            //缓存
            RedisClient.set(user_name + suffix, credentials);
            LOGGER.info("======RedisClient cached credentials====" + user_name);*/
        }
        return credentials;
    }

    /**
     * 根据用户名获取用户绑定的手机号
     *
     * @param user_name
     * @return
     */

    @Override
    public String getUserMobile(String user_name) {
        String mobile = loginDao.getMobileByName(user_name);
        return mobile;
    }

    @Override
    public CommonResult logout(String user_name) {
/*        CommonResult cmrt = new CommonResult();
        LOGGER.info("=========start to logout", user_name);
        Credentials credentialsCached = RedisClient.get(user_name + suffix, Credentials.class);
        if (null == credentialsCached || "".equals(credentialsCached.toString())) {
            cmrt.setRet(1);
            cmrt.setMsg("用户未登录");
            return cmrt;
        }
        RedisClient.del(user_name + suffix);
        return cmrt;*/
        return null;
    }
}
