package top.hxq.taotao.order.service;

import top.hxq.taotao.order.pojo.Order;

public interface OrderService {

	
	/**
	 * 保存订单信息到订单系统
	 * @param order 订单信息
	 * @return
	 */
	String saveOrder(Order order);

	/**
	 * 根据订单id查询订单信息
	 * @param orderId 订单id
	 * @return
	 */
	Order queryOrderByOrderId(String orderId);
	
	/**
	 * 定时关闭超过2天未支付的订单
	 */
	void autoCloseOrder();

}
