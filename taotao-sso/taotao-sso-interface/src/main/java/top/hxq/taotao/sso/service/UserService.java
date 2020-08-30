package top.hxq.taotao.sso.service;

import top.hxq.taotao.sso.pojo.User;

public interface UserService {

	
	/**
	 * 检查数据是否可用
	 * @param param 要检查的数据
	 * @param type 数据类型：1、2、3分别代表username、phone、email
	 * @return
	 */
	Boolean check(String param, Integer type);

	
	/**
	 * 根据ticket查询用户
	 * @param ticket 用户登录凭证
	 * @return
	 */
	String queryUserStrByTicket(String ticket);


	/**
	 * 注册
	 * @param user
	 */
	void register(User user);


	/**
	 * 登录
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	String login(User user) throws Exception;


	/**
	 * 退出登录，删除redis中的ticket
	 * @param ticket
	 */
	void logout(String ticket);

}
