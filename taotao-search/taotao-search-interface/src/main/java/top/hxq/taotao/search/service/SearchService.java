package top.hxq.taotao.search.service;

import java.util.List;

import top.hxq.taotao.common.vo.DataGridResult;
import top.hxq.taotao.search.vo.SolrItem;

public interface SearchService {

	/**
	 * 导入或更新数据到搜索系统
	 * @param solrItemList
	 * @throws Exception 
	 */
	void saveOrUpdateSolrItemList(List<SolrItem> solrItemList) throws Exception;

	/**
	 * 根据关键字分页搜索
	 * @param keywords
	 * @param page
	 * @param defaultRows
	 * @return
	 * @throws Exception 
	 */
	DataGridResult search(String keywords, Integer page, Integer defaultRows) throws Exception;

	/**
	 * 导入或更新数据到搜索系统
	 * @param solrItem
	 * @throws Exception 
	 */
	void saveOrUpdateSolrItem(SolrItem solrItem) throws Exception;

}
