package top.hxq.taotao.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import top.hxq.taotao.cart.service.CartService;
import top.hxq.taotao.portal.controller.user.UserController;
import top.hxq.taotao.portal.util.CookieUtils;
import top.hxq.taotao.sso.pojo.User;
import top.hxq.taotao.sso.service.UserService;

/**
 * 订单控制层
 * @author wuzm
 * @date 2020年9月9日 上午9:21:17
 */
@RequestMapping("/order")
@Controller
public class OrderController {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	/**
	 * 跳转到订单页面,携带购物车列表数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView toOrderPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("order-cart");
		try {
			/*String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET_NAME);
			User user = userService.queryUserByTicket(ticket);*/
			User user =  (User) request.getAttribute("user");
			mv.addObject("carts", cartService.getCartListByUserId(user.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

}
