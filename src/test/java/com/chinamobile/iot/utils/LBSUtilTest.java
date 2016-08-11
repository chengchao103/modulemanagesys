package com.chinamobile.iot.utils;

import com.chinamobile.iot.model.common.CommonResult;
import org.junit.Test;

/**
 * Created by Jolin on 2016/5/16.
 */
public class LBSUtilTest {

    @Test
    public void getLocation() throws Exception {
        CommonResult cmrt = LBSUtil.getLocation("460", "0", "13176","12071");

        System.out.println(cmrt.getRet());
        System.out.println(cmrt.getMsg());
    }
}