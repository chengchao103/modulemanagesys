package com.chinamobile.iot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinamobile.iot.dao.ModuleDao;
import com.chinamobile.iot.model.business.DeviceGroup;
import com.chinamobile.iot.model.business.Module;
import com.chinamobile.iot.model.business.Software;
import com.chinamobile.iot.model.onenet.request.*;
import com.chinamobile.iot.model.onenet.response.GetTokenRsp;
import com.chinamobile.iot.model.onenet.response.SearchProductsRsp;
import com.chinamobile.iot.model.response.CommonMulitiResp;
import com.chinamobile.iot.model.response.CommonSingleResp;
import com.chinamobile.iot.model.response.SimpleResp;
import com.chinamobile.iot.utils.HttpRequestor;
import com.chinamobile.iot.utils.MainConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by dds on 2016/6/12.
 */
@Service
public class GroupService {
    @Autowired
    HttpRequestor httpRequestor = new HttpRequestor();
    @Autowired
    private ModuleDao moduleDao;

    /**
     * 获取token
     *
     * @param user_id
     * @param secret
     * @return
     */
    public boolean getToken(String user_id, String secret) {
        boolean result = false;
        GetTokenReq req = new GetTokenReq();
        req.setUser_id(user_id);
        req.setSecret(secret);
        CommonSingleResp<GetTokenRsp> rsp = httpRequestor.executeSingle(req, new TypeReference<CommonSingleResp<GetTokenRsp>>() {
        });
        if (rsp != null && rsp.getData() != null) {
            BaseRequest.setToken(rsp.getData().getToken());
            BaseRequest.setInvolidTime(System.currentTimeMillis() + rsp.getData().getTimeout() * 1000);
            result = true;
        }
        if (BaseRequest.getProduct_id() == null) {
            BaseRequest.setProduct_id(MainConfig.getStringProperty("onenet.product_id"));
        }
        return result;
    }

    /**
     * 查询产品列表
     *
     * @param page
     * @param perPage
     * @return
     */
    public CommonMulitiResp searchProducts(int page, int perPage) {
        if (!checkToken()) return null;
        SearchProductsReq req = new SearchProductsReq();
        req.setPage(page);
        req.setPer_page(perPage);
        CommonMulitiResp<SearchProductsRsp> rsp = httpRequestor.executeMuliti(req, new TypeReference<CommonMulitiResp<SearchProductsRsp>>() {
        });
        return rsp;
    }

    /**
     * 查下分组列表
     *
     * @param keyword
     * @param page
     * @param per_page
     * @param type
     * @return
     */
    public CommonMulitiResp searchGroups(String keyword, int page, int per_page, int type) {
        if (!checkToken()) return null;
        SeachGroupsReq req = new SeachGroupsReq();
        req.setKeyword(keyword);
        req.setPage(page);
        req.setPer_page(per_page);
        String result = httpRequestor.execute(req);
        CommonMulitiResp cr = JSON.parseObject(result, new TypeReference<CommonMulitiResp<DeviceGroup>>() {
        });
        if (type == 2) {
            int page_count = cr.getData().getTotal_count() / per_page;
            page_count += cr.getData().getTotal_count() % per_page == 0 ? 0 : 1;
            if (page < page_count) {
                cr.getData().getItems().addAll(searchGroups(keyword, ++page, per_page, 2).getData().getItems());
            }
        }

        return cr;
    }

    public String searchGroup(String group_id) {
        if (!checkToken()) return null;
        GetGroupReq req = new GetGroupReq();
        req.setGroup_id(group_id);
        String rsp = httpRequestor.execute(req);
        return rsp;
    }

    public List<Module> searchGroupDevice(Map<String, Object> param) {
        return moduleDao.getAllModulesByGroup(param);
    }

    public int getGroupDeviceCount(Map<String, Object> param) {
        return moduleDao.getModulesCountByGroup(param);
    }

    /**
     * 上传固件
     *
     * @param version
     * @param description
     * @param file
     * @return
     */
    public String uploadSoftware(String version, String description, MultipartFile file, SimpleResp resp) throws Exception {
        if (!checkToken()) {
            resp.setErrno(1);
            resp.setError("企业版接口token错误");
        }
        UploadSoftwareReq req = new UploadSoftwareReq();
        req.setVersion(version);
        req.setFile_name(file.getOriginalFilename());
        req.setDescription(description);
        String rsp = httpRequestor.post(req, file);
        return rsp;
    }

    /**
     * 获取固件列表
     *
     * @param page
     * @param per_page
     * @param keyword
     * @return
     * @throws Exception
     */
    public CommonMulitiResp getSoftware(int page, int per_page, String keyword) {
        if (!checkToken()) return null;
        SearchSoftwareReq req = new SearchSoftwareReq();
        req.setPage(page);
        req.setPer_page(per_page);
        req.setKeyword(keyword);
        String result = httpRequestor.execute(req);
        CommonMulitiResp rsp = JSON.parseObject(result, new TypeReference<CommonMulitiResp<Software>>() {
        });

        int page_count = rsp.getData().getTotal_count() / per_page;
        page_count += rsp.getData().getTotal_count() % per_page == 0 ? 0 : 1;

        if (page < page_count) {
            rsp.getData().getItems().addAll(getSoftware(++page, per_page, null).getData().getItems());
        }

        return rsp;
    }

    /**
     * 固件升级
     *
     * @param groupIds
     * @param uuids
     * @return
     * @throws Exception
     */
    public String upgradeSoftware(String groupIds, String uuids) {
        if (!checkToken()) return null;
        UpgradeSoftwareReq req = new UpgradeSoftwareReq();
        String[] groupId = groupIds.split(",");
        String[] uuid = uuids.split(",");
        List<Integer> gids = new ArrayList<>();
        for (String gid : groupId) {
            gids.add(new Integer(gid));
        }
        List<String> uids = Arrays.asList(uuid);
        req.setGroup_ids(gids);
        req.setSoftware_uuids(uids);
        String str = JSON.toJSONString(req);
        String rsp = httpRequestor.execute(req);
        return rsp;
    }

    private boolean checkToken() {
        if (BaseRequest.getInvolidTime() - System.currentTimeMillis() < 60000) {
            String user_id = MainConfig.getStringProperty("onenet.user");
            String secret = MainConfig.getStringProperty("onenet.secret");
            return getToken(user_id, secret);
        } else {
            return true;
        }
    }


}
