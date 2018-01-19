package com.sinoiift.service;

import com.sinoiift.domain.App;

import java.util.List;

/**
 * Created by xg on 2018/1/18.
 */
public interface ImpSqlFileService {
    void executeSql(List<String> lis, String workspace, App app) throws Exception;
}
