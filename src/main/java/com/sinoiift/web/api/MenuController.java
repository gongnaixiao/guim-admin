package com.sinoiift.web.api;

import com.sinoiift.domain.result.Rest;
import com.sinoiift.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by xg on 2018/1/4.
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    /**
     * 加载当前用户的菜单
     * @return
     */
    @RequestMapping("/tree")
    public Rest menuTree(){

        List<Map<String, Object>> list = null;
        try {
            list = menuService.getMenuTree();
        } catch (Exception e) {
            return Rest.failure("访问异常");
        }
        return Rest.okData(list);
    }
}
