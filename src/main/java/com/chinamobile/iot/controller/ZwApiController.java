package com.chinamobile.iot.controller;

import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.model.response.SimpleResp;
import com.chinamobile.iot.utils.ZwApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by Jolin on 2016/5/26.
 */
@RestController
public class ZwApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZwApiController.class);


    /**
     * 发送短信，同时存入数据库
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/getZwInfo", method = RequestMethod.GET)
    private SimpleResp getModelInfo(@RequestParam(value ="iccid",required =true) String iccid) {
        LOGGER.info("=====getModelInfo  iccid:" + iccid);
        String num="00000001";
        StringBuilder str=new StringBuilder();
        Random r=new Random();
        for(int i=0;i<8;i++){
            str.append(r.nextInt(10));
        }
        num=str.toString();
        SimpleResp resp = new SimpleResp();//898602B2011440084735
         CommonResult cmrt= ZwApi.getModelInfo(str.toString(),iccid);

        if (cmrt.getRet() != 0) {
            resp.setErrno(1);
            resp.setError(cmrt.getMsg());
        }
        resp.setData(cmrt);
        LOGGER.info("专网接口 返回码:{}，返回msg:{}" , cmrt.getRet(),cmrt.getMsg());
        return resp;
    }

}
