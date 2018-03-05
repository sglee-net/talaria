package org.chronotics.talaria;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.chronotics.talaria.thrift.ThriftProperties;
import org.chronotics.talaria.websocket.springstompserver.SpringStompServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
@Validated
@PropertySources({
	@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:./config/talaria.properties")
	})
@ComponentScan(basePackages = {
		"org.chronotics.talaria.websocket.springstompserver", 
		"org.chronotics.talaria.thrift"})
@Component
public class Properties {
	@Autowired
	private ThriftProperties thriftProperties;
	public ThriftProperties getThriftProperties() {
		return thriftProperties;
	}
	
	@Autowired
	private SpringStompServerProperties springStompServerProperties;
	public SpringStompServerProperties getSpringStompServerProperties() {
		return springStompServerProperties;
	}
	
	@Valid
	@NotNull
	@Value("${queueMapKey}")
	private String queueMapKey;
	public String getQueueMapKey() {
		return queueMapKey;
	}
	public void setQueueMapKey(String _queueMapKey) {
		queueMapKey = _queueMapKey;
	}
}
