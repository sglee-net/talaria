package org.chronotics.talaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.chronotics.talaria",
		"org.chronotics.talaria.common",
		"org.chronotics.talaria.websocket.springstompserver", 
		"org.chronotics.talaria.thrift"})
public class Application {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// run spring boot
		ApplicationContext context = SpringApplication
				.run(Application.class,args);
	}
	
}
