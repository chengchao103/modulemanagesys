package com.chinamobile.iot.model.onenet.request;

import java.util.Map;

/**
 * Created by dds on 2016/6/14.
 */
public class GetTokenReq extends BaseRequest {
    private String user_id;
    private String secret;
//    private String url = "http://<API_ADDRESS>/api/token";
    private String url = "http://<API_ADDRESS>/token";

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public HTTPMETHOD getMethod() {
        return HTTPMETHOD.GET;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public Map<String, String> getParam() {
        return super.getParam();
    }
}
