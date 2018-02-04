package net.sglee.websocket.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class TextHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(TextHandler.class);
//	private Map<String,ExecutorService> executorMap=new ConcurrentHashMap<String,ExecutorService>();
//	private Map<String,Command> commandMap=new ConcurrentHashMap<String,Command>();
	int i=0;
	public void handleTextMessage(WebSocketSession _session,TextMessage _message) {
		synchronized(this) {
			String sessionId=_session.getId();
//			MessageQueueRepository mqRepo=MessageQueueRepository.getInstance();
//			MessageQueue messageQueque=mqRepo.get(sessionId);
//			if(messageQueque==null) {
//				//create
//				MessageQueue newQueue=new MessageQueue(sessionId);
//				
////				/////////////////////////////////////////////////////
////				Job jobRoot=new JobComposite();
////				Job subjob=new JobLeaf();
////				try {
////					jobRoot.add(subjob);
////				} catch (Exception e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////				
////				String jobName=sessionId;// change this
//////				newQueue.putJob(jobName,jobRoot);
////				newQueue.setJob(jobRoot);
////				logger.info("sessionId: " + sessionId + ", the new MQ is created");
//				
//				// update
////				Message message=new Message(_message.toString(),_message.getPayload());
////				messageQueque.add(message);
//
//			}
			
			logger.info("message id: "+ sessionId + ", message is "+_message);
			
			try {
				_session.sendMessage(new TextMessage("This is a server " + String.valueOf(i)));
//				logger.info("server handle is called");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//"Hello, I am a server, time: "+System.currentTimeMillis()+", index: "+i
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			i++;
			
			
//			String commandName="";
//			ExecutorService executor=executorMap.get(commandName);
//			if(executor==null) {
//				return;
//			}
//			Command command=commandMap.get(commandName);
//			if(command==null) {
//				return;
//			}
//			
//			executor=Executors.newFixedThreadPool(threadSize);
//			// Thread starts
//			//////////////////////////////////////
//			this.executor.execute(command));
//			//////////////////////////////////////
//			try {
//				if(i>100) 
//					return;
//				System.out.println(i+" "+message.getPayload());
//				session.sendMessage(new TextMessage(String.valueOf(i)));//"Hello, I am a server, time: "+System.currentTimeMillis()+", index: "+i
//				i++;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
	}
}
