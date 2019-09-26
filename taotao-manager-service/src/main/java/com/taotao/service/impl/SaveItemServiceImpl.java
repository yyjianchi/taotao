package com.taotao.service.impl;

import java.util.Date;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.taotao.idutils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.SaveItemService;
import com.taotao.taotaoresult.TaotaoResult;
@Service
public class SaveItemServiceImpl implements SaveItemService {
	@Autowired
	TbItemMapper itemMapper;
	@Autowired
	TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplata;
	@Autowired
	private Destination topicDestination;
	
	@Override
	public TaotaoResult saveItem(TbItem item, String desc) {
		
		// 1、生成商品id
		final long itemId = IDUtils.genItemId();
		// 2、补全TbItem对象的属性
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 3、向商品表插入数据
		itemMapper.insert(item);
		// 4、创建一个TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 5、补全TbItemDesc的属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 6、向商品描述表插入数据
		itemDescMapper.insert(itemDesc);
		//发送一个商品添加信息
		jmsTemplata.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage=session.createTextMessage(itemId+"");
				return textMessage;
				
			}
		});
		// 7、TaotaoResult.ok()
		return TaotaoResult.ok();
	}

}
