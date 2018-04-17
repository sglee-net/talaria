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
	@PropertySource(value = "classpath:properties/missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:properties/thriftserver.properties")
	})
@Component
public class ThriftServerProperties {
//	@Valid
//	@NotNull
//	@Value("${thrift.httpPort}")
//	private String httpPort;
//	String getHttpPort() {
//		return httpPort;
//	}
//	public void setHttpPort(String _port) {
//		httpPort = _port;
//	}
	
	@Valid
	@NotNull	
	@Value("${thrift.ip}")
	private String ip;
	String getIp() {
		return ip;
	}
	public void setIp(String _ip) {
		ip = _ip;
	}
	
	@Valid
	@NotNull	
	@Value("${thrift.port}")
	private String port;
	String getPort() {
		return port;
	}
	public void setPort(String _port) {
		port = _port;
	}
	
	@Valid
	@NotNull	
	@Value("${thrift.securePort}")
	private String securePort;
	String getSecurePort() {
		return securePort;
	}
	public void setSecurePort(String _port) {
		securePort = _port;
	}
	
	@Valid
	@NotNull	
	@Value("${thrift.secureKeyStore}")
	private String secureKeyStore;
	String getSecureKeyStore() {
		return secureKeyStore;
	}
	public void setSecureKeyStore(String _secureKeyStore) {
		secureKeyStore = _secureKeyStore;
	}
	
	@Valid
	@NotNull	
	@Value("${thrift.secureKeyPass}")
	private String secureKeyPass;
	String getSecureKeyPass() {
		return secureKeyPass;
	}
	public void setSecureKeyPass(String _secureKeyPass) {
		secureKeyPass = _secureKeyPass;
	}
}
