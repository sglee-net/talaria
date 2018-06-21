package org.chronotics.talaria.thrift;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.apache.thrift.TException;
import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.thrift.gen.TransferService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ThriftClientProperties.class})
public class ThriftClientTest {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(ThriftClientTest.class);
	
	@Autowired
	private ThriftClientProperties properties;
	
	@Test 
	public void getProperties() {
		assertEquals(properties.getIp(),"192.168.0.41");
		assertEquals(properties.getPort(),"9091");
	}

	@Test
	public void startStopThriftClient() {
		ThriftClient client = new ThriftClient();
		client.start(properties);
		
		TransferService.Client service = client.getService();
		logger.info("hello client");
//		try {
//			String ret = service.readString("thrift");
//			assertEquals(null, ret);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			service.writeId("thrift");
			logger.info("Id \"thrift\" is inserted");
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.toString());
		}
//		try {
//			String ret = service.readString("vibration");
//			assertEquals(null, ret);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		MessageQueueMap mqMap = MessageQueueMap.getInstance();
		MessageQueue mq = mqMap.get("thrift");
		
		int count = 5;
		for(int i=0; i<count; i++) {
			try {
				String ret = service.writeString("thrift", String.valueOf(i));
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info(e.toString());
			}
		}
		
		for(int i=0; i<10; i++) {
			try {
				String ret = null;
				ret = service.readString("thrift");
				System.out.println(ret);
//				assertEquals(String.valueOf(i),ret);
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info(e.toString());
			}
		}
	}
}
