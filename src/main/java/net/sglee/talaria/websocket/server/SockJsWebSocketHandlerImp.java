package net.sglee.talaria.websocket.server;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.sockjs.transport.SockJsServiceConfig;
import org.springframework.web.socket.sockjs.transport.handler.SockJsWebSocketHandler;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;

@Component
public class SockJsWebSocketHandlerImp extends SockJsWebSocketHandler {

	public SockJsWebSocketHandlerImp(SockJsServiceConfig serviceConfig, WebSocketHandler webSocketHandler,
			WebSocketServerSockJsSession sockJsSession) {
		super(serviceConfig, webSocketHandler, sockJsSession);
		// TODO Auto-generated constructor stub
	}

}
