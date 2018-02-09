package net.sglee.talaria.websocket.server.spring;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WSController {
	@MessageMapping("/hello")///net/sglee/talaria/websocket/server
    @SendTo("/topic/greetings")
    public WSMessage greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new WSMessage("Hello, " + message + "!");
    }
	
	@MessageMapping("/text")
    @SendTo("/topic/greetings")
    public String text(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new String("Hello, " + message + "!");
    }
}
