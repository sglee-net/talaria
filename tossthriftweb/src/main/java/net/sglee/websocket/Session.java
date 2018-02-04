package net.sglee.websocket;

import net.sglee.websocket.common.Message;

public interface Session {
	public void sendMessage(Message _msg);
	public void sendMessage(String _str);
}
