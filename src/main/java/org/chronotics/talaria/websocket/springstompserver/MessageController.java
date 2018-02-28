package org.chronotics.talaria.websocket.springstompserver;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
//  @MessageMapping("/hello")
//  @SendTo("/topic/greetings")
//  public Greeting greeting(HelloMessage message) throws Exception {
//      Thread.sleep(1000); // simulated delay
//      return new Greeting("Hello, " + message.getName() + "!");
// }
 
	@MessageMapping("/execute")
	@SendTo("/topic/reply")
	public String reply(String message) throws Exception {
		Thread.sleep(100); // simulated delay
		return new String("Hi" + message);
	}
}
