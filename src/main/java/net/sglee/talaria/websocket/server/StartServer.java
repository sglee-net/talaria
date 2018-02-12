package net.sglee.talaria.websocket.server;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

//@ComponentScan("net.sglee.talaria.websocket.server")
//@Component

//@EnableAutoConfiguration
//@Configuration tags the class as a source of bean definitions for the application context.
//@EnableAutoConfiguration tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
//Normally you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath. This flags the application as a web application and activates key behaviors such as setting up a DispatcherServlet.
//@ComponentScan tells Spring to look for other components, configurations, and services in the hello package, allowing it to find the GreetingController.

@Configuration
@ComponentScan
//@EnableAutoConfiguration
@ContextConfiguration(classes = {WebsocketProperties.class})
public class StartServer {
	@Autowired
	private static WebsocketProperties properties;
	
	public static void run(String args[]) {
		ApplicationContext context = SpringApplication.run(StartServer.class,args);
		
		WebsocketProperties properties = context.getBean(WebsocketProperties.class);
		System.out.println(properties.getServerPort());
	}
	private static final Logger logger = LoggerFactory.getLogger(StartServer.class);
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
//		System.out.println(properties.getServerPort());
		
	    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
	    factory.setPort(
	    		8080
	    		//Integer.parseInt(properties.getServerPort())//);//8080);//properties.getEnv().getProperty("server.port"))
	    		);
	    factory.setSessionTimeout(
	    		10,
	    		//Integer.parseInt(properties.getSessionTimeout()), //properties.getEnv().getProperty("server.sessionTimeout")
	    		TimeUnit.MINUTES);//Integer.parseInt(properties.getSessionTimeout()), TimeUnit.MINUTES); // 
//	    factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
	    return factory;
	}
	
}
