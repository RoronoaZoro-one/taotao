package top.hxq.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import top.hxq.taotao.sso.service.UserService;

/**
 * 用户控制层
 * 
 * @author wuzm
 * @date 2020年8月26日 下午2:58:00
 */
@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 检查数据是否可用，支持jsonp
	 * 
	 * @param param
	 *            要检查的数据
	 * @param type
	 *            数据类型：1、2、3分别代表username、phone、email
	 * @return
	 */
	@RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET)
	public ResponseEntity<String> check(@PathVariable String param, @PathVariable Integer type,
			@RequestParam(value = "callback", required = false) String callbak) {
		try {
			if (1 <= type && type <= 3) {
				Boolean flag = userService.check(param, type);
				String result = flag.toString();
				if(StringUtils.isNotBlank(callbak)) {
					result = callbak + "(" + result + ");";
				}
				return ResponseEntity.ok(result);
			} else {
				// 参数不合法
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 根据ticket查询用户，支持jsonp
	 * 
	 * @param ticket 用户登录凭证
	 * @return
	 */
	@RequestMapping(value = "/{ticket}", method = RequestMethod.GET)
	public ResponseEntity<String> queryUserStrByTicket(@PathVariable String ticket,
			@RequestParam(value = "callback", required = false) String callbak) {
		try {
			if (StringUtils.isNotBlank(ticket)) {
				String userJsonStr = userService.queryUserStrByTicket(ticket);
				if(StringUtils.isNotBlank(callbak)) {
					userJsonStr = callbak + "(" + userJsonStr + ");";
				}
				return ResponseEntity.ok(userJsonStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
