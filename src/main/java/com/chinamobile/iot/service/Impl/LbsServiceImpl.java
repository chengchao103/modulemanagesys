package com.chinamobile.iot.service.Impl;

import com.chinamobile.iot.model.business.LbsInfo;
import com.chinamobile.iot.model.business.SmsInfo;
import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.service.LbsService;
import com.chinamobile.iot.service.SmsService;
import com.chinamobile.iot.utils.AuthenticationUtil;
import com.chinamobile.iot.utils.LBSUtil;
import com.chinamobile.iot.utils.MainConfig;
import com.chinamobile.iot.utils.SMSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Jolin on 2016/5/26.
 */
@Service("lbsService")
public class LbsServiceImpl implements LbsService {

    @Override
    public CommonResult getLocation(LbsInfo lbs ) {
      return  LBSUtil.getLocation(lbs.getMcc(),lbs.getMnc(),lbs.getLac(),lbs.getCell());
    }



}
