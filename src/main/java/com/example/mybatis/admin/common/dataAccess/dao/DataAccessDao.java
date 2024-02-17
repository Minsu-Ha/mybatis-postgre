package com.example.mybatis.admin.common.dataAccess.dao;


import com.example.mybatis.admin.common.dataAccess.vo.DataAccessVo;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public interface DataAccessDao {

    DataAccessVo selectDataAccess(SqlSession session, String accessId);

    List<DataAccessVo> selectDataAccess(SqlSession session);

    DataAccessVo insertDataAccess(SqlSession session, DataAccessVo dataAccessVo);

    DataAccessVo updateDataAccess(SqlSession session, DataAccessVo dataAccessVo);

    DataAccessVo deleteDataAccess(SqlSession session, DataAccessVo dataAccessVo);

    List<Map<String, Object>> select(SqlSession session, Map<String, Object> map);

    int insert(SqlSession session, Map<String, Object> map);

    int update(SqlSession session, Map<String, Object> map);

    int delete(SqlSession session, Map<String, Object> map);

}
