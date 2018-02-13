package temp;

import net.sglee.util.collection.GenericConcurrentHashMap;

public class MessageQueueRepository extends GenericConcurrentHashMap<String, MessageQueue> {
	private static class Holder {
		private static final MessageQueueRepository theInstance=new MessageQueueRepository();
	}
	
	public static MessageQueueRepository getInstance() {
		return Holder.theInstance;
	}
}
