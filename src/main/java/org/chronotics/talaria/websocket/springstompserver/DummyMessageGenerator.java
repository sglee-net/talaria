package org.chronotics.talaria.websocket.springstompserver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.chronotics.talaria.common.MessageQueueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

import net.sglee.util.collection.GenericConcurrentLinkedQueue;

@Component
public class DummyMessageGenerator implements Runnable {
	
//	@Autowired
//	private SpringStompServerProperties properties;
//	public void setProperties(SpringStompServerProperties _properties) {
//		properties = _properties;
//	}
	
	int count = 100;
	@Override
	public void run() {
//		assert(properties != null);
//		if(properties == null) {
//			return;
//		}
		MessageQueueMap pool = MessageQueueMap.getInstance();
		ConcurrentLinkedQueue<String> msgqueue = 
				(ConcurrentLinkedQueue<String>) pool.getMessageQueue("vibration");//properties.getQueueMapKey());//"vib");
		if(msgqueue != null) {
			int i = 0;
			while(msgqueue.size() < count) {
				//Using Date class
				Date date = new Date();
				//Pattern for showing milliseconds in the time "SSS"
				DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");//properties.getDateFormat());//"yyyy/MM/dd HH:mm:ss.SSS");
				String time = sdf.format(date);
				
				double random = (double )(Math.random() * 10 + 1);
//				NumberMessage msg = new NumberMessage("", time, String.valueOf(random));
//				msg.addDataToList("8.1");
//				msg.addDataToList("3.4");
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("time",time);
				jsonObject.addProperty("value",String.valueOf(random));
				msgqueue.add(jsonObject.toString());//Integer.toString(i));
				
				System.out.println(i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
		}
	}

}
