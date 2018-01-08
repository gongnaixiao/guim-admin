package com.sinoiift.web.api;

import com.sinoiift.comm.aop.LoggerManage;
import com.sinoiift.service.EnvService;
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
@RequestMapping("/env")
public class EnvController {
    @Autowired
    private EnvService service;

    @LoggerManage(description = "获取柜面环境")
    @RequestMapping(value="/list",method= RequestMethod.GET)
    public Object env() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", service.getAll());
        map.put("total", 1);
        return map;
    }
}
