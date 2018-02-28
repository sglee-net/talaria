package org.chronotics.talaria.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueueMap {
	private static class Holder {
		private static final MessageQueueMap theInstance=new MessageQueueMap();
	}
	
	public static MessageQueueMap getInstance() {
		return Holder.theInstance;
	}

	private MessageQueueMap() {
	}
	
	private Map<String,ConcurrentLinkedQueue<?>> map = 
			new ConcurrentHashMap<String,ConcurrentLinkedQueue<?>>();
	
	public ConcurrentLinkedQueue<?> getMessageQueue(String _key) {
		return map.get(_key);
	}
	
	public void put(String _key,ConcurrentLinkedQueue<?> _value) {
		map.put(_key, _value);
	}
}
