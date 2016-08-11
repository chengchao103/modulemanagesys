package com.chinamobile.iot.service.Impl;

import com.chinamobile.iot.model.business.ImportEntity;
import com.chinamobile.iot.model.common.CommonResult;
import com.chinamobile.iot.service.ImportService;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jolin on 2016/5/13.
 */
@Service("importService")
public class ImportServiceImpl implements ImportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportServiceImpl.class);

    @Override
    public List<ImportEntity> getAllByExcel(InputStream is, CommonResult cmrt) {
        List<ImportEntity> list = new ArrayList<ImportEntity>();
        try {
            Workbook rwb = Workbook.getWorkbook(is);
            //Sheet rs=rwb.getSheet("Test Shee 1");//或者rwb.getSheet(0)
            Sheet rs = rwb.getSheet(0);
            int clos = rs.getColumns();//得到所有的列
            int rows = rs.getRows();//得到所有的行
            LOGGER.info("===clos===" + clos + " rows:" + rows);
            Date now = new Date();
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                    ImportEntity entity = new ImportEntity();
                    //第一个是列数，第二个是行数
                    String sn = rs.getCell(j++, i).getContents();//默认最左边编号也算一列 所以这里得j++
                    if (sn.length() < 1) {
                        cmrt.setRet(1);
                        cmrt.setMsg("PRIMARY key should not be null");
                        LOGGER.error("===主键不能为空====");
                        break;
                    }
                    String customer_name = rs.getCell(j++, i).getContents();
                    String industry = rs.getCell(j++, i).getContents();
                    String province = rs.getCell(j++, i).getContents();
                    String imei = rs.getCell(j++, i).getContents();
                    String type = rs.getCell(j++, i).getContents();
                    String comm_sys = rs.getCell(j++, i).getContents();
                    String app_ver = rs.getCell(j++, i).getContents();
                    String iccid = rs.getCell(j++, i).getContents();
                    String combo_name = rs.getCell(j++, i).getContents();
                    String prom_online_time = rs.getCell(j++, i).getContents();
                    entity.setSn(sn);
                    entity.setCustomer_name(customer_name);
                    entity.setIndustry(industry);
                    entity.setProvince(province);
                    entity.setImei(imei);
                    entity.setModule_type(type);
                    entity.setComm_sys(comm_sys);
                    entity.setApp_ver(app_ver);
                    entity.setIccid(iccid);
                    entity.setCombo_name(combo_name);
                    entity.setProm_online_time(prom_online_time);
                    entity.setImport_time(now);
                    LOGGER.info("==="+entity.toString());
                    list.add(entity);
                }
            }
        } catch (Exception e) {
            cmrt.setRet(1);
            cmrt.setMsg("excle data load error");
            LOGGER.error("===excle数据导入失败====");
            e.printStackTrace();
        }
        return list;


    }

    @Override
    public List<ImportEntity> getAllByCSV(InputStream is, CommonResult cmrt) {
        cmrt.setRet(0);
        List<ImportEntity> list = new ArrayList<ImportEntity>();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            Date now = new Date();
            String line = "";
            int lineNum = 0;
            while ((line = br.readLine()) != null) {

                lineNum++;
                //第一行为标题
                if (lineNum == 1) {
                    continue;
                } else {
                    ImportEntity entity = new ImportEntity();
                    if (line.contains(",")) {
                        String Columns[] = line.split(",");
                        entity.setSn(Columns[0]);
                        entity.setCustomer_name(Columns[1]);
                        entity.setIndustry(Columns[2]);
                        entity.setProvince(Columns[3]);
                        entity.setImei(Columns[4]);
                        entity.setModule_type(Columns[5]);
                        entity.setComm_sys(Columns[6]);
                        entity.setApp_ver(Columns[7]);
                        entity.setIccid(Columns[8]);
                        entity.setCombo_name(Columns[9]);
                        entity.setProm_online_time(Columns[10]);
                        entity.setImport_time(now);
                    } else {
                        cmrt.setRet(1);
                        cmrt.setMsg("data read error");
                    }
                    LOGGER.info("==="+entity.toString());
                    list.add(entity);

                }

            }
        } catch (Exception e) {
            cmrt.setRet(1);
            cmrt.setMsg("data read error");
            e.printStackTrace();
        }
        return list;
    }

}
