package org.chronotics.talaria.thrift;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.common.TalariaProperties;
import org.chronotics.talaria.common.Handler;
import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.impl.HandlerThriftToMessageQueue;
import org.chronotics.talaria.thrift.gen.TransferService;
import org.chronotics.talaria.websocket.springstompserver.SpringStompServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.chronotics.talaria.thrift"})
public class Application {
//	private static ThriftProperties thriftProperties;
//	@Autowired
//	private ThriftProperties autowiredThriftProperties;
//	@PostConstruct
//	private void init() {
//		thriftProperties = this.autowiredThriftProperties;
//	}
	
//	// Thrift Client
//	@RequestMapping("/")
//    @ResponseBody
//    String home() {
//		TTransport transport=FactoryTTransport.getInstance().get("192.168.1.187");
//		TProtocol protocol = new  TBinaryProtocol(transport);
//		TransferService.Client client = new TransferService.Client(protocol);
//        System.out.println("Thrift Client requested");
////        	Message msg = client.getMsg();
//		System.out.println("client.getMsg() ends");   
//        //transport.close();
//        return "Hello World!";
//    }
	
	public static void main(String[] args) {
		// run spring boot
		ApplicationContext context = SpringApplication.run(Application.class,args);

//		// getProperties
//		TalariaProperties properties = 
//				(TalariaProperties)context.getBean("talariaProperties");
//		String queueMapKey = properties.getQueueMapKey();
//		ThriftServerProperties thriftServerProperties = properties.getThriftServerProperties();
//		if(thriftServerProperties == null) {
//			System.out.println("check DI injection of ThriftServerProperties");
//			return;
//		}
		
		String queueMapKey = "vibration";
		ThriftServerProperties thriftServerProperties = 
				(ThriftServerProperties)context.getBean("thriftServerProperties");
		if(thriftServerProperties == null) {
			System.out.println("check DI injection of ThriftServerProperties");
			return;
		}
		// register message queue
		MessageQueue<String> msgqueue = 
				new MessageQueue<String>(
						String.class,
						MessageQueue.default_maxQueueSize,
						MessageQueue.OVERFLOW_STRATEGY.DELETE_FIRST);
		MessageQueueMap.getInstance().put(queueMapKey, msgqueue);	

		// start thrift server
		ThriftHandler thriftServiceHandler = new HandlerThriftToMessageQueue(queueMapKey);
		ThriftServer.startServer(thriftServiceHandler,thriftServerProperties);
	}
}
