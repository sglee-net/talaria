package org.chronotics.talaria.impl;

import org.chronotics.talaria.common.Handler;
import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.common.MessageQueueMap;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class HandlerMessageQueueToWebsocket extends Handler<SimpMessagingTemplate>{
	public static String queueMapKey = "queueMapKey";
	public static String targetDestination = "targetDestination";

	@Override
	public Object execute(SimpMessagingTemplate _template) throws Exception {
		String queueMapKey = 
				(String)super.attributes.get(HandlerMessageQueueToWebsocket.queueMapKey);
		String targetDestination = 
				(String)super.attributes.get(HandlerMessageQueueToWebsocket.targetDestination);
		
		MessageQueue<? extends Object> msgqueue = (MessageQueue<? extends Object>)
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		if(msgqueue == null) {
			throw new NullPointerException();
		}
		
		if(msgqueue.isEmpty()) {
			return null;
		}
	
//		Object object = msgqueue.peek();
//		String dataType = object.getClass().getName();
		String dataType = msgqueue.getElementClass().getName();
		if(dataType == Boolean.class.getName()) {
			int count = msgqueue.size();
			for (int i = 0; i < count; i++) {
				Boolean msg = (Boolean)msgqueue.poll();
				_template.convertAndSend(targetDestination,msg);
			}
		} else if (dataType == Integer.class.getName()) {
			int count = msgqueue.size();
			for (int i = 0; i < count; i++) {
				Integer msg = (Integer)msgqueue.poll();
				_template.convertAndSend(targetDestination,msg);
			}
		} else if (dataType == Short.class.getName()) {
			int count = msgqueue.size();
			for (int i = 0; i < count; i++) {
				Short msg = (Short)msgqueue.poll();
				_template.convertAndSend(targetDestination,msg);
			}
		} else if (dataType == Long.class.getName()) {
			int count = msgqueue.size();
			for (int i = 0; i < count; i++) {
				Long msg = (Long)msgqueue.poll();
				_template.convertAndSend(targetDestination,msg);
			}
		} else if (dataType == Double.class.getName()) {
			int count = msgqueue.size();
			for (int i = 0; i < count; i++) {
				Double msg = (Double)msgqueue.poll();
				_template.convertAndSend(targetDestination,msg);
			}
		} else if (dataType == String.class.getName()) {
			int count = msgqueue.size();
			for (int i = 0; i < count; i++) {
				String msg = (String)msgqueue.poll();
				_template.convertAndSend(targetDestination,msg);
			}
		} else {
			throw new ClassCastException("undefine type");
		}
		
		return null;
    }

}
