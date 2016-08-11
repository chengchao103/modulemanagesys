package com.chinamobile.iot.service.Impl;

import com.chinamobile.iot.dao.SimDao;
import com.chinamobile.iot.model.business.Sim;
import com.chinamobile.iot.service.SimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jolin on 2016/5/17.
 */
@Service("simService")
public class SimServiceImpl implements SimService {
    @Autowired
    private SimDao simDao;

    @Override
    public Sim getSimByIccid(String iccid) {
        return simDao.getSimByIccid(iccid);
    }
}
