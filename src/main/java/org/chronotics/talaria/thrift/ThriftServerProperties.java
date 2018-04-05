package org.chronotics.talaria.thrift;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@PropertySources({
//	@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:./config/thriftserver.properties")
	})
@Component
public class ThriftServerProperties {
	@Valid
	@NotNull	
	@Value("${ip}")
	private String ip;
	String getIp() {
		return ip;
	}
	public void setIp(String _ip) {
		ip = _ip;
	}
	
	@Valid
	@NotNull	
	@Value("${port}")
	private String port;
	String getPort() {
		return port;
	}
	public void setPort(String _port) {
		port = _port;
	}
	
	@Valid
	@NotNull	
	@Value("${securePort}")
	private String securePort;
	String getSecurePort() {
		return securePort;
	}
	public void setSecurePort(String _port) {
		securePort = _port;
	}
	
	@Valid
	@NotNull	
	@Value("${secureKeyStore}")
	private String secureKeyStore;
	String getSecureKeyStore() {
		return secureKeyStore;
	}
	public void setSecureKeyStore(String _secureKeyStore) {
		secureKeyStore = _secureKeyStore;
	}
	
	@Valid
	@NotNull	
	@Value("${secureKeyPass}")
	private String secureKeyPass;
	String getSecureKeyPass() {
		return secureKeyPass;
	}
	public void setSecureKeyPass(String _secureKeyPass) {
		secureKeyPass = _secureKeyPass;
	}
}
