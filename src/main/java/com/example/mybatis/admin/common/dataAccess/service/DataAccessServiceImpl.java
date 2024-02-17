package com.example.mybatis.admin.common.dataAccess.service;

import com.example.mybatis.admin.common.dataAccess.dao.DataAccessDao;
import com.example.mybatis.admin.common.dataAccess.vo.DataAccessVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataAccessServiceImpl implements DataAccessService {

    private final SqlSessionTemplate primarySession;
    private final SqlSessionTemplate secondarySession;
    private final DataAccessDao dao;

    private final PlatformTransactionManager primaryTransactionManager;

    private final PlatformTransactionManager secondaryTransactionManager;
    private SqlSessionTemplate session = null;
    private TransactionTemplate transactionTemplate;

    @Override
    public Map returnMap(Map map) throws DataAccessException {
        log.info("Request Param: {}", map.toString());

        DataAccessVo dataAccessVo = getDataAccess((String) map.get("accessId"));
        // AccessId가 없으면 throw
        if (dataAccessVo.getAccessId() != null && dataAccessVo.getAccessId().isEmpty()) {
            throw new DataAccessException("Has No Access ID");
        }

        String db = dataAccessVo.getDb();


        map.put("path", dataAccessVo.getMapper());

        setSession(db);

        if (this.session == null) {
            throw new DataAccessException("Not Connect Session");
        }

        return transactionTemplate.execute(status -> {
            log.info(excecuteQuery(map).toString());
            return excecuteQuery(map);
        });
    }

    /**
     * @param map 자료 구조
     *            {
     *            accessId: "[access id]"
     *            type: "type(C,R,U,D)"
     *            path: "[mapper path by DB]"
     *            param: Map
     *            }
     */
    private Map<String, Object> excecuteQuery(Map map) {
        int result = 0;

        if (map.get("type").equals("C")) {
            result += dao.insert(session, map);

        }

        if (map.get("type").equals("R")) {
            map.put("result", dao.select(session, map));
        }

        if (map.get("type").equals("U")) {
            result += dao.update(session, map);
        }

        if (map.get("type").equals("D")) {
            result += dao.delete(session, map);
        }

        map.put("cudResultCnt", result);
        return map;
    }

    @Override
    public void setSession(String db) {
        this.session = primarySession;
        transactionTemplate = new TransactionTemplate(primaryTransactionManager);
        if (db.equals("secondary")) {
            this.session = secondarySession;
            transactionTemplate = new TransactionTemplate(secondaryTransactionManager);
        }

    }

    @Override
    public DataAccessVo getDataAccess(String accessId) {
        return dao.selectDataAccess(primarySession, accessId);
    }

    @Override
    public List<DataAccessVo> getDataAccessList() {
        return dao.selectDataAccess(primarySession);
    }
}
