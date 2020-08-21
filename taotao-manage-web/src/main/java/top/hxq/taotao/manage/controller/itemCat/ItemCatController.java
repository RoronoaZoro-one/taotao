package top.hxq.taotao.manage.controller.itemCat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import top.hxq.taotao.manage.pojo.ItemCat;
import top.hxq.taotao.manage.service.itemCat.ItemCatService;


/**
 * 商品类目控制层
 * @author wuzm
 * @date 2020年8月21日 下午5:13:16
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;

	
	/**
	 * 分页查询商品列表
	 * @param page 页码
	 * @param rows 页大小
	 * @return
	 */
	@RequestMapping(value = "/query/{page}", method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByPage(@PathVariable Integer page,
			@RequestParam(value = "rows", defaultValue = "20") Integer rows) {
		try {
			List<ItemCat> itemCatList = itemCatService.queryItemCatListByPage(page,rows);
			return ResponseEntity.ok(itemCatList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
