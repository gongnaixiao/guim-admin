package com.sinoiift.web.api;

import com.sinoiift.comm.aop.LoggerManage;
import com.sinoiift.service.SysEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xg on 2018/1/3.
 */
@RestController
@RequestMapping("/sys")
public class SysController {
    @Autowired
    private SysEnvService service;

    @LoggerManage(description = "获取柜面环境")
    @RequestMapping(value="/env",method= RequestMethod.GET)
    public Object sysEnv() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", service.getAll());
        return map;
    }
}
