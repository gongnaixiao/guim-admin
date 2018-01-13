package com.sinoiift.service;

import com.sinoiift.domain.Menu;
import com.sinoiift.repository.MenuRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xg on 2018/1/4.
 */
@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;

    public List<Map<String, Object>> getMenuTree() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Menu> menus = menuRepository.findAll();
        List<Map<String, Object>> list = new ArrayList();
        for (Menu menu : menus) {
            Map menuMap = BeanUtils.describe(menu);
            list.add(menuMap);
        }
        return list;
    }
}
