package com.chinamobile.iot.model.business.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jolin on 2016/6/8.
 */
public class LocationEntity {
    private int type;
    private List catalog;
    private Object devices;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getDevices() {
        return devices;
    }

    public void setDevices(Object devices) {
        this.devices = devices;
    }

    public List getCatalog() {
        return catalog;
    }

    public void setCatalog(List catalog) {
        this.catalog = catalog;
    }

    //整理
    public void arrange() {
        Map<Object,List> arranged = new HashMap();
        List devices = (List) this.getDevices();
        List catalog = new ArrayList();
        for (Object a : devices) {
            Map ma = (Map) a;
            Object key = ma.get("identifier");
            ma.remove("identifier");
           if(null!= arranged.get(key)&&arranged.get(key).size()>0){
               arranged.get(key).add(ma);
           }else{
               List newListForArranged = new ArrayList();
               newListForArranged.add(ma);
               arranged.put(key,newListForArranged);
               catalog.add(key);
               this.setCatalog(catalog);
           }

        }
        this.devices = arranged;
    }
}
