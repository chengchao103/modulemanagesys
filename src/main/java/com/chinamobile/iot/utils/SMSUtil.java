package com.chinamobile.iot.utils;

import com.chinamobile.iot.model.common.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jolin on 2016/5/16.
 */
public class SMSUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SMSUtil.class);
    private static String templateSmsUrl = MainConfig.getStringProperty("sms.templateSmsUrl") == null ? "" : MainConfig.getStringProperty("sms.templateSmsUrl");
    private static String sicode = MainConfig.getStringProperty("sms.sicode") == null ? "" : MainConfig.getStringProperty("sms.sicode");
    private static HttpURLConnection connection;

    /**
     * @param mobiles     短信接收号码,最大100条,逗号分割
     * @param tempid      模板ID,创建模板时生成的ID,需要审核通过
     * @param tempParamKV 模板里面的参数key-Value键值对,多个键值对需要以&连接
     * @return CommonResult
     */

    public static CommonResult send(String mobiles, String tempid, String tempParamKV) {
        LOGGER.info("=====sendMessage request start====mobile:" + mobiles);
        StopWatch watch = new StopWatch();
        watch.start();
        CommonResult cmrt = new CommonResult();
        cmrt.setRet(0);
        cmrt.setMsg("短信发送成功");
        String formParam = "sicode=" + sicode + "&mobiles=" + mobiles + "&tempid=" + tempid + "&" + tempParamKV;
        StringBuffer sb = new StringBuffer();
        int code = 0;
        try {
            connection = (HttpURLConnection) new URL(templateSmsUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.getOutputStream().write(formParam.getBytes());
            connection.getOutputStream().close();
            code = connection.getResponseCode();
            InputStream stream = null;
            if (code == 200) {
                stream = connection.getInputStream();
            } else {
                stream = connection.getErrorStream();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "utf-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            watch.stop();
            LOGGER.info("调用模板短信接口" + (code == 200 ? "成功" : "失败") + "返回结果：" + sb.toString());
            LOGGER.info("=====sendMessage request end==== used {}ms:",watch.getLastTaskTimeMillis());
        } catch (IOException e) {
            cmrt.setRet(1);
            cmrt.setMsg("http连接异常");
            e.printStackTrace();
            return cmrt;
        }
        cmrt.setMsg(sb.toString());
        if (code != 200) {
            cmrt.setRet(1);
        }
        return cmrt;

    }


}
