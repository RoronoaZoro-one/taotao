package top.hxq.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import top.hxq.taotao.portal.controller.user.UserController;
import top.hxq.taotao.portal.util.CookieUtils;
import top.hxq.taotao.sso.pojo.User;
import top.hxq.taotao.sso.service.UserService;

/**
 * 订单拦截器
 * 
 * @author wuzm
 * @date 2020年9月9日 上午11:40:00
 */
public class OrderHandlerInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET_NAME);
		if (StringUtils.isNotBlank(ticket)) {
			User user = userService.queryUserByTicket(ticket);
			if (user != null) {
				request.setAttribute("user", user);
				return true;
			}
		}
		// 重定向到登录页面
		String redirectUrl = request.getRequestURL().toString();
		response.sendRedirect(request.getContextPath() + "/page/login.html?redirectUrl=" + redirectUrl);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
