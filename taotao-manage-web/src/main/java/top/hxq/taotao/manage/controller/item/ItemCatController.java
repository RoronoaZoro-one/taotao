package top.hxq.taotao.manage.controller.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import top.hxq.taotao.manage.pojo.item.ItemCat;
import top.hxq.taotao.manage.service.item.ItemCatService;


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
	 * 根据商品类目id获得商品类目信息
	 * @param categoryId 商品类目节点id
	 * @return
	 */
	@RequestMapping(value="/{categoryId}",method=RequestMethod.GET)
	public ResponseEntity<ItemCat> getItemCatByCategoryId(@PathVariable Long categoryId){
		try {
			ItemCat itemCat = itemCatService.queryById(categoryId);
			return ResponseEntity.ok(itemCat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	/**
	 * 根据父节点id获得子类节点
	 * @param parentId 父节点id
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> getItemCatByParentId(@RequestParam(value="id",defaultValue="0") Long parentId){
		try {
			ItemCat itemCat = new ItemCat();
			itemCat.setParentId(parentId);
			List<ItemCat> itemCatList = itemCatService.queryListByWhere(itemCat);
			return ResponseEntity.ok(itemCatList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	
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
			//List<ItemCat> itemCatList = itemCatService.queryItemCatListByPage(page,rows);
			List<ItemCat> itemCatList = itemCatService.queryListByPage(page, rows);
			return ResponseEntity.ok(itemCatList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
