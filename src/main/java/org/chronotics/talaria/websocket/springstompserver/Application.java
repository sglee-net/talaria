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
		ApplicationContext context = SpringApplication.run(Application.class, args);
		SpringStompServerProperties properties = 
				(SpringStompServerProperties)context.getBean("springStompServerProperties");
//		String[] allBeanNames = context.getBeanDefinitionNames();
//		for(String beanName : allBeanNames) {
//			System.out.println(beanName);
//		}
	    
		// properties
		String queueMapKey = properties.getQueueMapKey();//"vib";
		String targetDestination = properties.getTargetDestination(); // "/topic/vib";
		
		// register message queue
		ConcurrentLinkedQueue<String> value = new ConcurrentLinkedQueue<String>();
		MessageQueueMap msgqueues = MessageQueueMap.getInstance();
		msgqueues.put(queueMapKey, value);
		
		ScheduledUpdates scheduledUpdates = context.getBean(ScheduledUpdates.class);
		
		Handler<SimpMessagingTemplate> handlerWebsocketTask = 
				new HandlerMessageQueueToWebsocket();
		
		Map<String,Object> handlerAttributes = 
				new HashMap<String,Object>();
		handlerAttributes.put(HandlerMessageQueueToWebsocket.queueMapKey, queueMapKey);
		handlerAttributes.put(HandlerMessageQueueToWebsocket.targetDestination, targetDestination);//"/topic/vib");
		handlerWebsocketTask.setAttributes(handlerAttributes);
		
		scheduledUpdates.setHandler(handlerWebsocketTask);
		
		Thread thread = new Thread(new DummyMessageGenerator());
		thread.start();
	}

}
