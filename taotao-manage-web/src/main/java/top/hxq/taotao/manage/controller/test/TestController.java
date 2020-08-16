package top.hxq.taotao.manage.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.hxq.taotao.manage.service.test.TestService;

/**
 * 测试控制层类
 * @author wuzm
 * @date 2020年8月16日 上午11:36:31
 */
@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	/**
	 * 获得当前系统时间
	 * @return
	 */
	@RequestMapping("/date")
	public String queryCurrentDate() {
		return testService.queryCurrentDate();
	}

}
