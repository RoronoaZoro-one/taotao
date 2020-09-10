package top.hxq.taotao.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.hxq.taotao.order.mapper.OrderMapper;
import top.hxq.taotao.order.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
}
