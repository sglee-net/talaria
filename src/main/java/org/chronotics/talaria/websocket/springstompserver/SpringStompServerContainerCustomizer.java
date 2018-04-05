package org.chronotics.talaria.websocket.springstompserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

@Component
public class SpringStompServerContainerCustomizer implements EmbeddedServletContainerCustomizer {

	@Autowired
	SpringStompServerProperties springStompServerProperties;
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		// TODO Auto-generated method stub
		container.setPort(springStompServerProperties.getPort());	
	}

}
