package top.hxq.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import top.hxq.taotao.manage.service.item.ItemDescService;
import top.hxq.taotao.manage.service.item.ItemService;

@RequestMapping("/item")
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemDescService itemDescService;

	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public ModelAndView toItemPage(@PathVariable Long itemId) {
		ModelAndView mv = new ModelAndView("item");
		mv.addObject("item", itemService.queryById(itemId));
		mv.addObject("itemDesc", itemDescService.queryById(itemId));
		return mv;
	}

}
