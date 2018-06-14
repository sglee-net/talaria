package org.chronotics.talaria.thrift;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.common.taskexecutor.ThriftServiceWithMessageQueue;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ThriftServerProperties.class})
public class MessageTransferThroughThriftServer {
	@Autowired
	private ThriftServerProperties thriftServerProperties;

	private static List<String> keyList = null;
	private static int keySize = 100;
	private static String message = "{\n" + 
			"  \"timestamp\" : 1528804317,\n" + 
			"  \"sender_id\": \"sender\",\n" + 
			"  \"receiver_id\": \"receiver\",\n" + 
			"  \"command\": \"execute training\"\n" + 
			"}\n" + 
			"";
	private static long delay = 5000;
	
	public void startServer() {
		// start thrift server
		ThriftService thriftServiceHandler = 
				new ThriftServiceWithMessageQueue(null);
		ThriftServer.startServer(thriftServiceHandler,thriftServerProperties);		
	}
	
	@BeforeClass
	public static void setup() {
		keyList = new ArrayList<String>();
		for(int i=0; i<keySize; i++) {
			keyList.add("id"+String.valueOf(i));
		}
	}
	
	@AfterClass
	public static void teardown() {
		ThriftServer.stopServer();
	}
	
	@Test
	public void getQueueIdWithMessageQueueInsertion() {
		startServer();
		MessageQueueMap mqMap = MessageQueueMap.getInstance();
		for(int i=0; i<keySize; i++) {
			// register message queue
			MessageQueue<String> mq = 
					new MessageQueue<String>(
							String.class,
							MessageQueue.default_maxQueueSize,
							MessageQueue.OVERFLOW_STRATEGY.DELETE_FIRST);
			boolean rt = mqMap.put(keyList.get(i), mq);
			assertTrue(rt==true);
		}
		for(int i=0; i<keySize; i++) {
			MessageQueue<String> mq = 
					(MessageQueue<String>) 
					mqMap.getMessageQueue(keyList.get(i));
			assertTrue(mq!=null);
		}
	}
	
	@Test
	public void getQueueIdWithoutMessageQueueInsertion() {
		startServer();
		MessageQueueMap mqMap = MessageQueueMap.getInstance();
		for(int i=0; i<keySize; i++) {
			MessageQueue<String> mq = 
					(MessageQueue<String>) 
					mqMap.getMessageQueue(keyList.get(i));
			assertTrue(mq==null);
		}
	}
	
	@Test
	public void insertMessage() {
		startServer();
		MessageQueueMap mqMap = MessageQueueMap.getInstance();
		for(int i=0; i<keySize; i++) {
			MessageQueue<String> mq = 
					(MessageQueue<String>) 
					mqMap.getMessageQueue(keyList.get(i));
			if(mq==null) {
				mq = new MessageQueue<String>(
							String.class,
							MessageQueue.default_maxQueueSize,
							MessageQueue.OVERFLOW_STRATEGY.DELETE_FIRST);
				mqMap.put(keyList.get(i), mq);
			}
		}
		for(int i=0; i<keySize; i++) {
			MessageQueue<String> mq = 
					(MessageQueue<String>) 
					mqMap.getMessageQueue(keyList.get(i));
			mq.add(String.valueOf(i));
		}
		
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(true);
		
		for(int i=0; i<keySize; i++) {
			MessageQueue<String> mq = 
					(MessageQueue<String>) 
					mqMap.getMessageQueue(keyList.get(i));
			assertTrue(mq.size()==0);
		}
	}
}
