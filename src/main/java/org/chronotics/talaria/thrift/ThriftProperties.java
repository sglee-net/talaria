package org.chronotics.talaria.thrift;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Configuration
@PropertySources({
//	@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:./config/thrift.properties")
	})
@Component
public class ThriftProperties {
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//		return new PropertySourcesPlaceholderConfigurer();
//	}
	
	@Value("${server.ip}")
	private String serverIp;
	String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String _ip) {
		serverIp = _ip;
	}
	
	@Value("${server.port}")
	private String serverPort;
	String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String _port) {
		serverPort = _port;
	}
	
	@Value("${secure.port}")
	private String securePort;
	String getSecurePort() {
		return securePort;
	}
	public void setSecurePort(String _port) {
		securePort = _port;
	}
	
	@Value("${secure.keyStore}")
	private String secureKeyStore;
	String getSecureKeyStore() {
		return secureKeyStore;
	}
	public void setSecureKeyStore(String _secureKeyStore) {
		secureKeyStore = _secureKeyStore;
	}
	
	@Value("${secure.keyPass}")
	private String secureKeyPass;
	String getSecureKeyPass() {
		return secureKeyPass;
	}
	public void setSecureKeyPass(String _secureKeyPass) {
		secureKeyPass = _secureKeyPass;
	}
}
