package com.chinamobile.iot.controller;

import com.chinamobile.iot.model.business.DeviceGroup;
import com.chinamobile.iot.model.business.Group;
import com.chinamobile.iot.model.business.Module;
import com.chinamobile.iot.model.response.CommonMulitiResp;
import com.chinamobile.iot.model.response.SimpleResp;
import com.chinamobile.iot.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dds on 2016/6/22.
 */
@RestController
public class GroupController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    /**
     * 查询设备组列表
     *
     * @param groupName
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public CommonMulitiResp getDeviceGroups(
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pagesize", required = false) Integer pageSize,
            @RequestParam(value = "type", required = true) Integer type) {
        LOGGER.info("Receive getDeviceGroups Request,groupName={}, page={},pageSize={}", groupName, page, pageSize);
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        CommonMulitiResp rsp = groupService.searchGroups(groupName, page, pageSize, type);
        //type为2时,只返回id和name
        if (type == 2) {
            List<Group> groups = new ArrayList<>();
            for (Object item : rsp.getData().getItems()) {
                DeviceGroup dg = (DeviceGroup) item;
                groups.add(new Group(dg.getId(), dg.getName()));
            }
            rsp.getData().setItems(groups);
        }
        return rsp;
    }

    /**
     * 查询设备组详情
     *
     * @param group_id
     * @return
     */
    @RequestMapping(value = "/group/{group_id}", method = RequestMethod.GET)
    public String getGroupDetail(@PathVariable("group_id") String group_id) {
        LOGGER.info("Receive getDeviceGroups Request,group_id={}", group_id);
        String rsp = groupService.searchGroup(group_id);

        return rsp;
    }

    /**
     * 查询设备分组下的设备
     *
     * @param group_name
     * @param customer_name
     * @param industry
     * @param comm_sys
     * @param combo_name
     * @param separate
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getGroupDevices", method = RequestMethod.GET)
        public SimpleResp getGroupDevice(@RequestParam("group_name") String group_name,
                                     @RequestParam(value = "customer_name", required = false) String customer_name,
                                     @RequestParam(value = "industry", required = false) String industry,
                                     @RequestParam(value = "comm_sys", required = false) String comm_sys,
                                     @RequestParam(value = "combo_name", required = false) String combo_name,
                                     @RequestParam(value = "separate", required = false) String separate,
                                     @RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "pagesize", required = false) Integer pageSize
    ) {
        LOGGER.info("Receive getGroupDevice Request,group_name={},custName={},trade={},communication={},setMeal={},separate={}, page={},pageSize={}",
                group_name, customer_name, industry, comm_sys, combo_name, separate, page, pageSize);
        Integer pageStart;
        if (page != null && pageSize != null) {
            pageStart = (page - 1) * pageSize;
        } else {
            // 如果没有设置，就默认为10行
            page = 1;
            pageSize = 10;
            pageStart = 0;
        }
        SimpleResp resp = new SimpleResp();
        HashMap<String, Object> param = new HashMap<>();
        param.put("app_ver", group_name);
        param.put("customer_name", customer_name);
        param.put("industry", industry);
        param.put("comm_sys", comm_sys);
        param.put("combo_name", combo_name);
        param.put("separate", separate);
        param.put("pageStart", pageStart);
        param.put("pagesize", pageSize);

        List<Module> moduleList = groupService.searchGroupDevice(param);
        int count = groupService.getGroupDeviceCount(param);
        Map dataMap = new HashMap<>();
        dataMap.put("total_size", count);
        dataMap.put("list", moduleList);
        resp.setData(dataMap);
        return resp;
    }

    /**
     * 查询固件列表
     *
     * @return
     */
    @RequestMapping(value = "/softwareList", method = RequestMethod.GET)
    public CommonMulitiResp getSoftware() {
        LOGGER.info("Receive get software list Request");
        CommonMulitiResp rsp = groupService.getSoftware(1, 30, null);

        return rsp;
    }

    /**
     * 上传固件
     *
     * @return
     */
    @RequestMapping(value = "/uploadSoftware", method = RequestMethod.POST)
    public SimpleResp uploadSoftware(
            @RequestParam(value = "version", required = true) String version,
            @RequestParam(value = "desc", required = true) String description,
            @RequestParam(value = "file", required = true) MultipartFile file) throws Exception {
        LOGGER.info("Receive upload software Request,version={},desc={}", version, description);
        SimpleResp resp = new SimpleResp();
        groupService.uploadSoftware(version, description, file, resp);

        return resp;
    }

    /**
     * 固件升级
     * @param groupIds
     * @param uuIds
     * @return
     */
    @RequestMapping(value = "/upgradeSoftware", method = RequestMethod.GET)
    public String upgradeSoftware(@RequestParam(value = "groupIds",required = true) String groupIds,@RequestParam(value = "uuIds",required = true) String uuIds) {
        LOGGER.info("Receive upgrade software Request,groupIds={},uuids={}", groupIds, uuIds);
        String rsp = groupService.upgradeSoftware(groupIds, uuIds);
        return rsp;
    }


}
