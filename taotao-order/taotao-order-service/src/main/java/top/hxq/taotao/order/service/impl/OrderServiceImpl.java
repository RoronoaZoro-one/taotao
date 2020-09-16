package top.hxq.taotao.order.service.impl;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.hxq.taotao.order.mapper.OrderMapper;
import top.hxq.taotao.order.pojo.Order;
import top.hxq.taotao.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Override
	public String saveOrder(Order order) {
		String orderId = order.getUserId() + "" + System.currentTimeMillis();
		order.setOrderId(orderId);
		order.setStatus(1);
		order.setCreateTime(new Date());
		order.setUpdateTime(order.getCreateTime());
		orderMapper.saveOrder(order);
		return orderId;
	}

	
	@Override
	public Order queryOrderByOrderId(String orderId) {
		return orderMapper.queryOrderByOrderId(orderId);
	}


	@Override
	public void autoCloseOrder() {
		orderMapper.autoCloseOrder(DateTime.now().minusDays(2).toDate());
	}
	
	
	
}
