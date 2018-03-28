package org.chronotics.talaria.websocket.springstompserver;

import org.chronotics.talaria.common.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledUpdates {
	private static final Logger logger = 
			LoggerFactory.getLogger(ScheduledUpdates.class);
	
	@Autowired
    private SimpMessagingTemplate template;

	private Handler<SimpMessagingTemplate> handler = null;
	public void setHandler(Handler<SimpMessagingTemplate> _handler) {
		handler = _handler;
	}
	
    @Scheduled(fixedDelay=100)
    public void update(){
    	if(handler == null) {
    		logger.info("handler is null");
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
