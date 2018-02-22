package net.sglee.talaria.websocket.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.sockjs.frame.SockJsMessageCodec;
import org.springframework.web.socket.sockjs.transport.SockJsServiceConfig;
import org.springframework.web.socket.sockjs.transport.handler.DefaultSockJsService;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;

@Configuration
@EnableWebSocket
@EnableWebMvc
public class WebSocketConfigurerImp implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(getWebSocketHandlerText(), "/text");
//		registry.addHandler(getWebSocketHandlerBinary(), "/binary");
//		registry.addHandler(pongMessageHandler(), "/pong");
//		registry.addHandler(getWebSocketHandlerSockJs(), "/sockJS").withSockJS();
	}

	@Bean
    public WebSocketHandler getWebSocketHandlerText() {
        return new TextWebSocketHandlerImp();
    }
	
	@Bean
    public WebSocketHandler getWebSocketHandlerBinary() {
        return new BinaryWebSocketHandlerImp();
    }
	
//	private final 
//	SockJsServiceConfig serviceConfig = new SockJsServiceConfig() {
//
//		@Override
//		public TaskScheduler getTaskScheduler() {
//			return DefaultSockJsService.this.getTaskScheduler();
//		}
//
//		@Override
//		public int getStreamBytesLimit() {
//			return DefaultSockJsService.this.getStreamBytesLimit();
//		}
//
//		@Override
//		public long getHeartbeatTime() {
//			return DefaultSockJsService.this.getHeartbeatTime();
//		}
//
//		@Override
//		public int getHttpMessageCacheSize() {
//			return DefaultSockJsService.this.getHttpMessageCacheSize();
//		}
//
//		@Override
//		public SockJsMessageCodec getMessageCodec() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//	};
	
//	@Bean
//	public WebSocketHandler getWebSocketHandlerSockJs() {
//		SockJsServiceConfig serviceConfig = new SockJsServiceConfig();
//		WebSocketHandler webSocketHandler = new TextWebSocketHandler();
//		WebSocketServerSockJsSession sockJsSession = null;
//		return new SockJsWebSocketHandlerImp(serviceConfig, webSocketHandler, sockJsSession);
//	}
}
