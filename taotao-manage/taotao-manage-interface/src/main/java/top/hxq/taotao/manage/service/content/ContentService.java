package top.hxq.taotao.manage.service.content;

import top.hxq.taotao.common.vo.DataGridResult;
import top.hxq.taotao.manage.pojo.content.Content;
import top.hxq.taotao.manage.service.base.BaseService;

/**
 * 内容管理业务接口
 * @author wuzm
 * @date 2020年8月23日 上午10:56:17
 */
public interface ContentService extends BaseService<Content> {

	/**
	 * 根据分类id获得内容列表并分页
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	DataGridResult queryContentListByCategoryId(Integer page, Integer rows, Long categoryId);

	/**
	 * 查询最新的大广告分类下的6条数据
	 * @return
	 * @throws Exception 
	 */
	String  getPortalbigAdData() throws Exception;

}
