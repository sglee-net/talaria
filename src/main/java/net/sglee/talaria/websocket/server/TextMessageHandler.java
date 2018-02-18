package net.sglee.talaria.websocket.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class TextMessageHandler extends TextWebSocketHandler{
	private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);
	private int i=0;
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		synchronized(this) {
			Thread.sleep(1000);
			System.out.format("No. %d, %s \r\n", i, message.toString());
//			logger.info("No. %d, %s \r\n", i, message.toString());
			
			String sessionId=session.getId();		
			try {
				session.sendMessage(new TextMessage("This is a server " + String.valueOf(i)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
			
			}
	}
}
