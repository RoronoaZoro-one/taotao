package top.hxq.taotao.manage.service.itemCat;

import java.util.List;
import java.util.Map;

import top.hxq.taotao.manage.pojo.ItemCat;

public interface ItemCatService {

	/**
	 * 分页查询商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<ItemCat> queryItemCatListByPage(Integer page, Integer rows);

}
