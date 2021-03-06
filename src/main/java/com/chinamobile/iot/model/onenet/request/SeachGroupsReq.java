package com.chinamobile.iot.model.onenet.request;

/**
 * Created by dds on 2016/6/15.
 */
public class SeachGroupsReq extends BaseRequest {

    private String keyword;
    private int page;
    private int per_page;
//    private String url="http://<API_ADDRESS>/api/group";
    private String url="http://<API_ADDRESS>/group";

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    @Override
    public HTTPMETHOD getMethod() {
        return HTTPMETHOD.GET;
    }

    @Override
    public String getURL() {
        return url;
    }
}
