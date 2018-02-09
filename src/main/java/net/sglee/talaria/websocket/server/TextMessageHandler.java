package net.sglee.talaria.websocket.server;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class TextMessageHandler extends TextWebSocketHandler{
	private int i=0;
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		synchronized(this) {
			String sessionId=session.getId();
			
			try {
				session.sendMessage(new TextMessage("This is a server " + String.valueOf(i)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//"Hello, I am a server, time: "+System.currentTimeMillis()+", index: "+i
			i++;
			
			}
	}
}
