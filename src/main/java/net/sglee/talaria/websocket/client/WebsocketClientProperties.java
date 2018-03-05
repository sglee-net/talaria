package net.sglee.talaria.websocket.client;

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
	@PropertySource("classpath:./config/websocketClient.properties")
	})
@Component
public class WebsocketClientProperties {
	
	@Value("${targetURL}")
	@Valid
	@NotNull
	private String targetURL;
	public String getTargetURL() {
		return targetURL;
	}
	public void setTargetURL(String _targetURL) {
		targetURL = _targetURL;
	}
}
