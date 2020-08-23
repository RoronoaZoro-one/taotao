package top.hxq.taotao.manage.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/page")
public class PageController {
	
	/**
	 * 转发页面
	 * @param pageName
	 * @return
	 */
	@RequestMapping(value="/{pageName}",method=RequestMethod.GET)
	public String toPage(@PathVariable(value="pageName") String pageName) {
		return pageName;
	}

}
