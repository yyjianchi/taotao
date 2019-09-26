package com.taotao.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.service.SearchItemService;

public class ItemChangeMessageListener implements MessageListener {
	@Autowired
	private SearchItemService searchItemService;
	
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage=null;
		Long itemId=null;
		
		if (message instanceof TextMessage) {
			textMessage=(TextMessage) message;
			try {
				itemId=Long.parseLong(textMessage.getText());
				searchItemService.updateItemById(itemId);
				}
			
			catch (Exception e) {
				e.printStackTrace();
				}
	

			}
		}
	}