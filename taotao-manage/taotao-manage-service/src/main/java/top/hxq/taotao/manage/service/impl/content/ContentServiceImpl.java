package top.hxq.taotao.manage.service.impl.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import top.hxq.taotao.common.vo.DataGridResult;
import top.hxq.taotao.manage.mapper.content.ContentMapper;
import top.hxq.taotao.manage.pojo.content.Content;
import top.hxq.taotao.manage.service.content.ContentService;
import top.hxq.taotao.manage.service.impl.base.BaseServiceImpl;

/**
 * 商品描述业务实现类
 * 
 * @author wuzm
 * @date 2020年8月22日 下午9:25:30
 */
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

	
	@Value("${CONTENT_CATEGOY_BIG_AD_ID}")
	private Long CONTENT_CATEGOY_BIG_AD_ID;
	@Value("${PORTAL_BIG_AD_NUMBER}")
	private Integer PORTAL_BIG_AD_NUMBER;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private ContentMapper contentMapper;

	/**
	 * 根据分类id获得内容列表并分页
	 */
	@Override
	public DataGridResult queryContentListByCategoryId(Integer page, Integer rows, Long categoryId) {
		//启动分页查询
		PageHelper.startPage(page, rows);
		//高级查询
		Example example = new Example(Content.class);
		example.createCriteria().andEqualTo("categoryId", categoryId);
		//按更新时间排序
		example.orderBy("updated").desc();
		List<Content> list = contentMapper.selectByExample(example);
		//获得pageInfo信息
		PageInfo<Content> pageInfo = new PageInfo<Content>(list);
		return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}

	
	@Override
	public String getPortalbigAdData() throws Exception {
		//查询6条大广告分类下的最新的数据
		DataGridResult dataGridResult = queryContentListByCategoryId(1, PORTAL_BIG_AD_NUMBER, CONTENT_CATEGOY_BIG_AD_ID);
		//转换数据
		List<Content> list = (List<Content>) dataGridResult.getRows();
		if(list != null && list.size() > 0) {
			List<Map<String,Object>> resultList = new ArrayList<>();
			Map<String,Object> map = null;
			for (Content content : list) {
				map = new HashMap<>();
				map.put("alt", content.getTitle());
				map.put("height", 240);
				map.put("heightB", 240);
				map.put("width", 670);
				map.put("widthB", 550);
				map.put("src", content.getPic());
				map.put("srcB", content.getPic2());
				map.put("href", content.getUrl());
				resultList.add(map);
			}
			return MAPPER.writeValueAsString(resultList);
		}
		//返回json格式的数据
		return "";
	}

}
