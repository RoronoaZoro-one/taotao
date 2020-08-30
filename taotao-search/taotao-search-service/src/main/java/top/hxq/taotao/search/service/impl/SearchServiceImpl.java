package top.hxq.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.hxq.taotao.common.vo.DataGridResult;
import top.hxq.taotao.search.service.SearchService;
import top.hxq.taotao.search.vo.SolrItem;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private CloudSolrServer cloudSolrServer;

	@Override
	public void saveOrUpdateSolrItemList(List<SolrItem> solrItemList) throws Exception {
		cloudSolrServer.addBeans(solrItemList);
		cloudSolrServer.commit();
	}

	@Override
	public DataGridResult search(String keywords, Integer page, Integer rows) throws Exception {
		// 是否高亮
		boolean isHighlight = true;
		// 如果不存在，搜索所有
		if (StringUtils.isBlank(keywords)) {
			keywords = "*";
			isHighlight = false;
		}
		// 查询标题
		SolrQuery solrQuery = new SolrQuery("title:" + keywords + " AND status:1");
		if (isHighlight) {
			// 高亮的属性
			solrQuery.addHighlightField("title");
			solrQuery.setHighlight(isHighlight);
			// 高亮的起始标签
			solrQuery.setHighlightSimplePre("<em>");
			// 高亮的结束标签
			solrQuery.setHighlightSimplePost("</em>");
		}
		// 查询的起始索引
		solrQuery.setStart((page - 1) * rows);
		// 页大小
		solrQuery.setRows(rows);
		QueryResponse response = cloudSolrServer.query(solrQuery);
		List<SolrItem> list = response.getBeans(SolrItem.class);
		// 如果高亮，将高亮的属性设置到集合
		if (isHighlight && list != null && list.size() > 0) {
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			for (SolrItem solrItem : list) {
				solrItem.setTitle(highlighting.get(solrItem.getId().toString()).get("title").get(0));
			}
		}
		// 获得总数
		long total = response.getResults().getNumFound();
		return new DataGridResult(total, list);
	}

	@Override
	public void saveOrUpdateSolrItem(SolrItem solrItem) throws Exception {
		cloudSolrServer.addBean(solrItem);
		cloudSolrServer.commit();
	}

}
