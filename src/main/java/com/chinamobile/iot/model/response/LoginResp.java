package com.chinamobile.iot.model.response;

import com.chinamobile.iot.model.business.Credentials;

/**
 * Created by Jolin on 2016/5/19.
 */
public class LoginResp extends BaseResp {
    private Credentials data;

    public Credentials getData() {
        return data;
    }

    public void setData(Credentials data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data[" +
                data +
                ']';
    }
}
