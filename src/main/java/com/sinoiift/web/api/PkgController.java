package com.sinoiift.web.api;

import com.sinoiift.comm.aop.LoggerManage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xg on 2018/1/11.
 */

@RestController
@RequestMapping("/pkg")
public class PkgController {

    @LoggerManage(description = "获取所有更新包")
    @RequestMapping(value="/list",method= RequestMethod.GET)
    public void list(@RequestParam int page, @RequestParam int limit, String bug, String pkg) {
        System.out.println(" bug=" + bug + " pkg=" + pkg );
    }
}
