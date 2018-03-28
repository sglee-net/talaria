package org.chronotics.talaria.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SG Lee
 * @since 3/20/2015
 * @description
 * This Class is singleton class to manage multiple MessageQueues
 * Each MessageQue can be assigned to each sender, which means the key of 
 * MessageQueueMap could be a sort of ID that can distinguish senders.
 */

public class MessageQueueMap {
	private static class Holder {
		private static final MessageQueueMap theInstance=new MessageQueueMap();
	}
	
	public static MessageQueueMap getInstance() {
		return Holder.theInstance;
	}

	private MessageQueueMap() {
	}
	
	private Map<String,MessageQueue<?>> map = 
			new ConcurrentHashMap<String,MessageQueue<?>>();
	
	public MessageQueue<?> getMessageQueue(String _key) {
		return map.get(_key);
	}
	
	public void put(String _key,MessageQueue<?> _value) {
		map.put(_key, _value);
	}
}
