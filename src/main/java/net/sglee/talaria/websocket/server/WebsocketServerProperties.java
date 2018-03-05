package net.sglee.talaria.websocket.server;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@PropertySources({
	@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:./config/websocketServer.properties")
	})
@Component
public class WebsocketServerProperties {

	@Value("${port}")
	@Valid
	@NotNull
	private String port;
	public String getPort() {
		return port;
	}
	public void setPort(String _port) {
		port = _port;
	}
	
	@Value("${server.sessionTimeout}")
	@Valid
	@NotNull
	private String sessionTimeout;
	public String getSessionTimeout() {
		return sessionTimeout;
	}
	public void setSessionTimeout(String _sessionTimeout) {
		sessionTimeout = _sessionTimeout;
	}
}
