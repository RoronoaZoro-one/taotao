package top.hxq.taotao.manage.controller.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import top.hxq.taotao.manage.pojo.content.ContentCategory;
import top.hxq.taotao.manage.service.content.ContentCategoryService;

/**
 * 内容分类管理控制层
 * 
 * @author wuzm
 * @date 2020年8月23日 上午10:50:37
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	/**
	 * 根据父节点id获得子孙节点列表
	 * 
	 * @param parentId
	 *            父节点id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> queryContentCategoryListByParentId(
			@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		try {
			ContentCategory contentCategory = new ContentCategory();
			contentCategory.setParentId(parentId);
			List<ContentCategory> list = contentCategoryService.queryListByWhere(contentCategory);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 新增一个内容分类节点并更新父节点
	 * 
	 * @param contentCategory
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory) {
		try {
			ContentCategory category = contentCategoryService.saveContentCategory(contentCategory);
			return ResponseEntity.ok(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 重命名内容分类名称
	 * 
	 * @param contentCategory
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory) {
		try {
			contentCategoryService.updateSelective(contentCategory);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 删除内容类别节点及其子孙节点并更新父节点信息
	 * 
	 * @param contentCategory
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<Void> deleteContentCategory(ContentCategory contentCategory) {
		if(contentCategory.getParentId() != 0){//顶级节点不允许删除
			try {
				contentCategoryService.deleteContentCategory(contentCategory);
				return ResponseEntity.ok(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
