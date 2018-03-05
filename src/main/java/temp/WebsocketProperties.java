package temp;

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
@PropertySources({
	@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:./config/websocket.properties")
	})
@Component
public class WebsocketProperties {
	
	@Value("${server.port}")
	@Valid
	@NotNull
	private String port;
	public String getServerPort() {
		return port;
	}
	public void setServerPort(String _port) {
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
	
	@Value("${client.targetURL}")
	@Valid
	@NotNull
	private String clientTargetURL;
	public String getClientTargetURL() {
		return clientTargetURL;
	}
	public void setClientTargetURL(String _clientTargetURL) {
		clientTargetURL = _clientTargetURL;
	}
}
