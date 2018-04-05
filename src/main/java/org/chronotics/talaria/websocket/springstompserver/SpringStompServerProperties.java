package org.chronotics.talaria.websocket.springstompserver;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author SG Lee
 * @since 3/20/2015
 * @description
 */

@Validated
@PropertySources({
//	@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:./config/springstompserver.properties")
	})
@Component
public class SpringStompServerProperties {
	@Valid
	@NotNull
	@Value("${port}")
	private Integer port;
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer _port) {
		port = _port;
	}
	
	@Valid
	@NotNull
	@Value("${sessionTimeout}")
	private Integer sessionTimeout;
	public Integer getSessionTimeout() {
		return sessionTimeout;
	}
	public void setSessionTimeout(Integer _sessionTimeout) {
		sessionTimeout = _sessionTimeout;
	}
	
	@Valid
	@NotNull
	@Value("${destinationTopic}")
	private String destinationTopic;
	public String getDestinationTopic() {
		return destinationTopic;
	}
	public void setDestinationTopic(String _destinationTopic) {
		destinationTopic = _destinationTopic;
	}
	
	@Valid
	@NotNull
	@Value("${destinationPrefix}")
	private String destinationPrefix;
	public String getDestinationPrefix() {
		return destinationPrefix;
	}
	public void setDestinationPrefix(String _destinationPrefix) {
		destinationPrefix = _destinationPrefix;
	}
	
	@Valid
	@NotNull
	@Value("${endPoint}")
	private String endPoint;
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String _endPoint) {
		endPoint = _endPoint;
	}
	
	@Valid
	@NotNull
	@Value("${targetDestination}")
	private String targetDestination;
	public String getTargetDestination() {
		return targetDestination;
	}
	public void setTargetDestination(String _targetDestination) {
		targetDestination = _targetDestination;
	}
	
//	@Valid
//	@NotNull
//	@Value("${scheduledUpdatesDelay}")
//	private String scheduledUpdatesDelay;
//	public String getScheduledUpdatesDelay() {
//		return scheduledUpdatesDelay;
//	}
//	public void setScheduledUpdatesDelay(String _scheduledUpdatesDelay) {
//		scheduledUpdatesDelay = _scheduledUpdatesDelay;
//	}
}
