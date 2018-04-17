package org.chronotics.talaria;

import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.common.TalariaProperties;
import org.chronotics.talaria.impl.ThriftToMessageQueue;
import org.chronotics.talaria.thrift.ThriftServer;
import org.chronotics.talaria.thrift.ThriftServerProperties;
import org.chronotics.talaria.thrift.ThriftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CommandLineRunnerThriftServer implements CommandLineRunner {

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
		
		// thrift server properties
		ThriftServerProperties thriftServerProperties = 
				properties.getThriftServerProperties();
		assert(thriftServerProperties != null);
		if(thriftServerProperties == null) {
			return;
		}
		
		// start thrift server
		ThriftService thriftServiceHandler = new ThriftToMessageQueue(queueMapKey);
		ThriftServer.startServer(thriftServiceHandler,thriftServerProperties);
	}
}
