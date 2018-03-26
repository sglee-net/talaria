package org.chronotics.talaria.common;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.thrift.ThriftHandler;

public class HandlerThriftToMessageQueue<T> extends Handler<Map<String,T>> {
	public static String queueMapKey = "queueMapKey";
	
	@Override
	public Object execute(Map<String,T> _obj) throws Exception {
		String queueMapKey = (String)super.attributes.get(HandlerThriftToMessageQueue.queueMapKey);
		if(queueMapKey == null) {
			return null;
		}
		
		Map<String,T> map = (Map<String,T>)_obj;
//		if(map.get(ThriftHandler.function) != ThriftHandler.writeString) {
//			return null;
//		}
		T object = map.get(ThriftHandler.arg1);
		
		MessageQueueMap pool = MessageQueueMap.getInstance();
		ConcurrentLinkedQueue<String> msgqueue = 
				(ConcurrentLinkedQueue<String>) pool.getMessageQueue(queueMapKey);

		return msgqueue.add(object.toString());
	}

}
