package net.sglee.talaria.websocket.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import net.sglee.talaria.websocket.common.WebsocketProperties;

@ComponentScan("net.sglee.talaria.websocket.common")
@ComponentScan
public class RunClient {
	// the method for the injection of a global variable
	static private WebsocketProperties properties;
	@Autowired(required = true)
	void setProperties(WebsocketProperties _prop) {
		properties = _prop;
	}
	
	private static JettyWSConnectionPool connectionPool = null;
	private static JettyWSConnection connection = null;
		
	public static void start(String args[]) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("./config/job_bean.xml");		
//		net.sglee.automation.jobcontrol.Job rootJob = (net.sglee.automation.jobcontrol.Job)context.getBean("root");
//		System.out.println(rootJob.getName());
//		Collection<? extends Job> collection=null;
//		try {
//			collection = rootJob.collection();
//		} catch (Exception e3) {
//			e3.printStackTrace();
//		}
//		if(collection==null) {
//			return;
//		}
//		
//		for(net.sglee.automation.jobcontrol.Job job : collection) {
//			System.out.println(job.getName());
//		}
//		
//		net.sglee.automation.jobcontrol.JobManager jobManager=new net.sglee.automation.jobcontrol.JobManager();
//		jobManager.setJobTree(rootJob);
//		jobManager.setNumberOfRepeat(Integer.MAX_VALUE);
//		jobManager.setTerminationWaitTime(3000);
//		
////		Thread thread=new Thread(manager);
////		thread.start();
////		
////		try {
////		thread.join();
////		} catch (InterruptedException e2) {
////		// TODO Auto-generated catch block
////		e2.printStackTrace();
////		}
////		
////		System.out.println("Application ends");

		//String destUri = properties.getClientTargetURL(); //"ws://192.168.1.187:9000/text";
		String destUri = "ws://192.168.1.41:8080/text";
		if (args.length > 1) {
			destUri = args[1];
		}
		
		final int connectionSize=1;
		URI uri=null;
		try {
			uri = new URI(destUri);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
			
		
		String connectionId = "clientCon";
		connectionPool = new JettyWSConnectionPool(connectionId,uri,connectionSize,300,100,2000);
//		for(int i=0;i<connectionSize;i++) {
//			JettyWSConnection connection=connectionPool.getConnection();
//			System.out.println(connection.getId()+", connected");
//			if(!connectionPool.setConnectionActive(connection)) {
//				System.out.println("connection is not activated");
//			}
//		}
		
		connection=connectionPool.getConnection();
		
		Handler<Message> handlerReceiver = 
				new HandlerReceiverSimple();
		connection.setReceiver(handlerReceiver);
		Handler<Message> handlerSender = 
				new HandlerSenderSimple(connection.getSession(),connection.getTimeout());
		connection.setSender(handlerReceiver);
		
//		MessageQueue mq=new MessageQueue(connection.getId());
//		mq.setMessageHandler(handler);
//		MessageQueueRepository mqRepository=MessageQueueRepository.getInstance();
//		mqRepository.put(connection.getId(),mq);
		
//		connection.sendMessage("This is client");
		System.out.println(connection.getId()+", connected");

		Runtime.getRuntime().addShutdownHook(new ShutdownHook(connectionPool));
		
	}
	
	public static void stop() {
		assert( connection != null && connectionPool != null );
		if( connection == null || connectionPool == null ) {
			return;
		}
		
		connectionPool.returnConnection(connection);

		connectionPool.stop();
	}
}
