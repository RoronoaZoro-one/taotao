package top.hxq.taotao.search.activemq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.adapter.AbstractAdaptableMessageListener;

import top.hxq.taotao.manage.pojo.item.Item;
import top.hxq.taotao.manage.service.item.ItemService;
import top.hxq.taotao.search.service.SearchService;
import top.hxq.taotao.search.vo.SolrItem;

/**
 * 商品数据变更MQ监听器
 * @author wuzm
 * @date 2020年8月30日 下午4:11:59
 */
public class ItemMessageListener extends AbstractAdaptableMessageListener{
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SearchService searchService;

	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		try {
			if(message instanceof MapMessage) {
				MapMessage mapMessage = (MapMessage)message;
				long itemId = mapMessage.getLong("itemId");
				Item item = itemService.queryById(itemId);
				SolrItem solrItem = new SolrItem();
				solrItem.setId(item.getId());
				solrItem.setTitle(item.getTitle());
				solrItem.setImage(item.getImage());
				solrItem.setPrice(item.getPrice());
				solrItem.setSellPoint(item.getSellPoint());
				solrItem.setStatus(item.getStatus());
				searchService.saveOrUpdateSolrItem(solrItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
