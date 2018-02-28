package org.chronotics.talaria.taskhandler;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.chronotics.talaria.common.MessageQueueMap;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class HandlerMessageQueueToWebsocket extends Handler<SimpMessagingTemplate>{
	public static String queueMapKey = "queueMapKey";
	public static String targetTopic = "targetTopic";

	@Override
	public Object execute(SimpMessagingTemplate _template) throws Exception {
		String queueMapKey = (String)super.attributes.get(HandlerMessageQueueToWebsocket.queueMapKey);
		String targetTopic = (String)super.attributes.get(HandlerMessageQueueToWebsocket.targetTopic);
		
    	MessageQueueMap pool = MessageQueueMap.getInstance();
		ConcurrentLinkedQueue<String> msgqueue = 
	    		  (ConcurrentLinkedQueue<String>)pool.getMessageQueue(queueMapKey);
		if(msgqueue!=null && !msgqueue.isEmpty()) {
			while(!msgqueue.isEmpty()) {
				String msg = msgqueue.poll();
				_template.convertAndSend(targetTopic,msg);//"/topic/reply",msg);
			}
		}
		return null;
    }

}
