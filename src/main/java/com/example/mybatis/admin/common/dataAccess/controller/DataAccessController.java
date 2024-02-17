package com.example.mybatis.admin.common.dataAccess.controller;

import com.example.mybatis.admin.common.dataAccess.service.DataAccessException;
import com.example.mybatis.admin.common.dataAccess.service.DataAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/access")
@RequiredArgsConstructor
@Slf4j
public class DataAccessController {

    private final DataAccessService service;

    @PostMapping
    public ResponseEntity access(@RequestBody Map<String, Object> map) throws DataAccessException {
        log.info(map.toString());

        ResponseEntity<Map> ok = ResponseEntity.ok(service.returnMap(map));
        return ok;
    }

    @GetMapping
    public ResponseEntity getDataAccess(@RequestParam(required = false) Map<String, Object> map) {
        log.info(map.toString());

        return new ResponseEntity(service.getDataAccessList(), HttpStatus.OK);
    }


}
