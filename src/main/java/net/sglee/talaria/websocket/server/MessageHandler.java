package net.sglee.talaria.websocket.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@EnableWebMvc
//@ComponentScan(basePackages = {
//"net.sglee.websocket.controller"
//})
public class MessageHandler implements WebSocketConfigurer {
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(textMessageHandler(), "/text");
		registry.addHandler(binaryMessageHandler(), "/binary");
//		registry.addHandler(pongMessageHandler(), "/pong");
//		registry.addHandler(textMessageHandler(), "/sockJS").withSockJS();
	}
	
	@Bean
    public WebSocketHandler textMessageHandler() {
        return new TextMessageHandler();
    }
	
	@Bean
    public WebSocketHandler binaryMessageHandler() {
        return new BinaryMessageHandler();
    }
}
