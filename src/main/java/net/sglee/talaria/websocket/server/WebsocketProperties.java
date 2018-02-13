package net.sglee.talaria.websocket.server;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
//@Configuration
//@ConfigurationProperties(prefix="server")
@PropertySources({
	@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:./config/websocket.properties")
	})
@Component
public class WebsocketProperties {
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//		return new PropertySourcesPlaceholderConfigurer();
//	}
	
	@Value("${server.port}")
	@Valid
	@NotNull
	private String port;
	public String getServerPort() {
		return port;
	}
	
	@Value("${server.sessionTimeout}")
	@Valid
	@NotNull
	private String sessionTimeout;
	public String getSessionTimeout() {
		return sessionTimeout;
	}
}
