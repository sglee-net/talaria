package org.chronotics.talaria.websocket.springstompserver;

import java.util.HashMap;
import java.util.Map;

import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.common.TalariaProperties;
import org.chronotics.talaria.impl.DummyMessageGenerator;
import org.chronotics.talaria.common.Handler;
import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.impl.HandlerMessageQueueToWebsocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.chronotics.talaria.common",
		"org.chronotics.talaria.websocket.springstompserver"})
public class Application {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
//		String[] allBeanNames = context.getBeanDefinitionNames();
//		for(String beanName : allBeanNames) {
//			System.out.println(beanName);
//		}
	    
		// getProperties
		TalariaProperties properties = 
				(TalariaProperties)context.getBean("talariaProperties");
		String queueMapKey = properties.getQueueMapKey();//"vib";
		SpringStompServerProperties stompProperties = properties.getSpringStompServerProperties();
		String targetDestination = stompProperties.getTargetDestination(); // "/topic/vib";
		
		// register message queue
		MessageQueue<String> msgqueue = 
				new MessageQueue<String>(
						String.class,
						MessageQueue.default_maxQueueSize,
						MessageQueue.OVERFLOW_STRATEGY.DELETE_FIRST);
		MessageQueueMap.getInstance().put(queueMapKey, msgqueue);
		
		ScheduledUpdates scheduledUpdates = context.getBean(ScheduledUpdates.class);
		
		Handler<SimpMessagingTemplate> handlerWebsocketTask = 
				new HandlerMessageQueueToWebsocket(Handler.PROPAGATION_RULE.SIMULTANEOUSLY, null);
		
		handlerWebsocketTask.putProperty(
				HandlerMessageQueueToWebsocket.targetDestination,
				targetDestination);
		
		scheduledUpdates.setAttribute(queueMapKey, handlerWebsocketTask);
		
		Thread thread = new Thread(new DummyMessageGenerator());
		thread.start();
	}
}
