package org.chronotics.talaria.websocket.spring.stomp;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledUpdates {
	@Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedDelay=500)
    public void update(){
    	MessageQueuePool pool = MessageQueuePool.getInstance();
		ConcurrentLinkedQueue<String> msgqueue = 
	    		  (ConcurrentLinkedQueue<String>) pool.getMessageQueue("msgqueue");
	      if(msgqueue!=null && !msgqueue.isEmpty()) {
	    	  String msg = "";
	    	  int i=0;
	    	  while(!msgqueue.isEmpty()) {
	    		  i++;
	    		  msgqueue.poll();
	    		  msg = String.valueOf(i);
			      System.out.println(msg);
			      template.convertAndSend("/topic/reply",new String(msg + ", " + String.valueOf(msgqueue.size())));
	    	  }
	      }
        
    }
}
