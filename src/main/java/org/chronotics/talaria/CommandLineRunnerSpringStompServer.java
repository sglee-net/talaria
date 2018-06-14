package org.chronotics.talaria;

import org.chronotics.talaria.common.TaskExecutor;
import org.chronotics.talaria.common.taskexecutor.MessageQueueToWebsocketServer;
import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.common.TalariaProperties;
import org.chronotics.talaria.websocket.springstompserver.ScheduledUpdates;
import org.chronotics.talaria.websocket.springstompserver.SpringStompServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
public class CommandLineRunnerSpringStompServer implements CommandLineRunner {

	@Autowired
	private ApplicationContext context;
	
	@Override
	public void run(String... arg0) throws Exception {
		
		TalariaProperties properties = 
				(TalariaProperties)context.getBean("talariaProperties");
		assert(properties != null);
		if(properties == null) {
			return;
		}
		
		String queueMapKey = properties.getQueueMapKey();
		
		// register message queue
		if(MessageQueueMap.getInstance().getMessageQueue(queueMapKey) == null) {
			MessageQueue<String> msgqueue = 
					new MessageQueue<String>(
							String.class,
							MessageQueue.default_maxQueueSize,
							MessageQueue.OVERFLOW_STRATEGY.DELETE_FIRST);
			MessageQueueMap.getInstance().put(queueMapKey, msgqueue);
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
		
		scheduledUpdates.setAttribute(queueMapKey,executorWebsocketTask);
	}
}
