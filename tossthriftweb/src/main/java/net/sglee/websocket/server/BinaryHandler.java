package net.sglee.websocket.server;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Component
public class BinaryHandler extends BinaryWebSocketHandler {
	public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
	}
}
