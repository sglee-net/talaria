package org.chronotics.talaria;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

@Component
public class TalariaContainerCustomizer implements EmbeddedServletContainerCustomizer {
	@Value("${httpServicePort}")
	private Integer httpServicePort;

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		//System.out.println(httpServicePort);
		container.setPort(httpServicePort);//8087);//
	}

}
