package top.hxq.taotao.manage.controller.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import top.hxq.taotao.manage.pojo.item.ItemDesc;
import top.hxq.taotao.manage.service.item.ItemDescService;

/**
 * 商品描述控制层
 * 
 * @author wuzm
 * @date 2020年8月22日 上午11:14:04
 */
@Controller
@RequestMapping("/item/desc")
public class ItemDescController {

	@Autowired
	private ItemDescService itemDescService;

	/**
	 * 根据商品id获得商品描述信息
	 * @param itemId 商品id
	 * @return
	 */
	@RequestMapping(value="/{itemId}",method = RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = itemDescService.queryById(itemId);
			return ResponseEntity.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
