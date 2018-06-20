package org.chronotics.talaria;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.chronotics.talaria.thrift.ThriftServerProperties;
import org.chronotics.talaria.websocket.springstompserver.SpringStompServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author SG Lee
 * @since 3/20/2015
 * @description
 * General properties
 */

@PropertySources({
	@PropertySource(value = "classpath:spring/properties/missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:spring/properties/mqMap.properties")
	})
@ComponentScan(basePackages = {
		"org.chronotics.talaria.websocket.springstompserver", 
		"org.chronotics.talaria.thrift"})
@Component
public class TalariaProperties {
	
//	@Inject
//	private Environment environment;
	
	public boolean isNull() {
		if( thriftServerProperties == null && 
				springStompServerProperties == null) {
			return true;
		} else {
			return false;
		}
	}
	
	@Autowired
	private ThriftServerProperties thriftServerProperties;
	public ThriftServerProperties getThriftServerProperties() {
//		return new ThriftServerProperties(thriftServerProperties);
		return thriftServerProperties;
	}
	public void setThriftServerProperties(ThriftServerProperties _prop) {
		thriftServerProperties = _prop;
	}
	
	@Autowired
	private SpringStompServerProperties springStompServerProperties;
	public SpringStompServerProperties getSpringStompServerProperties() {
		return springStompServerProperties;
//		return new SpringStompServerProperties(springStompServerProperties);
	}
	public void setSpringStompServerProperties(SpringStompServerProperties _prop) {
		springStompServerProperties = _prop;
	}
	
	@Valid
	@NotNull
	@Value("${mqMap.key}")
	private String mqMapKey;
	public String getMqMapKey() {
		return mqMapKey;
	}
	public void setMqMapKey(String _mqMapKey) {
		mqMapKey = _mqMapKey;
	}
}
