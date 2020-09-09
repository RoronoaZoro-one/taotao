package top.hxq.taotao.common.service.redis.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisCluster;
import top.hxq.taotao.common.service.redis.RedisService;

public class RedisClusterServiceImpl implements RedisService {

	@Autowired(required = false)
	private JedisCluster jedisCluster;

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return jedisCluster.setex(key, seconds, value);
	}

	@Override
	public long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Long del(String key) {
		return jedisCluster.del(key);
	}

	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return jedisCluster.hset(key,field,value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCluster.hget(key,field);
	}

	@Override
	public List<String> hvals(String key) {
		return jedisCluster.hvals(key);
	}

	@Override
	public Long hdel(String key, String field) {
		return jedisCluster.hdel(key,field);
	}

}
