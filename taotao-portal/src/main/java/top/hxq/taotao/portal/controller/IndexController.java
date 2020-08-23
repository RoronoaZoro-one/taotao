package top.hxq.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页控制层
 * @author wuzm
 * @date 2020年8月23日 上午10:01:22
 */
@RequestMapping("/index")
@Controller
public class IndexController {
	
	
	/**
	 * 进入门户首页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView toIndexPage() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}

}
