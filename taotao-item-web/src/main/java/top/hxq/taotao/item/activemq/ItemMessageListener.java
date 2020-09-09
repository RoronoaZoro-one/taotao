package top.hxq.taotao.item.activemq;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import top.hxq.taotao.manage.pojo.item.Item;
import top.hxq.taotao.manage.pojo.item.ItemDesc;
import top.hxq.taotao.manage.service.item.ItemDescService;
import top.hxq.taotao.manage.service.item.ItemService;

public class ItemMessageListener extends AbstractAdaptableMessageListener{
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService itemDescService;
	
	@Value("${HTML_SAVE_PATH}")
	private String HTML_SAVE_PATH;
	
	
	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		if(message instanceof MapMessage) {
			try {
				MapMessage mapMessage = (MapMessage)message;
				long itemId = mapMessage.getLong("itemId");
				String type = mapMessage.getString("type");
				if("delete".equals(type) || "instock".equals(type)) {
					File file = new File(HTML_SAVE_PATH + itemId + ".html");
					if(file.exists()) {
						file.delete();
					}
				}else {
					//获得freemarker配置对象
					Configuration configuration = freeMarkerConfigurer.getConfiguration();
					//获得模板对象
					Template template = configuration.getTemplate("item.ftl");
					//获得商品和商品详情数据
					Item item = itemService.queryById(itemId);
					ItemDesc itemDesc = itemDescService.queryById(itemId);
					Map<String,Object> dataModel = new HashMap<>();
					dataModel.put("item", item);
					dataModel.put("itemDesc", itemDesc);
					//输出
					FileWriter out = new FileWriter(HTML_SAVE_PATH + itemId + ".html");
					template.process(dataModel, out);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
