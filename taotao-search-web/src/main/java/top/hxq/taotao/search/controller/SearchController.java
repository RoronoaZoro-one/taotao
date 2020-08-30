package top.hxq.taotao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import top.hxq.taotao.common.vo.DataGridResult;
import top.hxq.taotao.search.service.SearchService;

/**
 * 搜索系统控制层
 * 
 * @author wuzm
 * @date 2020年8月30日 上午10:49:02
 */
@RequestMapping("/search")
@Controller
public class SearchController {

	// 默认的页大小
	private static final Integer DEFAULT_ROWS = 20;

	@Autowired
	private SearchService searchService;

	/**
	 * 根据关键字分页搜索商品列表
	 * 
	 * @param keywords
	 * @param page
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(value = "q", required = false) String keywords,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		ModelAndView mv = new ModelAndView("search");
		try {
			if (StringUtils.isNotBlank(keywords)) {
				keywords = new String(keywords.getBytes("ISO-8859-1"), "UTF-8");
			}

			DataGridResult result = searchService.search(keywords, page, DEFAULT_ROWS);
			// 搜索关键字
			mv.addObject("query", keywords);
			// 商品列表
			mv.addObject("itemList", result.getRows());
			// 页码
			mv.addObject("page", page);
			// 总页数
			// (总数+页大小-1)/页大小
			long totalPages = (result.getTotal() + DEFAULT_ROWS - 1) / DEFAULT_ROWS;
			mv.addObject("totalPages", totalPages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

}
