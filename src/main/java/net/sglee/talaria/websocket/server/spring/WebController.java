package net.sglee.talaria.websocket.server.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebController {
	@RequestMapping("/web")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
