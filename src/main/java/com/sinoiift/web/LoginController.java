package com.sinoiift.web;

import com.sinoiift.comm.aop.LoggerManage;
import com.sinoiift.repository.UserRepository;
import com.sinoiift.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class LoginController extends BaseController{
    @Autowired
    private UserRepository userRepository;
    @Autowired
	private RedisService redisService;


/*	@RequestMapping(value="/",method=RequestMethod.GET)
	@LoggerManage(description="登陆后首页")
	public String home(Model model) {
		long size= collectRepository.countByUserIdAndIsDelete(getUserId(),IsDelete.NO);
		Config config = configRepository.findByUserId(getUserId());
		Favorites favorites = favoritesRepository.findOne(Long.parseLong(config.getDefaultFavorties()));
		List<String> followList = followRepository.findByUserId(getUserId());
		model.addAttribute("config",config);
		model.addAttribute("favorites",favorites);
		model.addAttribute("size",size);
		model.addAttribute("followList",followList);
		model.addAttribute("user",getUser());
		model.addAttribute("newAtMeCount",noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "at", "unread"));
		model.addAttribute("newCommentMeCount",noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "comment", "unread"));
		model.addAttribute("newPraiseMeCount",noticeRepository.countPraiseByUserIdAndReaded(getUserId(), "unread"));
		logger.info("collect size="+size+" userID="+getUserId());
		return "home";
	}*/

	@RequestMapping(value="/login",method= RequestMethod.GET)
	@LoggerManage(description="登陆页面")
	public String login() {
		return "login";
	}
	
/*	@RequestMapping(value="/logout",method=RequestMethod.GET)
	@LoggerManage(description="登出")
	public String logout(HttpServletResponse response,Model model) {
		getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
		Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		IndexCollectorView indexCollectorView = (IndexCollectorView) redisService.getObject("collector");
		if(indexCollectorView==null){
			indexCollectorView = collectorService.getCollectors();
			redisService.setObject("collector", indexCollectorView);
		}
		model.addAttribute("collector",indexCollectorView);
		return "index";
	}*/

	@RequestMapping(value="/forgotPassword",method=RequestMethod.GET)
	@LoggerManage(description="忘记密码页面")
	public String forgotPassword() {
		return "user/forgotpassword";
	}
	
	@RequestMapping(value="/newPassword",method=RequestMethod.GET)
	public String newPassword(String email) {
		return "user/newpassword";
	}

	@RequestMapping(value="/uploadHeadPortrait")
	@LoggerManage(description="上传你头像页面")
	public String uploadHeadPortrait(){
		return "user/uploadheadportrait";
	}

}