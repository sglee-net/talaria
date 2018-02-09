package net.sglee.talaria.conn.websocket;


public interface Actor<T,U> {
	
	public void receive(T _obj);
	public U send(U _obj);
}
