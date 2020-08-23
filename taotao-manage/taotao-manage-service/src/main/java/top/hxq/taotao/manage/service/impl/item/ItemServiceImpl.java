package top.hxq.taotao.manage.service.impl.item;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import top.hxq.taotao.common.vo.DataGridResult;
import top.hxq.taotao.manage.mapper.item.ItemDescMapper;
import top.hxq.taotao.manage.mapper.item.ItemMapper;
import top.hxq.taotao.manage.pojo.item.Item;
import top.hxq.taotao.manage.pojo.item.ItemDesc;
import top.hxq.taotao.manage.service.impl.base.BaseServiceImpl;
import top.hxq.taotao.manage.service.item.ItemService;

/**
 * 商品业务实现类
 * @author wuzm
 * @date 2020年8月22日 上午11:14:28
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;

	
	/**
	 * 保存商品和描述信息
	 */
	@Override
	public Long saveItem(Item item, String desc) {
		//保存商品信息
		saveSelective(item);
		//保存商品描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		itemDescMapper.insertSelective(itemDesc);
		//返回商品id
		return item.getId();
	}

	
	/**
	 * 更新商品和描述信息
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	@Override
	public void updateItem(Item item, String desc) {
		//更新商品信息
		updateSelective(item);
		//保存商品描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}


	/**
	 * 根据商品名称分页模糊查询商品列表
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public DataGridResult getItemListByTitleInPage(String title, Integer page, Integer rows) throws Exception {
		PageHelper.startPage(page,rows);
		Example example = new Example(Item.class);
		if(StringUtils.isNotBlank(title)) {
			title = URLDecoder.decode(title,"utf-8");
			example.createCriteria().andLike("title", "%" + title + "%");
		}
		example.orderBy("updated").desc();
		List<Item> list = itemMapper.selectByExample(example);
		PageInfo<Item> pageInfo = new PageInfo<Item>(list);
		return new DataGridResult(pageInfo.getTotal(),pageInfo.getList());
	}

}
