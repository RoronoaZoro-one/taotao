package top.hxq.taotao.manage.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import top.hxq.taotao.manage.pojo.item.Item;
import top.hxq.taotao.manage.service.item.ItemService;

/**
 * 测试标准的restful风格接口
 * 
 * @author wuzm
 * @date 2020年8月24日 下午3:04:45
 */
@RequestMapping("/item/interface")
@RestController
public class ItemInterfaceController {

	@Autowired
	private ItemService itemService;

	/**
	 * 新增商品(post请求)
	 * 
	 * @param item
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item) {
		try {
			itemService.saveSelective(item);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 查询商品(get请求)
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<Item> queryItemById(@PathVariable Long itemId) {
		try {
			Item item = itemService.queryById(itemId);
			return ResponseEntity.ok(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 更新商品(put请求)
	 * 
	 * @param item
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item) {
		try {
			itemService.updateSelective(item);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	/**
	 * 删除商品(delete请求)
	 * 
	 * @param item
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteItem(@RequestParam(value="ids",required = false)Long[] ids) {
		try {
			if(null != ids && ids.length > 0) {
				itemService.deleteByIds(ids);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
