package net.sglee.talaria.websocket;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sglee.talaria.websocket.server.WebsocketProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebsocketProperties.class})
public class WebsocketTest {
	@Autowired
	private WebsocketProperties properties;
	
	@Test 
	public void getSeverProperties() {
		assertEquals(properties.getServerPort(),"8080");
		assertEquals(properties.getSessionTimeout(),"10");
	}
}