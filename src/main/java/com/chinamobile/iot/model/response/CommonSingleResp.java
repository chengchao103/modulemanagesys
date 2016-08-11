package com.chinamobile.iot.model.response;

/**
 * Created by dds on 2016/6/12.
 */
public class CommonSingleResp<T> extends BaseResp {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
