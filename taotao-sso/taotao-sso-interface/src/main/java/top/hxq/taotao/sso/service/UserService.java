package top.hxq.taotao.sso.service;

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

}
