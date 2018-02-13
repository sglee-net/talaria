package net.sglee.talaria.websocket.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Handler<T> {
	protected List<Handler<?>> postHandler = new ArrayList<Handler<?>>();
	
	public void addPostHandler(Handler<?> _handler) {
		postHandler.add(_handler);
	}
	
	public int sizePostHandler() {
		return postHandler.size();
	}
	
	public Iterator<Handler<?>> getPostHandlerIterator() {
		return postHandler.iterator();
	}
	
//	abstract public void setAttribute(Object _obj) throws Exception;
	abstract public Object execute(T _obj) throws Exception;
}
