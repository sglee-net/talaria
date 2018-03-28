package org.chronotics.talaria.websocket.springstompserver;

import java.util.HashMap;
import java.util.Map;

import org.chronotics.talaria.common.Handler;
import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.impl.HandlerMessageQueueToWebsocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledUpdates<T> {
	private static final Logger logger = 
			LoggerFactory.getLogger(ScheduledUpdates.class);
	
	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

	public String queueMapKey = null;

	private Handler<T> handler = null;
	public void setAttribute(
			String _queueMapKey, 
			Handler<T> _handler) {
		queueMapKey = _queueMapKey;
		handler = _handler;
		handler.putAttribute(
				HandlerMessageQueueToWebsocket.simpMessagingTemplate, 
				simpMessagingTemplate);
	}
	
    @Scheduled(fixedDelay=100)
    public void update(){
    	if(handler == null) {
    		logger.info("handler is null");
    		return;
    	}
	
    	assert(queueMapKey != null);
    	if(queueMapKey == null) {
    		throw new NullPointerException("queueMapKey is null");
    	}
    	
		MessageQueue<T> msgqueue = (MessageQueue<T>)
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		if(msgqueue == null) {
			throw new NullPointerException();
		}
		
		if(msgqueue.isEmpty()) {
			return;
		}  	
		int count = msgqueue.size();
		for (int i = 0; i < count; i++) {	
			try {
				handler.execute(msgqueue.poll());//(SimpMessagingTemplate)template);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
