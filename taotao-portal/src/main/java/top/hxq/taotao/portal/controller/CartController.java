package top.hxq.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import top.hxq.taotao.cart.pojo.Cart;
import top.hxq.taotao.cart.service.CartService;
import top.hxq.taotao.manage.pojo.item.Item;
import top.hxq.taotao.manage.service.item.ItemService;
import top.hxq.taotao.portal.controller.user.UserController;
import top.hxq.taotao.portal.service.CookieCartService;
import top.hxq.taotao.portal.util.CookieUtils;
import top.hxq.taotao.sso.pojo.User;
import top.hxq.taotao.sso.service.UserService;

@RequestMapping("/cart")
@Controller
public class CartController {

	@Autowired
	private UserService userService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private CookieCartService cookieCartService;

	/**
	 * 加入购物车
	 * 
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{itemId}/{num}", method = RequestMethod.GET)
	public String addCart(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获得ticket，判断是否已经登录
			String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET_NAME);
			User user = userService.queryUserByTicket(ticket);
			if (user != null) {
				// 如果已经登录，将购物车数据存放到redis
				Item item = itemService.queryById(itemId);
				cartService.addCartByUserId(item, num, user.getId());
			} else {
				// 如果未登录或者登录已过期，将购物车数据存放到cookie
				cookieCartService.addCartByItemId(itemId,num,request,response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/cart/show.html";
	}

	/**
	 * 显示购物车列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showCart(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("cart");
		try {
			String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET_NAME);
			User user = userService.queryUserByTicket(ticket);
			if (user != null) {
				// 如果已经登录，从redis中获得购物车列表
				List<Cart> cartList = cartService.getCartListByUserId(user.getId());
				mv.addObject("cartList", cartList);
			} else {
				// 如果未登录或者登录已过期，从cookie中获得购物车列表
				List<Cart> cartList = cookieCartService.getCartList(request);
				mv.addObject("cartList", cartList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mv;
	}

	/**
	 * 修改购物车商品的数量
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update/num/{itemId}/{num}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateCartItemNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response){
		try {
			// 获得ticket，判断是否已经登录
			String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET_NAME);
			User user = userService.queryUserByTicket(ticket);
			if (user != null) {
				// 如果已经登录
				cartService.updateCartItemNum(itemId, num, user.getId());
			} else {
				// 如果未登录或者登录已过期
				cookieCartService.updateCartItemNum(itemId,num,request,response);
			}
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	
	/**
	 * 删除购物车中的商品
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delete/{itemId}", method = RequestMethod.GET)
	public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,
			HttpServletResponse response){
		try {
			// 获得ticket，判断是否已经登录
			String ticket = CookieUtils.getCookieValue(request, UserController.COOKIE_TICKET_NAME);
			User user = userService.queryUserByTicket(ticket);
			if (user != null) {
				// 如果已经登录
				cartService.deleteCartByItemIdAndUserId(itemId, user.getId());
			} else {
				// 如果未登录或者登录已过期
				cookieCartService.deleteCartByItemId(itemId,request,response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/cart/show.html";
	}
}
