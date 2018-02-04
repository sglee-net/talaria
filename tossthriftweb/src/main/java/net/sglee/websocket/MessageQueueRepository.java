package net.sglee.websocket;

import net.sglee.util.collection.GenericConcurrentHashMap;
import net.sglee.websocket.common.MessageQueue;

public class MessageQueueRepository extends GenericConcurrentHashMap<String, MessageQueue> {
	private static class Holder {
		private static final MessageQueueRepository theInstance=new MessageQueueRepository();
	}
	
	public static MessageQueueRepository getInstance() {
		return Holder.theInstance;
	}
}
