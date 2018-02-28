package org.chronotics.talaria.websocket.springstompserver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.taskhandler.Handler;
import org.chronotics.talaria.taskhandler.HandlerMessageQueueToWebsocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		String queueMapKey = "vib";
		// register message queue
		ConcurrentLinkedQueue<String> value = new ConcurrentLinkedQueue<String>();
		MessageQueueMap msgqueues = MessageQueueMap.getInstance();
		msgqueues.put(queueMapKey, value);
		
		ApplicationContext context = SpringApplication.run(Application.class, args);
		
		ScheduledUpdates scheduledUpdates = context.getBean(ScheduledUpdates.class);
		
		Handler<SimpMessagingTemplate> handlerWebsocketTask = 
				new HandlerMessageQueueToWebsocket();
		
		Map<String,Object> handlerAttributesWebsocketTask = 
				new HashMap<String,Object>();
		handlerAttributesWebsocketTask.put(HandlerMessageQueueToWebsocket.queueMapKey, queueMapKey);
		handlerAttributesWebsocketTask.put(HandlerMessageQueueToWebsocket.targetTopic, "/topic/vib");
		handlerWebsocketTask.setAttributes(handlerAttributesWebsocketTask);
		
		scheduledUpdates.setHandler(handlerWebsocketTask);
		
		Thread thread = new Thread(new DummyMessageGenerator());
		thread.start();
	}

}
