package com.sinoiift.web;

import com.sinoiift.comm.aop.LoggerManage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by xg on 2017/11/24.
 */

@Controller
@RequestMapping("/")
public class HomeController extends BaseController{
    @RequestMapping(value="/",method= RequestMethod.GET)
    @LoggerManage(description="登陆后首页")
    public String home(Model model) {
        model.addAttribute("user",getUser());
        return "home";
    }
}
