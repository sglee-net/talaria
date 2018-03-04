package org.chronotics.talaria.thrift;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.taskhandler.Handler;
import org.chronotics.talaria.taskhandler.HandlerThriftToMessageQueue;
import org.chronotics.talaria.thrift.gen.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class Application {
	private static ThriftProperties thriftProperties;
	@Autowired(required = true)
	public void setThriftProperties(ThriftProperties _properties) {
		thriftProperties = _properties;
	}
	
	// Thrift Client
	@RequestMapping("/")
    @ResponseBody
    String home() {
		TTransport transport=FactoryTTransport.getInstance().get("192.168.1.187");
		TProtocol protocol = new  TBinaryProtocol(transport);
		TransferService.Client client = new TransferService.Client(protocol);
        System.out.println("Thrift Client requested");
//        	Message msg = client.getMsg();
		System.out.println("client.getMsg() ends");   
        //transport.close();
        return "Hello World!";
    }
	
	public static void main(String[] args) {
		String queueMapKey = "vib";
		// register message queue
		ConcurrentLinkedQueue<String> value = new ConcurrentLinkedQueue<String>();
		MessageQueueMap msgqueues = MessageQueueMap.getInstance();
		msgqueues.put(queueMapKey, value);

		// run spring boot
		ApplicationContext context =SpringApplication.run(ThriftService.class,args);
		
		// start thrift server
		if(thriftProperties == null) {
			System.out.println("check DI injection of properties");
			return;
		}
		Handler<Map<String,Object>> handlerThriftTask = 
				new HandlerThriftToMessageQueue();
		
		Map<String,Object> handlerAttributesThriftTask = 
				new HashMap<String,Object>();
		handlerAttributesThriftTask.put(HandlerThriftToMessageQueue.queueMapKey, queueMapKey);
		handlerThriftTask.setAttributes(handlerAttributesThriftTask);
		
		ThriftHandler thriftServiceHandler = new ThriftHandler();
		thriftServiceHandler.setHandler(handlerThriftTask);

		ThriftServer.startServer(thriftServiceHandler, thriftProperties);
		
	}
}
