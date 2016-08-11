package com.chinamobile.iot.controller;

import com.chinamobile.iot.model.business.Credentials;
import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.utils.MainConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jolin on 2016/5/19.
 */
public abstract class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
    private static String suffix = MainConfig.getStringProperty("credentials.suffix") == null ? "_credentials" : MainConfig.getStringProperty("credentials.suffix");

    public CommonResult auth(String user_name, String access_token) {
      /*  CommonResult cmrt = new CommonResult();
        LOGGER.info("=========start to auth {}'s access_token", user_name);
        Credentials credentialsCached = RedisClient.get(user_name + suffix, Credentials.class);
        if (null == credentialsCached || "".equals(credentialsCached.toString())) {
            cmrt.setRet(1);
            cmrt.setMsg("用户未登录");
            return cmrt;
        }
        String access_tokenCached = credentialsCached.getAccess_token() == null ? "" : credentialsCached.getAccess_token();
        if (!access_token.equals(access_tokenCached)) {
            cmrt.setRet(1);
            cmrt.setMsg("用户登录过期或异常");
            return cmrt;
        }
        return cmrt;*/
        return null;
    }

}
