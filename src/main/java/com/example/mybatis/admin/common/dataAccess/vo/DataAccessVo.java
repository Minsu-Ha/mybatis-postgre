package com.example.mybatis.admin.common.dataAccess.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@Builder
public class DataAccessVo{

    private String accessId;
    private String db;
    private String mapper;
    private String description;
    private Date crtTime;
    private Date udtTime;

}
