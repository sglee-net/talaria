package org.chronotics.talaria.websocket.spring.stomp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueuePool {
	private static class Holder {
		private static final MessageQueuePool theInstance=new MessageQueuePool();
	}
	
	public static MessageQueuePool getInstance() {
		return Holder.theInstance;
	}

	private MessageQueuePool() {
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
