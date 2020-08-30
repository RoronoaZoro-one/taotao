package top.hxq.taotao.search.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import top.hxq.taotao.manage.pojo.item.Item;
import top.hxq.taotao.manage.service.item.ItemService;
import top.hxq.taotao.search.service.SearchService;
import top.hxq.taotao.search.vo.SolrItem;

public class SaveItemTest {

	private ItemService itemService;

	private SearchService searchService;

	@Before
	public void setUp() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		itemService = context.getBean(ItemService.class);
		searchService = context.getBean(SearchService.class);
	}

	@Test
	public void test() {
		int page = 1;
		int rows = 500;
		List<Item> itemList = null;
		List<SolrItem> solrItemList = null;
		SolrItem solrItem = null;
		try {
			do {
				System.out.println("正在导入第" + page + "页...");
				itemList = itemService.queryListByPage(page, rows);
				solrItemList = new ArrayList<>();
				solrItem = new SolrItem();
				for (Item item : itemList) {
					solrItem.setId(item.getId());
					solrItem.setTitle(item.getTitle());
					solrItem.setImage(item.getImage());
					solrItem.setPrice(item.getPrice());
					solrItem.setSellPoint(item.getSellPoint());
					solrItem.setStatus(item.getStatus());
					solrItemList.add(solrItem);
				}
				searchService.saveOrUpdateSolrItemList(solrItemList);
				System.out.println("导入第" + page + "页完成。");
				page ++;
				rows = itemList.size();
			} while (rows == 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
