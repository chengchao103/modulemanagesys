package com.chinamobile.iot.model.response;

/**
 * Created by Jolin on 2016/5/13.
 */
public abstract class BaseResp {
    /**
     * 错误码
     */
    private int errno=0;
    /**
     * 响应结果，可能为succ或者失败原因
     */
    private String error="succ";


    /**
     * 成功时，返回该二进制数据的标识号
     */
    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "BaseRsp [errno=" + errno + ", error=" + error + "]";
    }
}
