package com.chinamobile.iot.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinamobile.iot.model.business.CustomerListEntity;
import com.chinamobile.iot.model.business.Module;
import com.chinamobile.iot.model.business.ModuleDetail;
import com.chinamobile.iot.model.business.Sim;
import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.model.response.SimpleResp;
import com.chinamobile.iot.service.CustomerService;
import com.chinamobile.iot.service.ModuleService;
import com.chinamobile.iot.service.SimService;
import com.chinamobile.iot.utils.ZwApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by Jolin on 2016/5/13.
 */
@RestController
public class ModuleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleController.class);
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private SimService simService;
    @Autowired
    private CustomerService customerService;

    /**
     * 根据sn号查询模组详细信息 包括模组所属的客户信息，模组的sim卡信息（如果有）
     *
     * @param sn
     * @return GetModuleDetailResp
     */
    @RequestMapping(value = "/modules/moduleDetail", method = RequestMethod.GET)
    private SimpleResp getModuleDetail(@RequestParam("sn") String sn) {
        LOGGER.info("=====getModuleDetail====sn:" + sn);
        SimpleResp resp = new SimpleResp();
        ModuleDetail moduleDetail = new ModuleDetail();
        Module module = moduleService.getModuleBySn(sn);
        if (null == module) {
            resp.setErrno(1);
            resp.setError("没有此模组信息，请检查sn号");
            return resp;
        }
        moduleDetail.setModule(module);
        String iccid = module.getIccid();
        //有的模组可能没有iccid
        if (null != iccid && !iccid.equals("")) {
            Sim sim = simService.getSimByIccid(iccid);
            JSONObject info = new JSONObject();
            StringBuilder str=new StringBuilder();
            Random r=new Random();
            for(int i=0;i<8;i++){
                str.append(r.nextInt(10));
            }
            CommonResult cmrt= ZwApi.getModelInfo(str.toString(),iccid);
            LOGGER.info("专网接口:" + cmrt.getMsg());
            if(!"".equals(cmrt.getMsg())){
                info=JSONObject.parseObject(cmrt.getMsg());
                info=JSONObject.parseObject(info.get("result").toString());
                info=JSONObject.parseObject(info.get("gprs").toString());


                sim.setCombo_name(info.get("prodname").toString());
                sim.setCombo_remain(info.get("left").toString());
                LOGGER.info("套餐名称:" +info.get("prodname").toString()+"，套餐余量："+info.get("left").toString());

            }
             moduleDetail.setSim(sim);
        }
        String customer_name = module.getCustomer_name();
        if (null != customer_name && !customer_name.equals("")) {
            CustomerListEntity customerListEntity = customerService.getCustomerDetail(customer_name);
            moduleDetail.setCustomerListEntity(customerListEntity);
        }
        resp.setData(moduleDetail);
        LOGGER.info("===getModuleDetailResp.data==" + resp.getData().toString());
        return resp;
    }

    /**
     *或取下拉框信息
     *
     * @return GetModuleDetailResp
     */
    @RequestMapping(value = "/getSelectFields", method = RequestMethod.GET)
    private SimpleResp getSelectFields() {
        LOGGER.info("=====getSelectFields request====");
        SimpleResp resp = new SimpleResp();
        Map dataMap = new HashMap<>();
        List tempList = new ArrayList<>();
        //行业
        tempList = moduleService.getFields("industry");
        dataMap.put("industry",tempList);
        //套餐
        tempList = moduleService.getFields("combo_name");
        dataMap.put("combo_name",tempList);
        //通讯制式
        tempList = moduleService.getFields("comm_sys");
        dataMap.put("comm_sys",tempList);
        //软件版本
        tempList = moduleService.getFields("app_ver");
        dataMap.put("app_ver",tempList);
        //机卡分离状态
        tempList = moduleService.getFields("separate");
        dataMap.put("status",tempList);

        resp.setData(dataMap);
        LOGGER.info("===getModuleDetailResp.data==" + resp.getData().toString());
        return resp;
    }


}
