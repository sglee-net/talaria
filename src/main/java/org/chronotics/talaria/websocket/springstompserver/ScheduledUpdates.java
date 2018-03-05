package org.chronotics.talaria.websocket.springstompserver;

import org.chronotics.talaria.taskhandler.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledUpdates {
	@Autowired
    private SimpMessagingTemplate template;

	private Handler<SimpMessagingTemplate> handler = null;
	public void setHandler(Handler<SimpMessagingTemplate> _handler) {
		handler = _handler;
	}
	
    @Scheduled(fixedDelay=50)
    public void update(){
    	if(handler == null) {
    		return;
    	}
    	
    	try {
			handler.execute((SimpMessagingTemplate)template);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
