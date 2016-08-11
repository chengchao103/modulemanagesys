package com.chinamobile.iot.utils;

import com.chinamobile.iot.model.common.CommonResult;
import org.junit.Test;

/**
 * Created by Jolin on 2016/5/16.
 */
public class SMSUtilTest {

    @Test
    public void send() throws Exception {
        CommonResult cmrt = SMSUtil.send("18723305397","10525","code=1234");
    }
}