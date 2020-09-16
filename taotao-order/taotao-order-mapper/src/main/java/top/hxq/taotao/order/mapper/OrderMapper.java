package top.hxq.taotao.order.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import top.hxq.taotao.order.pojo.Order;

public interface OrderMapper {

	/**
	 * 保存商品信息到数据库
	 * 
	 * @param order
	 */
	void saveOrder(Order order);

	/**
	 * 根据订单id查询订单信息
	 * 
	 * @param orderId
	 *            订单id
	 * @return
	 */
	Order queryOrderByOrderId(@Param("orderId") String orderId);

	void autoCloseOrder(@Param("date")Date date);

}
