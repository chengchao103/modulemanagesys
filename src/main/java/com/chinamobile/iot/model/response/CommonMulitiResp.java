package com.chinamobile.iot.model.response;

import java.util.List;

/**
 * Created by dds on 2016/6/22.
 */
public class CommonMulitiResp<T> extends BaseResp {

    Data<T> data;

    public Data<T> getData() {
        return data;
    }

    public void setData(Data<T> data) {
        this.data = data;
    }

    public static class Data <T> {
        private int total_count;
        private int pageNo;
        private int pageSize;
        List<T> items;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<T> getItems() {
            return items;
        }

        public void setItems(List<T> item) {
            this.items = item;
        }
    }

}
