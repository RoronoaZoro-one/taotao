package top.hxq.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/page")
@Controller
public class PageController {

	
	/**
	 * 转发到指定的页面
	 * @param pageName
	 * @return
	 */
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
	public String toPage(@PathVariable String pageName) {
		return pageName;
	}

}
