package net.sglee.websocket.common;


public interface Actor<T,U> {
	
	public void receive(T _obj);
	public U send(U _obj);
}
