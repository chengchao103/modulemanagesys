package com.chinamobile.iot.controller;

import com.chinamobile.iot.dao.ImportDao;
import com.chinamobile.iot.model.business.ImportEntity;
import com.chinamobile.iot.model.business.Module;
import com.chinamobile.iot.model.common.CommonResult;

import com.chinamobile.iot.model.response.SimpleResp;
import com.chinamobile.iot.service.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jolin on 2016/5/13.
 */
@RestController
public class ImportController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportController.class);
    @Autowired
    private ImportService importService;
    @Autowired
    private ImportDao importDao;

    @RequestMapping(value = "/dataImport", method = RequestMethod.POST)
    private SimpleResp upload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        LOGGER.info("=====dataImport====fileName:" + fileName);
        SimpleResp resp = new SimpleResp();
        CommonResult cmrt = new CommonResult();
        InputStream is = null;
        //将请求中的的MultipartFile放入InputStream中
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            resp.setErrno(1);
            resp.setError("file read error");
            e.printStackTrace();
            return resp;
        }
        //根据文件的名称判断用哪种方式解析数据
        List<ImportEntity> list = new ArrayList<ImportEntity>();
        if (fileName.endsWith(".xls")) {
            list = importService.getAllByExcel(is, cmrt);
        } else if (fileName.endsWith(".csv") || fileName.endsWith(".txt")) {
            list = importService.getAllByCSV(is, cmrt);
        }
        //如果解析失败需要返回错误
        if (cmrt.getRet() != 0) {
            resp.setErrno(1);
            resp.setError(cmrt.getMsg());
            return resp;
        }
        //入库
        try {
            importDao.moduleImport(list);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            resp.setErrno(1);
            resp.setError("导入失败,主键'sn'有重复");
            e.printStackTrace();
            return resp;
        }
        catch (Exception e) {
            resp.setErrno(1);
            resp.setError("导入失败,系统错误");
            e.printStackTrace();
            return resp;
        }
        return resp;
    }

}
