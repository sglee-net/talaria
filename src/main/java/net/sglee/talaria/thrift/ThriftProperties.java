package net.sglee.talaria.thrift;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
@PropertySources({
	@PropertySource(value = "classpath:missing.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:./config/thrift.properties")
	})
public class ThriftProperties {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Value("${server.ip}")
	private String serverIp;
	String getServerIp() {
		return serverIp;
	}
	
	@Value("${server.port}")
	private String serverPort;
	String getServerPort() {
		return serverPort;
	}
	
	@Value("${secure.port}")
	private String securePort;
	String getSecurePort() {
		return securePort;
	}
	
	@Value("${secure.keyStore}")
	private String secureKeyStore;
	String getSecureKeyStore() {
		return secureKeyStore;
	}
	
	@Value("${secure.keyPass}")
	private String secureKeyPass;
	String getSecureKeyPass() {
		return secureKeyPass;
	}
}
