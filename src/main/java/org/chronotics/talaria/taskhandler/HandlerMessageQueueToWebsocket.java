package org.chronotics.talaria.taskhandler;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.chronotics.talaria.common.MessageQueueMap;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class HandlerMessageQueueToWebsocket extends Handler<SimpMessagingTemplate>{
	public static String queueMapKey = "queueMapKey";
	public static String targetDestination = "targetDestination";

	@Override
	public Object execute(SimpMessagingTemplate _template) throws Exception {
		String queueMapKey = (String)super.attributes.get(HandlerMessageQueueToWebsocket.queueMapKey);
		String targetDestination = (String)super.attributes.get(HandlerMessageQueueToWebsocket.targetDestination);
		
    	MessageQueueMap pool = MessageQueueMap.getInstance();
		ConcurrentLinkedQueue<String> msgqueue = 
	    		  (ConcurrentLinkedQueue<String>)pool.getMessageQueue(queueMapKey);
		if(msgqueue!=null && !msgqueue.isEmpty()) {
			while(!msgqueue.isEmpty()) {
				String msg = msgqueue.poll();
				_template.convertAndSend(targetDestination,msg);//"/topic/reply",msg);
			}
		}
		return null;
    }

}
