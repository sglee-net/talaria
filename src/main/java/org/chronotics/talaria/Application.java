package org.chronotics.talaria;

import org.chronotics.talaria.thrift.ThriftServer;
import org.chronotics.talaria.thrift.ThriftService;
import org.chronotics.talaria.websocket.springstompserver.DummyMessageGenerator;
import org.chronotics.talaria.websocket.springstompserver.ScheduledUpdates;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.taskhandler.Handler;
import org.chronotics.talaria.taskhandler.HandlerMessageQueueToWebsocket;
import org.chronotics.talaria.taskhandler.HandlerThriftToMessageQueue;
import org.chronotics.talaria.thrift.ThriftHandler;
import org.chronotics.talaria.thrift.ThriftProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.chronotics.talaria.websocket.springstompserver", 
		"org.chronotics.talaria.thrift"})
//@ComponentScan(basePackageClasses = {org.chronotics.talaria.websocket.springstompserver.ScheduledUpdates.class})
public class Application {
	private static ThriftProperties thriftProperties;
	@Autowired(required = true)
	public void setThriftProperties(ThriftProperties _properties) {
		thriftProperties = _properties;
	}

	public static void main(String[] args) {
		String queueMapKey = "vib";
		// register message queue
		ConcurrentLinkedQueue<String> value = new ConcurrentLinkedQueue<String>();
		MessageQueueMap msgqueues = MessageQueueMap.getInstance();
		msgqueues.put(queueMapKey, value);
		
		// run spring boot
		ApplicationContext context =SpringApplication.run(Application.class,args);
		String[] allBeanNames = context.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
        
		// start thrift server
		Handler<Map<String,Object>> handlerThriftTask = 
				new HandlerThriftToMessageQueue();
		
		Map<String,Object> handlerAttributesThriftTask = 
				new HashMap<String,Object>();
		handlerAttributesThriftTask.put(HandlerThriftToMessageQueue.queueMapKey, queueMapKey);
		handlerThriftTask.setAttributes(handlerAttributesThriftTask);
		
		ThriftHandler thriftServiceHandler = new ThriftHandler();
		thriftServiceHandler.setHandler(handlerThriftTask);

		ThriftServer.startServer(thriftServiceHandler,thriftProperties);

		
//		ScheduledUpdates scheduledUpdates = context.getBean(ScheduledUpdates.class);
//		
//		Handler<SimpMessagingTemplate> handlerWebsocketTask = 
//				new HandlerMessageQueueToWebsocket();
//		
//		Map<String,Object> handlerAttributesWebsocketTask = 
//				new HashMap<String,Object>();
//		handlerAttributesWebsocketTask.put(HandlerMessageQueueToWebsocket.queueMapKey, queueMapKey);
//		handlerAttributesWebsocketTask.put(HandlerMessageQueueToWebsocket.targetTopic, "/topic/vib");
//		handlerWebsocketTask.setAttributes(handlerAttributesWebsocketTask);
//		
//		scheduledUpdates.setHandler(handlerWebsocketTask);
//		
//		Thread thread = new Thread(new DummyMessageGenerator());
//		thread.start();
	}

}
