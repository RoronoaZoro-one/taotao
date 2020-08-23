package top.hxq.taotao.manage.service.item;

import java.util.List;

import top.hxq.taotao.manage.pojo.item.ItemCat;
import top.hxq.taotao.manage.service.base.BaseService;

public interface ItemCatService extends BaseService<ItemCat>{

	/**
	 * 分页查询商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<ItemCat> queryItemCatListByPage(Integer page, Integer rows);

}
