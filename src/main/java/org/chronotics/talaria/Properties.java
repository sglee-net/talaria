package org.chronotics.talaria;

import org.chronotics.talaria.thrift.ThriftProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sglee.talaria.websocket.common.WebsocketProperties;

@Component
public class Properties {
	private static class Holder {
		private static final Properties theInstance=new Properties();
	}
	
	public static Properties getInstance() {
		return Holder.theInstance;
	}
	
	@Autowired
	private ThriftProperties thriftProperties;
	@Autowired
	private WebsocketProperties websocketProperties;
	public ThriftProperties getThriftProperties() {
		return thriftProperties;
	}
	public WebsocketProperties getWebsocketProperties() {
		return websocketProperties;
	}
}
