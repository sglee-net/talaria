package net.sglee.talaria.websocket.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@PropertySources({
	@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:./config/websocket.properties")
	})
public class WebsocketProperties {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Value("${server.ip}")
	private String serverIp;
	public String getServerIp() {
		return serverIp;
	}
	
	@Value("${server.port}")
	private String serverPort;
	public String getServerPort() {
		return serverPort;
	}
	
	@Value("${server.sessionTimeout}")
	private String sessionTimeout;
	public String getSessionTimeout() {
		return sessionTimeout;
	}
}
