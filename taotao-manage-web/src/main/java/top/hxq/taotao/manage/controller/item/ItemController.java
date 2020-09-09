package top.hxq.taotao.manage.controller.item;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import top.hxq.taotao.common.vo.DataGridResult;
import top.hxq.taotao.manage.pojo.item.Item;
import top.hxq.taotao.manage.service.item.ItemService;

/**
 * 商品控制层
 * 
 * @author wuzm
 * @date 2020年8月22日 上午11:14:04
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Destination topicDestination;

	/**
	 * 保存商品和描述信息
	 * 
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item, @RequestParam(value = "desc", required = false) String desc) {
		try {
			Long itemId = itemService.saveItem(item, desc);
			// 发布mq消息
			sendMqMsg(itemId,"insert");
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 更新商品和描述信息
	 * 
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateItem(Item item, @RequestParam(value = "desc", required = false) String desc) {
		try {
			itemService.updateItem(item, desc);
			// 发布mq消息
			sendMqMsg(item.getId(),"update");
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 根据商品名称分页模糊查询商品列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<DataGridResult> getItemListByTitleInPage(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "30") Integer rows,
			@RequestParam(value = "title", required = false) String title) {
		try {
			DataGridResult dataGridResult = itemService.getItemListByTitleInPage(title, page, rows);
			return ResponseEntity.ok(dataGridResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	
	/**
	 * 根据商品id删除商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<Void> deleteItem(@RequestParam(value = "ids", required = false) Long[] ids) {
		try {
			if(ids != null && ids.length > 0) {
				for (Long itemId : ids) {
					Item item = new Item();
					item.setId(itemId);
					item.setStatus(3);
					itemService.updateSelective(item);
					sendMqMsg(itemId,"delete");
				}
			}
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 根据商品id下架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/instock", method = RequestMethod.POST)
	public ResponseEntity<Void> instockItem(@RequestParam(value = "ids", required = false) Long[] ids) {
		try {
			if(ids != null && ids.length > 0) {
				for (Long itemId : ids) {
					Item item = new Item();
					item.setId(itemId);
					item.setStatus(2);
					itemService.updateSelective(item);
					sendMqMsg(itemId,"instock");
				}
			}
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	
	/**
	 * 根据商品id上架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/reshelf", method = RequestMethod.POST)
	public ResponseEntity<Void> reshelfItem(@RequestParam(value = "ids", required = false) Long[] ids) {
		try {
			if(ids != null && ids.length > 0) {
				for (Long itemId : ids) {
					Item item = new Item();
					item.setId(itemId);
					item.setStatus(1);
					itemService.updateSelective(item);
					sendMqMsg(itemId,"reshelf");
				}
			}
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	
	/**
	 * 发布消息
	 * 
	 * @param itemId
	 *            商品id
	 * @param type
	 *            update/insert/delete
	 */
	private void sendMqMsg(final Long itemId,final String type) {
		jmsTemplate.send(topicDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();
				mapMessage.setLong("itemId", itemId);
				mapMessage.setString("type", type);
				return mapMessage;
			}
		});
	}
}
