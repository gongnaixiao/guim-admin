package com.sinoiift.web;

import com.sinoiift.comm.aop.LoggerManage;
import com.sinoiift.domain.User;
import com.sinoiift.domain.result.Result;
import com.sinoiift.repository.UserRepository;
import com.sinoiift.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class LoginController extends BaseController{
    @Autowired
    private UserRepository userRepository;
    @Autowired
	private RedisService redisService;


	@RequestMapping(value="/login",method= RequestMethod.GET)
	@LoggerManage(description= "登陆页面")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@LoggerManage(description="验证登陆")
	@ResponseBody
	public Object login(User user) {
		return new Result(true, "登陆成功");
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@LoggerManage(description="主界面")
	public String index() { return "index"; }
}