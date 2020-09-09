package top.hxq.taotao.portal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import top.hxq.taotao.cart.pojo.Cart;
import top.hxq.taotao.manage.pojo.item.Item;
import top.hxq.taotao.manage.service.item.ItemService;
import top.hxq.taotao.portal.util.CookieUtils;

@Service
public class CookieCartService {

	// 购物车数据在cookie中的名称
	private static final String COOKIE_CART_NAME = "TT_CART";

	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 购物车数据在cookie中的存活的最大时间
	private static final int COOKIE_CART_MAX_AGE = 60 * 60 * 24 * 7;

	@Autowired
	private ItemService itemService;

	/**
	 * 添加购物车数据到cookie
	 * 
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void addCartByItemId(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Cart> cartList = getCartList(request);
		boolean flag = false;
		if (cartList != null && cartList.size() > 0) {
			for (Cart cart : cartList) {
				if (itemId.toString().equals(cart.getItemId().toString())) {
					cart.setNum(cart.getNum() + num);
					cart.setUpdated(new Date());
					flag = true;
					break;
				}
			}
		}
		if (!flag) {
			if (cartList == null) {
				cartList = new ArrayList<>();
			}
			Cart cart = new Cart();
			Item item = itemService.queryById(itemId);
			cart.setItemId(item.getId());
			if (item.getImages() != null) {
				cart.setItemImage(item.getImages()[0]);
			}
			cart.setItemPrice(item.getPrice());
			cart.setItemTitle(item.getTitle());
			cart.setNum(num);
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartList.add(cart);
		}

		CookieUtils.setCookie(request, response, COOKIE_CART_NAME, MAPPER.writeValueAsString(cartList),
				COOKIE_CART_MAX_AGE, true);
	}

	/**
	 * 获得cookie中的购物车数据集合
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<Cart> getCartList(HttpServletRequest request) throws Exception {
		String cartListJsonStr = CookieUtils.getCookieValue(request, COOKIE_CART_NAME, true);
		if (StringUtils.isNotBlank(cartListJsonStr)) {
			return MAPPER.readValue(cartListJsonStr,
					MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
		}
		return null;
	}

	/**
	 * 修改cookie中的商品数量
	 * 
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void updateCartItemNum(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Cart> cartList = getCartList(request);
		if (cartList != null && cartList.size() > 0) {
			for (Cart cart : cartList) {
				if (itemId.toString().equals(cart.getItemId().toString())) {
					cart.setNum(num);
					break;
				}
			}
			CookieUtils.setCookie(request, response, COOKIE_CART_NAME, MAPPER.writeValueAsString(cartList),
					COOKIE_CART_MAX_AGE, true);
		}
	}

	/**
	 * 删除cookie中的购物车列表
	 * 
	 * @param itemId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteCartByItemId(Long itemId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Cart> cartList = getCartList(request);
		if (cartList != null && cartList.size() > 0) {
			Iterator<Cart> iterator = cartList.iterator();
			while (iterator.hasNext()) {
				Cart cart = iterator.next();
				if (itemId.toString().equals(cart.getItemId().toString())) {
					cartList.remove(cart);
					break;
				}
			}

		}
		if (cartList != null && cartList.size() > 0) {
			CookieUtils.setCookie(request, response, COOKIE_CART_NAME, MAPPER.writeValueAsString(cartList),
					COOKIE_CART_MAX_AGE, true);
		} else {
			CookieUtils.deleteCookie(request, response, COOKIE_CART_NAME);
		}
	}
}
