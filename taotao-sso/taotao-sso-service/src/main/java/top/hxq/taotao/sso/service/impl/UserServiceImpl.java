package top.hxq.taotao.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.hxq.taotao.common.service.redis.RedisService;
import top.hxq.taotao.sso.mapper.UserMapper;
import top.hxq.taotao.sso.pojo.User;
import top.hxq.taotao.sso.service.UserService;

/**
 * 用户接口业务实现类
 * 
 * @author wuzm
 * @date 2020年8月26日 下午2:58:11
 */
@Service
public class UserServiceImpl implements UserService {
	
	//存放在redis中ticket的前缀
	private static final String REDIS_TICKET_PREFIX = "top-taotao-sso-ticket";
	
	//ticket在redis中的存活时间
	private static final int REDIS_TICKET_EXPIRE_TIME = 60*60;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RedisService redisService;

	
	@Override
	public Boolean check(String param, Integer type) {
		//如果返回false则表示数据已经存在，不允许注册使用
		User user = new User();
		switch (type) {
		case 1:
			user.setUsername(param);
			break;
		case 2:
			user.setPhone(param);
			break;
		default:
			user.setEmail(param);
			break;
		}
		int count = userMapper.selectCount(user);
		return count == 0;
	}


	@Override
	public String queryUserStrByTicket(String ticket) {
		String key = REDIS_TICKET_PREFIX + ticket;
		String userJsonStr = redisService.get(key);
		if(StringUtils.isNotBlank(userJsonStr)) {
			//重置ticket在redis中的存活时间
			redisService.expire(key, REDIS_TICKET_EXPIRE_TIME);
			return userJsonStr;
		}
		return "";
	}
	
	
	
}