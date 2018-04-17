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
	@PropertySource(value = "classpath:properties/missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:properties/springstompserver.properties")
	})
@Component
public class SpringStompServerProperties {
	@Valid
	@NotNull
	@Value("${stompserver.port}")
	private Integer port;
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer _port) {
		port = _port;
	}
	
	@Valid
	@NotNull
	@Value("${stompserver.sessionTimeout}")
	private Integer sessionTimeout;
	public Integer getSessionTimeout() {
		return sessionTimeout;
	}
	public void setSessionTimeout(Integer _sessionTimeout) {
		sessionTimeout = _sessionTimeout;
	}
	
	@Valid
	@NotNull
	@Value("${stompserver.destinationTopic}")
	private String destinationTopic;
	public String getDestinationTopic() {
		return destinationTopic;
	}
	public void setDestinationTopic(String _destinationTopic) {
		destinationTopic = _destinationTopic;
	}
	
	@Valid
	@NotNull
	@Value("${stompserver.destinationPrefix}")
	private String destinationPrefix;
	public String getDestinationPrefix() {
		return destinationPrefix;
	}
	public void setDestinationPrefix(String _destinationPrefix) {
		destinationPrefix = _destinationPrefix;
	}
	
	@Valid
	@NotNull
	@Value("${stompserver.endPoint}")
	private String endPoint;
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String _endPoint) {
		endPoint = _endPoint;
	}
	
	@Valid
	@NotNull
	@Value("${stompserver.targetDestination}")
	private String targetDestination;
	public String getTargetDestination() {
		return targetDestination;
	}
	public void setTargetDestination(String _targetDestination) {
		targetDestination = _targetDestination;
	}
}
