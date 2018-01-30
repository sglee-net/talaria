package net.sglee.websocket.server;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@ComponentScan("net.sglee.websocket.server")
//@ComponentScan("net.sglee.kafka.producer.command,net.sglee.kafka.producer.model,net.sglee.kafka.producer,net.sglee.kafka.consumer.control")
//@EnableAutoConfiguration
//@Configuration tags the class as a source of bean definitions for the application context.
//@EnableAutoConfiguration tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
//Normally you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath. This flags the application as a web application and activates key behaviors such as setting up a DispatcherServlet.
//@ComponentScan tells Spring to look for other components, configurations, and services in the hello package, allowing it to find the GreetingController.
@SpringBootApplication
@Component
public class StartServer {

	public static void start(String args[]) {
		ApplicationContext ctx=SpringApplication.run(StartServer.class,args);
	}
	private static final Logger logger = LoggerFactory.getLogger(StartServer.class);
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
	    factory.setPort(9000);
	    factory.setSessionTimeout(10, TimeUnit.MINUTES);
	    factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
	    return factory;
	}
}
