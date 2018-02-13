package net.sglee.talaria.websocket.client;

import net.sglee.talaria.websocket.client.Message;

public interface Session {
	public void sendMessage(Message _msg);
	public void sendMessage(String _str);
}
