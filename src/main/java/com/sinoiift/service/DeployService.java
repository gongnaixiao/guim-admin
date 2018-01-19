package com.sinoiift.service;

import com.sinoiift.domain.App;

/**
 * Created by xg on 2018/1/18.
 */
public interface DeployService {
    void executeDeploy(String file, App app) throws Exception;
}
