package com.chinamobile.iot.service;

import com.chinamobile.iot.model.business.ImportEntity;
import com.chinamobile.iot.model.business.Module;
import com.chinamobile.iot.model.common.CommonResult;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Jolin on 2016/5/13.
 */
public interface ImportService {

    /**
     * 得到Excel文件所有数据
     * @param is，cmrt
     * @return List<ModuleFromImport>
     */
    public List<ImportEntity> getAllByExcel(InputStream is, CommonResult cmrt);

    public List<ImportEntity> getAllByCSV(InputStream is, CommonResult cmrt);
}
