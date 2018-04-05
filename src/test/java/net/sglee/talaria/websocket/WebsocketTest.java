package net.sglee.talaria.websocket;

import static org.junit.Assert.assertEquals;

import org.chronotics.talaria.websocket.springstompserver.SpringStompServerProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sglee.automation.jobcontrol.Application;
import net.sglee.talaria.websocket.server.WebsocketServerProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringStompServerProperties.class})
@TestPropertySource("classpath:./config/springstompserver.properties")
public class WebsocketTest {
	@Autowired
	private SpringStompServerProperties serverProperties;
	
	@Test 
	public void getSeverProperties() {		
		assertEquals(serverProperties.getPort(),"8080");
		assertEquals(serverProperties.getSessionTimeout(),"10");
	}
	
//	@Test
//	public void getClientProperties() {
//		assertEquals(properties.getClientTargetURL(),"ws://192.168.1.187:9000/text");
//	}
}
