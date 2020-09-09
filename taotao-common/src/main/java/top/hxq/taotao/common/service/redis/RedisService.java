package top.hxq.taotao.common.service.redis;

import java.util.List;

public interface RedisService {

	/**
	 * 设置字符串类型的key及其对应的值
	 * 
	 * @param key
	 *            键名
	 * @param value
	 *            键值
	 * @return
	 */
	public String set(String key, String value);

	/**
	 * 设置字符串类型的key及其对应的值,并设置过期时间
	 * 
	 * @param key
	 *            键名
	 * @param seconds
	 *            过期的时间（秒）
	 * @param value
	 *            键值
	 * @return
	 */
	public String setex(String key, int seconds, String value);

	/**
	 * 对指定的key设置生存时间
	 * 
	 * @param key
	 *            键名
	 * @param seconds
	 *            过期的时间（秒）
	 * @return
	 */
	public long expire(String key, int seconds);

	/**
	 * 获取指定key的值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);

	/**
	 * 删除指定的key
	 * 
	 * @param key
	 * @return
	 */
	public Long del(String key);

	/**
	 * 对指定的key的值递增，默认步长为1
	 * 
	 * @param key
	 * @return
	 */
	public Long incr(String key);
	
	
	/**
	 * 插入一条散列数据
	 * @param key	键名
	 * @param field	域名
	 * @param value	值
	 * @return
	 */
	public Long hset(String key,String field,String value);
	
	/**
	 * 获得指定key的域中的值
	 * @param key	键名
	 * @param field	域名
	 * @return
	 */
	public String hget(String key,String field);
	
	/**
	 * 获得指定key的值的集合
	 * @param key	键名
	 * @return
	 */
	public List<String> hvals(String key);
	
	
	/**
	 * 删除指定key的域中的值
	 * @param key	键名
	 * @param field	域名
	 * @return
	 */
	public Long hdel(String key,String field);

}
