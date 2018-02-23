package org.chronotics.talaria.websocket.spring.stomp;

import java.util.concurrent.ConcurrentLinkedQueue;

import net.sglee.util.collection.GenericConcurrentLinkedQueue;

public class DummyMessageGenerator implements Runnable {
	int count = 20;
	@Override
	public void run() {
		MessageQueuePool pool = MessageQueuePool.getInstance();
		ConcurrentLinkedQueue<String> msgqueue = 
				(ConcurrentLinkedQueue<String>) pool.getMessageQueue("msgqueue");
						//new MessageQueueKey("msgqueue",String.class));
		System.out.println(msgqueue);
		if(msgqueue != null) {
			for(int i=0; i < count; i++) {
				msgqueue.add(Integer.toString(i));
				//System.out.println(i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
