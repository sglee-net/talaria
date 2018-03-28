package org.chronotics.talaria.impl;

import org.chronotics.talaria.common.Handler;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class HandlerMessageQueueToWebsocket<T> extends Handler<T>{
	public HandlerMessageQueueToWebsocket(Handler<T> _nextHandler) {
		super(_nextHandler);
		// TODO Auto-generated constructor stub
	}
	
	public static String targetDestination = "targetDestination";
	public static String simpMessagingTemplate = "simpMessaingTemplate";

	@Override
	public void executeImp(T _arg) throws Exception {
		String targetDestination = 
				(String)super.attributes.get(HandlerMessageQueueToWebsocket.targetDestination);
		if(targetDestination == null) {
			throw new NullPointerException("targetDestination is not defined");
		}
		
		SimpMessagingTemplate simpMessagingTemplate = 
				(SimpMessagingTemplate)super.attributes.get(HandlerMessageQueueToWebsocket.simpMessagingTemplate);
		if(simpMessagingTemplate == null) {
			throw new NullPointerException("simpleMessagingTemplate is not defined");
		}
	
		@SuppressWarnings("unchecked")
		Class<T> cls = (Class<T>)_arg.getClass();
		if(cls == Boolean.class) {
			simpMessagingTemplate.convertAndSend(targetDestination,(Boolean)_arg);	
		} else if (cls == Integer.class) {
			simpMessagingTemplate.convertAndSend(targetDestination,(Integer)_arg);	
		} else if (cls == Short.class) {
			simpMessagingTemplate.convertAndSend(targetDestination,(Short)_arg);	
		} else if (cls == Long.class) {
			simpMessagingTemplate.convertAndSend(targetDestination,(Long)_arg);	
		} else if (cls == Double.class) {
			simpMessagingTemplate.convertAndSend(targetDestination,(Double)_arg);	
		} else if (cls == String.class) {
			simpMessagingTemplate.convertAndSend(targetDestination,(String)_arg);	
		} else {
			throw new ClassCastException("undefine type");
		}
    }

}
