package net.sglee.talaria.websocket.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class Handler<T> {
	protected List<Handler<?>> postHandler = new ArrayList<Handler<?>>();
	protected Map<String,Object> attributes = null;
	
	public void addPostHandler(Handler<?> _handler) {
		postHandler.add(_handler);
	}
	
	public int sizePostHandler() {
		return postHandler.size();
	}
	
	public Iterator<Handler<?>> getPostHandlerIterator() {
		return postHandler.iterator();
	}
	
	public void setAttributes(Map<String,Object> _attributes) {
		attributes = _attributes;
	}
	
	public Map<String,Object> getAttributes() {
		return attributes;
	}
	
	public Object getAttribute(String _key) {
		if(attributes == null) {
			return null;
		}
		
		return attributes.get(_key);
	}
	
//	abstract public void setAttribute(Object _obj) throws Exception;
	public void put(T _obj) throws Exception {
	}
	abstract public Object execute(T _obj) throws Exception;
}
