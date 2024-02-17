package com.example.mybatis.admin.common.dataAccess.service;


import com.example.mybatis.admin.common.dataAccess.vo.DataAccessVo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 1. Major DB -> DataAccess List
 * 2. Access list -> Mapper Mapping
 */
public interface DataAccessService {


    Map<String, Object> returnMap(Map<String, Object> map) throws DataAccessException;

    void setSession(String db) throws SQLException;

    DataAccessVo getDataAccess(String accessId);

    List<DataAccessVo> getDataAccessList();


}
