package com.chinamobile.iot.model.onenet.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

/**
 * Created by dds on 2016/6/12.
 */
public class BaseRequest {
    private static String token;
    private static String product_id;
    private static long involidTime;
    public enum HTTPMETHOD
    {
        GET, POST, PUT, DELETE
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BaseRequest.token = token;
    }

    public static String getProduct_id() {
        return product_id;
    }

    public static void setProduct_id(String product_id) {
        BaseRequest.product_id = product_id;
    }

    public static long getInvolidTime() {
        return involidTime;
    }

    public static void setInvolidTime(long involidTime) {
        BaseRequest.involidTime = involidTime;
    }


    @JSONField(serialize = false)
    public HTTPMETHOD getMethod() {
        return null;
    }

    @JSONField(serialize = false)
    public  String getURL() {
        return null;
    }

    @JSONField(serialize = false)
    public Map<String, String> getParam() {
        return null;
    }

}
