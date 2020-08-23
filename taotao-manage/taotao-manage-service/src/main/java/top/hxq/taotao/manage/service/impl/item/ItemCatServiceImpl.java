package top.hxq.taotao.manage.service.impl.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import top.hxq.taotao.manage.mapper.item.ItemCatMapper;
import top.hxq.taotao.manage.pojo.item.ItemCat;
import top.hxq.taotao.manage.service.impl.base.BaseServiceImpl;
import top.hxq.taotao.manage.service.item.ItemCatService;

/**
 * 商品类目业务实现类
 * @author wuzm
 * @date 2020年8月21日 下午5:41:52
 */
@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;

	/**
	 * 分页查询商品列表
	 */
	@Override
	public List<ItemCat> queryItemCatListByPage(Integer page, Integer rows) {
		PageHelper.startPage(page,rows);
		return itemCatMapper.selectAll();
	}

}
