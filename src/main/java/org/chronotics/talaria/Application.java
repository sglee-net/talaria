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
		
//		String[] allBeanNames = context.getBeanDefinitionNames();
//		logger.info("BeanNames : ");
//		for(String beanName : allBeanNames) {
//			logger.info(beanName);
//		}

	}
	
}
