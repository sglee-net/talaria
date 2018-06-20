package org.chronotics.talaria;

import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.common.TaskExecutor;
import org.chronotics.talaria.common.taskexecutor.MessageQueueToWebsocketServer;
import org.chronotics.talaria.common.taskexecutor.ThriftServiceWithMessageQueue;
import org.chronotics.talaria.thrift.ThriftServer;
import org.chronotics.talaria.thrift.ThriftServerProperties;
import org.chronotics.talaria.thrift.ThriftService;
import org.chronotics.talaria.websocket.springstompserver.SpringStompServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.chronotics.talaria",
		"org.chronotics.talaria.common",
		"org.chronotics.talaria.websocket.springstompserver", 
		"org.chronotics.talaria.thrift"})
public class Application {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(Application.class);
	
	static private ThriftServer thriftServer = null;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// run spring boot
		ApplicationContext context = SpringApplication
				.run(Application.class,args);
		
		String[] allBeanNames = context.getBeanDefinitionNames();
		logger.info("BeanNames : ");
		for(String beanName : allBeanNames) {
			logger.info(beanName);
		}
		
		{
			TalariaProperties properties = 
					(TalariaProperties)context.getBean("talariaProperties");
			
			assert(properties != null);
			if(properties == null) {
				return;
			}
			
			String mqMapKey = properties.getMqMapKey();
			
			// register message queue
			if(MessageQueueMap.getInstance().get(mqMapKey) == null) {
				MessageQueue<String> msgqueue = 
						new MessageQueue<String>(
								String.class,
								MessageQueue.default_maxQueueSize,
								MessageQueue.OVERFLOW_STRATEGY.DELETE_FIRST);
				MessageQueueMap.getInstance().put(mqMapKey, msgqueue);
			}
			
			// websocket server properties
			SpringStompServerProperties stompProperties = 
					properties.getSpringStompServerProperties();
			assert(stompProperties != null);
			if(stompProperties == null) {
				return;
			}
			
			String targetDestination = stompProperties.getTargetDestination();
			
			// start websocket server
			ScheduledUpdates scheduledUpdates = context.getBean(ScheduledUpdates.class);
			
			TaskExecutor<SimpMessagingTemplate> executorWebsocketTask = 
					new MessageQueueToWebsocketServer(
							TaskExecutor.PROPAGATION_RULE.SIMULTANEOUSLY, 
							null);
			
			executorWebsocketTask.putProperty(
					MessageQueueToWebsocketServer.targetDestination,
					targetDestination);
			
			scheduledUpdates.setAttribute(mqMapKey,executorWebsocketTask);
		}
		
		{
			TalariaProperties properties = 
					(TalariaProperties)context.getBean("talariaProperties");
						
			assert(properties != null);
			if(properties == null) {
				return;
			}
			if(properties.isNull()) {
				logger.error("TalariaProperties is null");
				return;
			}
			
			// thrift server properties
			ThriftServerProperties thriftServerProperties = 
					properties.getThriftServerProperties();
			
//			logger.info(thriftServerProperties.getIp());
//			logger.info(thriftServerProperties.getPort());
//			logger.info(thriftServerProperties.getSecureServer());


			assert(thriftServerProperties != null);
			if(thriftServerProperties == null) {
				return;
			}
			assert(!thriftServerProperties.isNull());
			if(thriftServerProperties.isNull()) {
				logger.error("ThriftServerProperties is null");
				return;
			}
			
	//		ThriftServerProperties prop = new ThriftServerProperties();
	//		prop.setIp(thriftServerProperties.getIp());
	//		prop.setPort(thriftServerProperties.getPort());
	//		prop.setSecureKeyPass(thriftServerProperties.getSecureKeyPass());
	//		prop.setSecureKeyStore(thriftServerProperties.getSecureKeyStore());
	//		prop.setSecurePort(thriftServerProperties.getSecurePort());
	//		prop.setSecureServer(thriftServerProperties.getSecureServer());
			
//			// start thrift server
//			ThriftService thriftServiceHandler = 
//					new ThriftServiceWithMessageQueue(null);
//			thriftServer = new ThriftServer();
//			thriftServer.start(thriftServiceHandler,thriftServerProperties);
			
			// start thrift server
			ThriftService thriftServiceHandler = new ThriftServiceWithMessageQueue(null);
			thriftServer = new ThriftServer();
			thriftServer.start(thriftServiceHandler,thriftServerProperties);
		}
	}
	
}
