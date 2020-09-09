package top.hxq.taotao.portal.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import top.hxq.taotao.portal.util.CookieUtils;
import top.hxq.taotao.sso.pojo.User;
import top.hxq.taotao.sso.service.UserService;

/**
 * 用户管理控制层
 * @author wuzm
 * @date 2020年8月28日 上午11:00:16
 */
@RequestMapping("/user")
@Controller
public class UserController {

	//ticket存放在cookie中的key
	public static final String COOKIE_TICKET_NAME = "TT_TICKET";
	
	//ticket存放在cookie中的存活时间
	private static final int COOKIE_TICKET_MAX_AGE = 60 * 60;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 退出登录
	 * @param user 用户信息
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		String ticket = CookieUtils.getCookieValue(request, COOKIE_TICKET_NAME);
		if(StringUtils.isNotBlank(ticket)) {
			userService.logout(ticket);
		}
		//删除cookie中的ticket
		CookieUtils.deleteCookie(request, response, COOKIE_TICKET_NAME);
		return "redirect:/index.html";
	}
	
	/**
	 * 登录
	 * @param user 用户信息
	 * @return
	 */
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(User user,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String ticket = userService.login(user);
			if(StringUtils.isNotBlank(ticket)) {
				CookieUtils.setCookie(request, response, COOKIE_TICKET_NAME, ticket, COOKIE_TICKET_MAX_AGE);
				result.put("status", 200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

	/**
	 * 注册用户
	 * @param user 用户实体
	 * @return
	 */
	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> register(User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			userService.register(user);
			result.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

}
