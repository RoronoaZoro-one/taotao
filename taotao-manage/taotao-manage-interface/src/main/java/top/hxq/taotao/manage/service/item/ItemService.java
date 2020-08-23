package top.hxq.taotao.manage.service.item;

import top.hxq.taotao.common.vo.DataGridResult;
import top.hxq.taotao.manage.pojo.item.Item;
import top.hxq.taotao.manage.service.base.BaseService;


/**
 * 商品业务接口类
 * @author wuzm
 * @date 2020年8月22日 上午11:15:23
 */
public interface ItemService extends BaseService<Item> {

	
	/**
	 * 保存商品和描述信息
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	public Long saveItem(Item item, String desc);

	
	/**
	 * 更新商品和描述信息
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	public void updateItem(Item item, String desc);


	/**
	 * 根据商品名称分页模糊查询商品列表
	 * @param title
	 * @param page
	 * @param rows
	 * @return
	 */
	public DataGridResult getItemListByTitleInPage(String title, Integer page, Integer rows) throws Exception;

}
