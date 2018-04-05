package org.chronotics.talaria.thrift;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@PropertySources({
//	@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:./config/thrift.properties")
	})
@Component
public class ThriftProperties {
	@Valid
	@NotNull	
	@Value("${server.ip}")
	private String serverIp;
	String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String _ip) {
		serverIp = _ip;
	}
	
	@Valid
	@NotNull	
	@Value("${server.port}")
	private String serverPort;
	String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String _port) {
		serverPort = _port;
	}
	
	@Valid
	@NotNull	
	@Value("${secure.port}")
	private String securePort;
	String getSecurePort() {
		return securePort;
	}
	public void setSecurePort(String _port) {
		securePort = _port;
	}
	
	@Valid
	@NotNull	
	@Value("${secure.keyStore}")
	private String secureKeyStore;
	String getSecureKeyStore() {
		return secureKeyStore;
	}
	public void setSecureKeyStore(String _secureKeyStore) {
		secureKeyStore = _secureKeyStore;
	}
	
	@Valid
	@NotNull	
	@Value("${secure.keyPass}")
	private String secureKeyPass;
	String getSecureKeyPass() {
		return secureKeyPass;
	}
	public void setSecureKeyPass(String _secureKeyPass) {
		secureKeyPass = _secureKeyPass;
	}
}
