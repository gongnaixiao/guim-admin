package com.sinoiift.service;

import com.sinoiift.domain.Env;
import com.sinoiift.repository.EnvRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xg on 2018/1/3.
 */
@Service
public class EnvService {
    @Autowired
    EnvRepository envRepository;

    public Object selectPage(int page, int size, String search) {
        Pageable pageable = new PageRequest(page, size);
        Page<Env> pages;
        if(StringUtils.isNotBlank(search)) {
            search = "%" + search + "%";
            pages = envRepository.findByNameLike(search, pageable);
        } else {
            pages = envRepository.findAll(pageable);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", pages.getContent());
        map.put("total", pages.getTotalElements());

        return map;
    }

    public void insert(Env env) {
        envRepository.save(env);
    }
    public void update(Env env) {
        envRepository.save(env);
    }

    @Transactional
    public void delete(String[] ids) {
        for (String id : ids) {
            envRepository.delete(Long.parseLong(id));
        }
    }
}
