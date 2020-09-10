package top.hxq.taotao.cart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import top.hxq.taotao.cart.pojo.Cart;
import top.hxq.taotao.cart.service.CartService;
import top.hxq.taotao.common.service.redis.RedisService;
import top.hxq.taotao.manage.pojo.item.Item;

@Service
public class CartServiceImpl implements CartService {

	private static final String REDIS_CART_KEY = "REDIS_CART_KEY";

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Autowired
	private RedisService redisService;

	@Override
	public void addCartByUserId(Item item, Integer num, Long userId) throws Exception {
		String key = REDIS_CART_KEY + userId;
		String field = item.getId().toString();
		// 获得redis中对应key的购物车字符串
		String cartJsonStr = redisService.hget(key, field);
		Cart cart = null;
		if (StringUtils.isNotBlank(cartJsonStr)) {
			// 如果redis已存在购物车数据，将购物车数量叠加
			cart = MAPPER.readValue(cartJsonStr, Cart.class);
			cart.setNum(cart.getNum() + num);
			cart.setUpdated(new Date());
		} else {
			// 如果不存在，创建购物车对象，存放到reids
			cart = new Cart();
			cart.setItemId(item.getId());
			if (item.getImages() != null) {
				cart.setItemImage(item.getImages()[0]);
			}
			cart.setItemPrice(item.getPrice());
			cart.setItemTitle(item.getTitle());
			cart.setNum(num);
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cart.setUserId(userId);
		}
		redisService.hset(key, field, MAPPER.writeValueAsString(cart));
	}

	@Override
	public List<Cart> getCartListByUserId(Long userId) throws Exception {
		List<String> cartListJsonStr = redisService.hvals(REDIS_CART_KEY + userId);
		List<Cart> cartList = new ArrayList<>();
		if (cartListJsonStr != null && cartListJsonStr.size() > 0) {
			for (String cartJsonStr : cartListJsonStr) {
				cartList.add(MAPPER.readValue(cartJsonStr, Cart.class));
			}
		}
		return cartList;
	}

	@Override
	public void updateCartItemNum(Long itemId, Integer num, Long userId) throws Exception {
		String key = REDIS_CART_KEY + userId;
		String field = itemId.toString();
		// 获得redis中对应key的购物车字符串
		String cartJsonStr = redisService.hget(key, field);
		if (StringUtils.isNotBlank(cartJsonStr)) {
			Cart cart = MAPPER.readValue(cartJsonStr, Cart.class);
			cart.setNum(num);
			cart.setUpdated(new Date());
			redisService.hset(key, field, MAPPER.writeValueAsString(cart));
		}
	}

	@Override
	public void deleteCartByItemIdAndUserId(Long itemId, Long userId) {
		String key = REDIS_CART_KEY + userId;
		String field = itemId.toString();
		redisService.hdel(key, field);
	}

}
