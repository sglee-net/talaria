package net.sglee.websocket.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
//@EnableWebMvc
//@ComponentScan(basePackages = {
//        "net.sglee.websocket.controller"
//})
public class MessageHandler implements WebSocketConfigurer {
//	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(textHandler(), "/text");//.withSockJS();
		registry.addHandler(binaryHandler(), "/binary");//.withSockJS();
	}
	
	@Bean
    public WebSocketHandler textHandler() {
        return new TextHandler();
    }
	
	@Bean
    public WebSocketHandler binaryHandler() {
        return new BinaryHandler();
    }
}
