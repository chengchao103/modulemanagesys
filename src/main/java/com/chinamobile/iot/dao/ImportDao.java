package com.chinamobile.iot.dao;

import com.chinamobile.iot.model.business.ImportEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Jolin on 2016/5/13.
 */
public interface ImportDao {
   void moduleImport(@Param("list") List<ImportEntity> list);
}
