package com.chinamobile.iot.model.onenet.request;

/**
 * Created by dds on 2016/6/21.
 */
public class SearchSoftwareReq extends BaseRequest {
    private int page;
    private int per_page;
    private String keyword;
//    private final String url="http://<API_ADDRESS>/api/software";
    private final String url="http://<API_ADDRESS>/software";

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public HTTPMETHOD getMethod() {
        return HTTPMETHOD.GET;
    }
}
