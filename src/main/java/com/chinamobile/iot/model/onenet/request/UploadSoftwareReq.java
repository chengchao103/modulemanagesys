package com.chinamobile.iot.model.onenet.request;

/**
 * Created by dds on 2016/6/20.
 */
public class UploadSoftwareReq extends BaseRequest{
    private String version;
    private String file_name;
    private String description;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public HTTPMETHOD getMethod() {
        return HTTPMETHOD.POST;
    }


}
