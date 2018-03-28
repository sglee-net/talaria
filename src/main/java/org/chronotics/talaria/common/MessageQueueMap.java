package org.chronotics.talaria.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
