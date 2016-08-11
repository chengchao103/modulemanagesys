package com.chinamobile.iot.service;

import com.chinamobile.iot.model.business.Sim;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
public interface SimService {
    public Sim getSimByIccid(String iccid);

}
