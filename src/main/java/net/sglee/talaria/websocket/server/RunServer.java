package net.sglee.talaria.websocket.server;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ContextConfiguration;

//@EnableAutoConfiguration
//@Configuration tags the class as a source of bean definitions for the application context.
//@EnableAutoConfiguration tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
//Normally you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath. This flags the application as a web application and activates key behaviors such as setting up a DispatcherServlet.
//@ComponentScan tells Spring to look for other components, configurations, and services in the hello package, allowing it to find the GreetingController.

//@Configuration

@ComponentScan("net.sglee.talaria.websocket.common")
@ComponentScan
public class RunServer {
	private static final Logger logger = LoggerFactory.getLogger(RunServer.class);

	// the method for the injection of a global variable
	static private WebsocketServerProperties properties;
	@Autowired(required = true)
	void setProperties(WebsocketServerProperties _prop) {
		properties = _prop;
	}
	
	public static void start(String args[]) {
		ApplicationContext context = SpringApplication.run(RunServer.class,args);
		System.out.println(properties.getPort());
	}
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
	    factory.setPort(
	    		Integer.parseInt(properties.getPort()) // 8080
	    		);
	    factory.setSessionTimeout(
	    		Integer.parseInt(properties.getSessionTimeout()), // 10
	    		TimeUnit.MINUTES);
//	    factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
	    return factory;
	}
}
