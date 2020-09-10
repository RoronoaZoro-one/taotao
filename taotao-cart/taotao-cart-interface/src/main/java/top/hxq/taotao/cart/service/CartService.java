package top.hxq.taotao.cart.service;

import java.util.List;

import top.hxq.taotao.cart.pojo.Cart;
import top.hxq.taotao.manage.pojo.item.Item;

public interface CartService {

	/**
	 * 添加一个商品到购物车
	 * @param item
	 * @param num
	 * @param userId
	 * @throws Exception
	 */
	void addCartByUserId(Item item, Integer num, Long userId) throws Exception;

	
	/**
	 * 根据用户id获得购物车商品列表
	 * @param id
	 * @return
	 */
	List<Cart> getCartListByUserId(Long userId) throws Exception;


	/**
	 * 修改购物车商品的数量
	 * @param itemId
	 * @param num
	 * @param id
	 * @throws Exception 
	 */
	void updateCartItemNum(Long itemId, Integer num, Long userId) throws Exception;


	/**
	 * 删除购物车中的商品
	 * @param itemId
	 * @param id
	 */
	void deleteCartByItemIdAndUserId(Long itemId, Long userId);

}
