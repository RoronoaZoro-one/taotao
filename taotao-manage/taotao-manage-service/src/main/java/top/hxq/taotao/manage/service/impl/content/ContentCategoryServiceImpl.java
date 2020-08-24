package top.hxq.taotao.manage.service.impl.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.hxq.taotao.manage.mapper.content.ContentCategoryMapper;
import top.hxq.taotao.manage.pojo.content.ContentCategory;
import top.hxq.taotao.manage.service.content.ContentCategoryService;
import top.hxq.taotao.manage.service.impl.base.BaseServiceImpl;

/**
 * 商品描述业务实现类
 * 
 * @author wuzm
 * @date 2020年8月22日 下午9:25:30
 */
@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {

	@Autowired
	private ContentCategoryMapper contentCategoryMapper;

	/**
	 * 新增一个内容分类节点并更新父节点字段isParent修改为true
	 */
	@Override
	public ContentCategory saveContentCategory(ContentCategory contentCategory) {

		// 保存节点
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(100);
		saveSelective(contentCategory);
		// 更新该节点的父节点字段isParent修改为true
		ContentCategory parentCategory = new ContentCategory();
		parentCategory.setId(contentCategory.getParentId());
		parentCategory.setIsParent(true);
		updateSelective(parentCategory);

		return contentCategory;
	}

	/**
	 * 删除内容类别节点及其子孙节点并更新父节点信息
	 */
	@Override
	public void deleteContentCategory(ContentCategory contentCategory) {
		// 获得当前节点及其所有子孙节点
		List<Long> ids = new ArrayList<Long>();
		ids.add(contentCategory.getId());
		getCategoryIds(contentCategory.getId(), ids);
		// 删除节点及其子孙节点
		deleteByIds(ids.toArray(new Long[] {}));
		// 查询父节点是否还存在子节点，不存在则更新父节点
		ContentCategory category = new ContentCategory();
		category.setParentId(contentCategory.getParentId());
		long count = queryCountByWhere(category);
		if (count == 0) {
			ContentCategory parnetCategory = new ContentCategory();
			parnetCategory.setId(contentCategory.getParentId());
			parnetCategory.setIsParent(false);
			updateSelective(parnetCategory);
		}
	}

	/**
	 * 递归获取指定节点的所有子孙节点并添加到集合
	 * @param id
	 * @param ids
	 */
	private void getCategoryIds(Long id, List<Long> ids) {
		ContentCategory contentCategory = new ContentCategory();
		contentCategory.setParentId(id);
		List<ContentCategory> list = queryListByWhere(contentCategory);
		if (list != null && list.size() > 0) {
			for (ContentCategory cc : list) {
				ids.add(cc.getId());
				getCategoryIds(cc.getId(), ids);
			}
		}

	}

}
