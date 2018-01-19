package com.sinoiift.web.api;

import com.sinoiift.comm.aop.LoggerManage;
import com.sinoiift.domain.App;
import com.sinoiift.domain.Env;
import com.sinoiift.domain.result.Rest;
import com.sinoiift.domain.view.EnvApp;
import com.sinoiift.repository.AppRepository;
import com.sinoiift.repository.EnvRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xg on 2018/1/3.
 */
@RestController
public class EnvController {
    @Autowired
    private EnvRepository envRepository;
    @Autowired
    private AppRepository appRepository;

    @LoggerManage(description = "获取柜面环境")
    @GetMapping("/env/list")
    @Transactional
    public Object list(@RequestParam int page, @RequestParam int limit, String search) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<EnvApp> pages;
        if(StringUtils.isNotBlank(search)) {
            search = "%" + search + "%";
            pages = appRepository.findEnvsLike(search, pageable);
        } else {
            pages = appRepository.findEnvs(pageable);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", pages.getContent());
        map.put("total", pages.getTotalElements());

        return map;
    }

    @LoggerManage(description = "新增柜面环境")
    @PostMapping("/env/add")
    @Transactional
    public Rest add(EnvApp envApp) {
        Env env = new Env();
        App app = new App();
        try {
            BeanUtils.copyProperty(env, "name", envApp.getName());
            BeanUtils.copyProperties(app, envApp);
        } catch (Exception e) {
            return Rest.failure(e.getMessage());
        }

        Env tmp = envRepository.findByName(envApp.getName());
        if (tmp == null) {
            tmp = envRepository.save(env);
        }
        app.setEnvId(tmp.getId());
        appRepository.save(app);

        return Rest.ok();
    }

    @LoggerManage(description = "更新柜面环境")
    @PostMapping("/env/edit")
    @Transactional
    public Rest edit(EnvApp envApp) {
        add(envApp);

        return Rest.ok();
    }

    @LoggerManage(description = "删除柜面环境")
    @PostMapping("/env/delete")
    @Transactional
    public Rest delete(@RequestParam("ids") long[] ids) {
        if(ArrayUtils.isEmpty(ids)){
            return Rest.failure("客户端传入对象id为空");
        }
        for (long id : ids) {
            App app = appRepository.findOne(id);
            if(appRepository.countByEnvId(app.getEnvId()) == 1) {
                envRepository.delete(app.getEnvId());
            }
            appRepository.delete(id);
        }
        return Rest.ok();
    }

    @LoggerManage(description = "获取柜面环境名")
    @GetMapping("/env/envNames")
    public Object listNames() {
        Map<String, Object> map = new HashMap();
        List<Env> envs = envRepository.findAll();

        map.put("data", envs);
        return map;
    }
}
