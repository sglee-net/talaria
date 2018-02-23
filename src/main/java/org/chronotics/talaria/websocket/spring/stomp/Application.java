package org.chronotics.talaria.websocket.spring.stomp;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
//		MessageQueueKey<String> key = new MessageQueueKey<String>("msgqueue",String.class);
		ConcurrentLinkedQueue<String> value = new ConcurrentLinkedQueue<String>();
		MessageQueuePool msgqueues = MessageQueuePool.getInstance();
		msgqueues.put("msgqueue", value);
	
		Thread thread = new Thread(new DummyMessageGenerator());
		thread.start();
		
		ApplicationContext context = SpringApplication.run(Application.class, args);
	}

}
