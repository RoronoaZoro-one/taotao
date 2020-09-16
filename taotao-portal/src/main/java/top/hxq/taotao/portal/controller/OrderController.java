package top.hxq.taotao.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import top.hxq.taotao.cart.service.CartService;
import top.hxq.taotao.order.pojo.Order;
import top.hxq.taotao.order.service.OrderService;
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
	
	@Autowired
	private OrderService orderService;

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
	
	/**
	 * 保存订单信息到订单系统
	 * @param request
	 * @param order 订单信息
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> saveOrder(HttpServletRequest request,Order order) {
		Map<String,Object> result = new HashMap<>();
		result.put("status", 500);
		try {
			User user = (User)request.getAttribute("user");
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			String orderId = orderService.saveOrder(order);
			if(StringUtils.isNotBlank(orderId)) {
				result.put("data", orderId);
				result.put("status", 200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(result);
	}

	
	/**
	 * 订单生成成功页面
	 * @param orderId 订单id
	 * @return
	 */
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public ModelAndView success(@RequestParam(value="id") String orderId) {
		ModelAndView mv = new ModelAndView("success");
		Order order = orderService.queryOrderByOrderId(orderId);
		mv.addObject("order", order);
		mv.addObject("date", DateTime.now().plusDays(2).toString("MM月dd日"));
		return mv;
	}
}
