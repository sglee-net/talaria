package net.sglee.websocket.client;

public interface MessageHandler {
	public String handleTextMessage(String _msg);
	public String handleBynaryMessage(byte[] _bytes, long _offset, long _length);
}