package com.chinamobile.iot.model.onenet.response;

/**
 * Created by dds on 2016/6/15.
 */
public class GetTokenRsp {
    private String token;
    private long timeout;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
