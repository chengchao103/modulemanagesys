package com.chinamobile.iot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jolin on 2016/5/19.
 */
public class AuthenticationUtil {
    //产生access_token
    public static String generateAT(String userName) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowStr = sdf.format(now);
        String at = MD5.md5(nowStr + userName);
        return at;

    }

    public static String generateSmsCode() {
        int code = (int)((Math.random()*9+1)*100000);
        return String.valueOf(code);
    }
}
