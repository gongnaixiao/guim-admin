package com.sinoiift.service;

import com.sinoiift.domain.SysEnv;
import com.sinoiift.repository.SysEnvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xg on 2018/1/3.
 */
@Service
public class SysEnvService {
    @Autowired
    SysEnvRepository sysEnvRepository;

    public List<SysEnv> getAll() {
       List<SysEnv> envs = sysEnvRepository.findAll();

        return envs;
    }
}
