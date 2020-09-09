package top.hxq.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/page")
@Controller
public class PageController {

	/**
	 * 转发到指定的页面
	 * 
	 * @param pageName
	 * @return
	 */
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
	public ModelAndView toPage(@PathVariable String pageName,
			@RequestParam(value = "redirectUrl", required = false) String redirectUrl) {
		ModelAndView mv = new ModelAndView(pageName);
		mv.addObject("redirectUrl", redirectUrl);
		return mv;
	}

}
