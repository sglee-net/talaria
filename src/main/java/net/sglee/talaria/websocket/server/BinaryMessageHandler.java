package net.sglee.talaria.websocket.server;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Component
public class BinaryMessageHandler extends BinaryWebSocketHandler {
	public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
	}
}
