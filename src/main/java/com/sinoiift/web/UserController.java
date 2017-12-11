package com.sinoiift.web;

import com.sinoiift.comm.Const;
import com.sinoiift.comm.aop.LoggerManage;
import com.sinoiift.domain.User;
import com.sinoiift.domain.result.ExceptionMsg;
import com.sinoiift.domain.result.Response;
import com.sinoiift.domain.result.ResponseData;
import com.sinoiift.repository.UserRepository;
import com.sinoiift.utils.FileUtil;
import com.sinoiift.utils.MD5Util;
import com.sinoiift.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private UserRepository userRepository;

	@Resource
    private JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	private String mailFrom;
	@Value("${mail.subject.forgotpassword}")
	private String mailSubject;
	@Value("${mail.content.forgotpassword}")
	private String mailContent;
	@Value("${forgotpassword.url}")
	private String forgotpasswordUrl;
	@Value("${static.url}")
	private String staticUrl;
	@Value("${file.profilepictures.url}")
	private String fileProfilepicturesUrl;
	@Value("${file.backgroundpictures.url}")
	private String fileBackgroundpicturesUrl;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@LoggerManage(description="登陆")
	public ResponseData login(User user, HttpServletResponse response) {
		try {
			User loginUser = userRepository.findByUserNameOrEmail(user.getUserName(), user.getUserName());
			if (loginUser == null ) {
				return new ResponseData(ExceptionMsg.LoginNameNotExists);
			}else if(!loginUser.getPassWord().equals(getPwd(user.getPassWord()))){
				return new ResponseData(ExceptionMsg.LoginNameOrPassWordError);
			}
			Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, cookieSign(loginUser.getId().toString()));
			cookie.setMaxAge(Const.COOKIE_TIMEOUT);
			cookie.setPath("/");
			response.addCookie(cookie);
			getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUser);
			String preUrl = "/";

			return new ResponseData(ExceptionMsg.SUCCESS, preUrl);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("login failed, ", e);
			return new ResponseData(ExceptionMsg.FAILED);
		}
	}

	/**
	 * 忘记密码-发送重置邮件
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/sendForgotPasswordEmail", method = RequestMethod.POST)
	@LoggerManage(description="发送忘记密码邮件")
	public Response sendForgotPasswordEmail(String email) {
		try {
			User registUser = userRepository.findByEmail(email);
			if (null == registUser) {
				return result(ExceptionMsg.EmailNotRegister);
			}	
			String secretKey = UUID.randomUUID().toString(); // 密钥
            Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
            long date = outDate.getTime() / 1000 * 1000;
            userRepository.setOutDateAndValidataCode(outDate+"", secretKey, email);
            String key =email + "$" + date + "$" + secretKey;
            String digitalSignature = MD5Util.encrypt(key);// 数字签名
//            String basePath = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" + this.getRequest().getServerPort() + this.getRequest().getContextPath() + "/newPassword";
            String resetPassHref = forgotpasswordUrl + "?sid="
                    + digitalSignature +"&email="+email;
            String emailContent = MessageUtil.getMessage(mailContent, resetPassHref);
	        MimeMessage mimeMessage = mailSender.createMimeMessage();	        
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        helper.setFrom(mailFrom);
	        helper.setTo(email);
	        helper.setSubject(mailSubject);
	        helper.setText(emailContent, true);
	        mailSender.send(mimeMessage);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("sendForgotPasswordEmail failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	
	/**
	 * 忘记密码-设置新密码
	 * @param newpwd
	 * @param email
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/setNewPassword", method = RequestMethod.POST)
	@LoggerManage(description="设置新密码")
	public Response setNewPassword(String newpwd, String email, String sid) {
		try {
			User user = userRepository.findByEmail(email);
			Timestamp outDate = Timestamp.valueOf(user.getOutDate());
			if(outDate.getTime() <= System.currentTimeMillis()){ //表示已经过期
				return result(ExceptionMsg.LinkOutdated);
            }
            String key = user.getEmail()+"$"+outDate.getTime()/1000*1000+"$"+user.getValidataCode();//数字签名
            String digitalSignature = MD5Util.encrypt(key);
            if(!digitalSignature.equals(sid)) {
            	 return result(ExceptionMsg.LinkOutdated);
            }
            userRepository.setNewPassword(getPwd(newpwd), email);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("setNewPassword failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}
	
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@LoggerManage(description="修改密码")
	public Response updatePassword(String oldPassword, String newPassword) {
		try {
			User user = getUser();
			String password = user.getPassWord();
			String newpwd = getPwd(newPassword);
			if(password.equals(getPwd(oldPassword))){
				userRepository.setNewPassword(newpwd, user.getEmail());
				user.setPassWord(newpwd);
				getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
			}else{
				return result(ExceptionMsg.PassWordError);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("updatePassword failed, ", e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}

}