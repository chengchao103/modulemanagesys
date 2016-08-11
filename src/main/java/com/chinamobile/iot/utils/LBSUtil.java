package com.chinamobile.iot.utils;

import com.chinamobile.iot.model.common.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by w on 2016/5/19.
 */
public class LBSUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(LBSUtil.class);
    private static String templateLbsUrl = MainConfig.getStringProperty("lbs.templateLbsUrl") == null ? "" : MainConfig.getStringProperty("lbs.templateLbsUrl");
    private static String sicode = MainConfig.getStringProperty("lbs.sicode") == null ? "" : MainConfig.getStringProperty("lbs.sicode");
    private static HttpURLConnection connection;

    /**
     * 基站定位
     *
     * @param mcc 国家代码：中国代码;460
     * @param mnc 网络类型0移动1联通
     * @param lac 小区号
     * @param cell 基站号
     *
     * @return lng	经度
     * @return lat	纬度
     * @return g_lng	纠偏后的经度(适用于高德/腾讯搜搜/搜狐搜狗/阿里云/灵图/谷歌地图显示)
     * @return g_lat	纠偏后的纬度(适用于高德/腾讯搜搜/搜狐搜狗/阿里云/灵图/谷歌地图显示)
     * @return b_lng	纠偏后的经度(适用于百度/图吧地图显示)
     * @return b_lat	纠偏后的纬度(适用于百度/图吧地图显示)
     */
    public static CommonResult getLocation(String mcc,String mnc,String lac, String cell) {
        CommonResult cmrt = new CommonResult();
        cmrt.setRet(0);
        cmrt.setMsg("获取位置成功");
        String formParam = "mcc=" + mcc + "&mnc=" + mnc + "&lac=" + lac+ "&cell=" + cell + "&apikey=" + sicode;
        StringBuffer sb = new StringBuffer();
        int code = 0;
        try {
            connection = (HttpURLConnection) new URL(templateLbsUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);

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

            LOGGER.info("调用位置接口" + (code == 200 ? "成功" : "失败") + "返回结果：" + sb.toString());
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
