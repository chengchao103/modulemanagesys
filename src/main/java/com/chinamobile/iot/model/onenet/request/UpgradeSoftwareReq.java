package com.chinamobile.iot.model.onenet.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by dds on 2016/6/23.
 */
public class UpgradeSoftwareReq extends BaseRequest {
    private List<Integer> group_ids;
    private List<String>  software_uuids;
//    private final String url="http://<API_ADDRESS>/api/software_activate";
    private final String url="http://<API_ADDRESS>/software_activate";

    public List<Integer> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(List<Integer> group_ids) {
        this.group_ids = group_ids;
    }

    public List<String> getSoftware_uuids() {
        return software_uuids;
    }

    public void setSoftware_uuids(List<String> software_uuids) {
        this.software_uuids = software_uuids;
    }

    @Override
    @JSONField(serialize = false)
    public String getURL() {
        return url;
    }

    @Override
    @JSONField(serialize = false)
    public HTTPMETHOD getMethod() {
        return HTTPMETHOD.POST;
    }
}
