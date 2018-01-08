package com.sinoiift.service;

import com.sinoiift.domain.Env;
import com.sinoiift.repository.EnvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xg on 2018/1/3.
 */
@Service
public class EnvService {
    @Autowired
    EnvRepository envRepository;

    public List<Env> getAll() {
        List<Env> envs = envRepository.findAll();

        return envs;
    }
}
