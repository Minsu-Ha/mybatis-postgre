package com.example.mybatis.admin.common.dataAccess.dao;

import com.example.mybatis.admin.common.dataAccess.vo.DataAccessVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DataAccessDaoImpl implements DataAccessDao{
    @Override
    public DataAccessVo selectDataAccess(SqlSession session, String accessId) {
        return session.selectOne("DataAccessMapper.selectDataAccess", accessId);
    }

    @Override
    public List<DataAccessVo> selectDataAccess(SqlSession session) {
        return session.selectList("DataAccessMapper.selectDataAccess");
    }

    @Override
    public DataAccessVo insertDataAccess(SqlSession session, DataAccessVo dataAccessVo) {
        return null;
    }

    @Override
    public DataAccessVo updateDataAccess(SqlSession session, DataAccessVo dataAccessVo) {
        return null;
    }

    @Override
    public DataAccessVo deleteDataAccess(SqlSession session, DataAccessVo dataAccessVo) {
        return null;
    }

    @Override
    public List<Map<String, Object>> select(SqlSession session, Map<String, Object> map) {
        return session.selectList((String) map.get("path"), (Map<String, Object>) map.get("param"));
    }

    @Override
    public int insert(SqlSession session, Map<String, Object> map) {
        return session.insert((String) map.get("path"), (Map<String, Object>) map.get("param"));
    }

    @Override
    public int update(SqlSession session, Map<String, Object> map) {
        return session.update((String) map.get("path"), (Map<String, Object>) map.get("param"));
    }

    @Override
    public int delete(SqlSession session, Map<String, Object> map) {
        return session.delete((String) map.get("path"), (Map<String, Object>) map.get("param"));
    }


}
