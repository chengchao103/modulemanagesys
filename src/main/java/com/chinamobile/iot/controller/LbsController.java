package com.chinamobile.iot.controller;

import com.alibaba.fastjson.JSON;
import com.chinamobile.iot.model.business.LbsInfo;
import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.model.response.BaseResp;
import com.chinamobile.iot.model.response.SimpleResp;
import com.chinamobile.iot.service.LbsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jolin on 2016/5/26.
 */
@RestController
public class LbsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LbsController.class);

    @Autowired
    private LbsService lbsService;

    /**
     * 获取位置信息
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "lbs/getLocation", method = RequestMethod.GET)
    private String  getLocation(@RequestParam("mcc") String mcc, @RequestParam("mnc") String mnc,
             @RequestParam("lac") String lac, @RequestParam("cell") String cell) {

        SimpleResp resp = new SimpleResp();
        CommonResult cmrt = new CommonResult();
        LbsInfo lbs=new LbsInfo( mcc,  mnc,  lac,  cell);
        LOGGER.info("=====getLocation====lbs:" + lbs.toString());
        cmrt=lbsService.getLocation(lbs);
        if (cmrt.getRet() != 0) {
            resp.setErrno(1);
            resp.setError(cmrt.getMsg());
            return JSON.toJSONString(resp);
        }
        resp.setData(cmrt.getMsg());
        return JSON.toJSONString(resp);
    }

}
