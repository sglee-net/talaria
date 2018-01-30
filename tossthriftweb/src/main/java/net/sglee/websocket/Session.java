package net.sglee.websocket;

public interface Session {
	public void sendMessage(Message _msg);
	public void sendMessage(String _str);
}
