package top.hxq.taotao.manage.controller.content;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import top.hxq.taotao.common.vo.DataGridResult;
import top.hxq.taotao.manage.pojo.content.Content;
import top.hxq.taotao.manage.service.content.ContentService;

/**
 * 内容管理控制层
 * 
 * @author wuzm
 * @date 2020年8月23日 下午1:42:37
 */
@RequestMapping("/content")
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;

	/**
	 * 根据分类id获得内容列表并分页
	 * 
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<DataGridResult> queryContentListByCategoryId(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "20") Integer rows,
			@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId) {
		try {
			DataGridResult dataGridResult = contentService.queryContentListByCategoryId(page, rows, categoryId);
			return ResponseEntity.ok(dataGridResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 新增内容信息
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveContent(Content content) {
		try {
			contentService.saveSelective(content);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 更新内容信息
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResponseEntity<Void> updateContent(Content content) {
		try {
			contentService.updateSelective(content);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 批量删除内容内容
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> deleteContent(
			@RequestParam(value = "ids", required = false) Long[] ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 500);
		try {
			if (ids != null && ids.length > 0) {
				contentService.deleteByIds(ids);
			}
			result.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}
}
