package com.chinamobile.iot.model.onenet.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dds on 2016/6/22.
 */
public class GetGroupReq extends BaseRequest {

//    private String url="http://<API_ADDRESS>/api/group/<group_id>";
    private String url="http://<API_ADDRESS>/group/<group_id>";
    private String group_id;

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
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
        HashMap<String, String> param=new HashMap<>();
        param.put("group_id",this.group_id);
        return param;
    }
}
