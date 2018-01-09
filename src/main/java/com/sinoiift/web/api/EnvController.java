package com.sinoiift.web.api;

import com.sinoiift.comm.aop.LoggerManage;
import com.sinoiift.domain.Env;
import com.sinoiift.domain.result.Rest;
import com.sinoiift.service.EnvService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Object list(@RequestParam int page, @RequestParam int limit, String search) {
        Object obj = service.selectPage(page - 1, limit, search);
        return obj;
    }

    @LoggerManage(description = "新增柜面环境")
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public Rest add(Env env) {
        service.insert(env);

        return Rest.ok();
    }

    @LoggerManage(description = "更新柜面环境")
    @RequestMapping(value="/edit",method= RequestMethod.POST)
    public Rest edit(Env env) {
        service.update(env);

        return Rest.ok();
    }

    @LoggerManage(description = "删除柜面环境")
    @RequestMapping(value="/delete",method= RequestMethod.POST)
    public Rest add(@RequestParam("ids") String[] ids) {
        if(ArrayUtils.isEmpty(ids)){
            return Rest.failure("客户端传入对象id为空");
        }
        service.delete(ids);
        return Rest.ok();
    }
}
