package top.hxq.taotao.manage.service.content;

import top.hxq.taotao.manage.pojo.content.ContentCategory;
import top.hxq.taotao.manage.service.base.BaseService;

/**
 * 内容分类管理业务接口
 * 
 * @author wuzm
 * @date 2020年8月23日 上午10:55:59
 */
public interface ContentCategoryService extends BaseService<ContentCategory> {

	/**
	 * 新增一个内容分类节点并更新父节点
	 * 
	 * @param contentCategory
	 * @return
	 */
	ContentCategory saveContentCategory(ContentCategory contentCategory);

	/**
	 * 删除内容类别节点及其子孙节点并更新父节点信息
	 * 
	 * @param contentCategory
	 */
	void deleteContentCategory(ContentCategory contentCategory);

}
