package top.hxq.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

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


	@Override
	public void register(User user) {
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insertSelective(user);
	}


	
	@Override
	public String login(User user) throws Exception {
		//1.根据用户账号和密码查询用户
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		List<User> userList = userMapper.select(user);
		String ticket = "";
		//2.将用户转换成json字符串存放到redis,并返回ticket
		if(userList != null && userList.size() > 0) {
			ticket = DigestUtils.md5Hex(UUID.randomUUID().toString() + System.currentTimeMillis());
			User tmp = userList.get(0);
			redisService.setex(REDIS_TICKET_PREFIX + ticket, REDIS_TICKET_EXPIRE_TIME, MAPPER.writeValueAsString(tmp));
		}
		return ticket;
	}


	@Override
	public void logout(String ticket) {
		//删除redis中对应的用户信息
		redisService.del(REDIS_TICKET_PREFIX + ticket);
	}
	
	
	
}