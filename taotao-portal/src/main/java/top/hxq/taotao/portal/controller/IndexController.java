package top.hxq.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import top.hxq.taotao.manage.service.content.ContentService;

/**
 * 首页控制层
 * @author wuzm
 * @date 2020年8月23日 上午10:01:22
 */
@RequestMapping("/index")
@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;
	
	
	/**
	 * 进入门户首页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView toIndexPage() {
		ModelAndView mv = new ModelAndView("index");
		try {
			//获取6条最新的大广告数据
			mv.addObject("bigAdData", contentService.getPortalbigAdData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

}
