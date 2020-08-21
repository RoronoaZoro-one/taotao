package top.hxq.taotao.manage.service.impl.itemCat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import top.hxq.taotao.manage.mapper.itemCat.ItemCatMapper;
import top.hxq.taotao.manage.pojo.ItemCat;
import top.hxq.taotao.manage.service.itemCat.ItemCatService;

/**
 * 商品类目业务实现类
 * @author wuzm
 * @date 2020年8月21日 下午5:41:52
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
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
