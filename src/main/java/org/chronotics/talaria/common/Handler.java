package org.chronotics.talaria.common;

import java.util.HashMap;
import java.util.Map;

public abstract class Handler<T> {

	@SuppressWarnings("unused")
	private Handler() {}
	
	protected Handler(Handler <T> _nextHandler) {
		nextHandler = _nextHandler;
	}
	
	protected Handler<T> nextHandler = null; 

	public void addNextHandler(Handler<T> _nextHandler) {
		nextHandler = _nextHandler;
	}
	
	// argument
	// _arg : might be the value to execute Object of attributes.
	protected abstract void executeImp(T _arg) throws Exception;
	
	public final void execute(T _arg) throws Exception {
		executeImp(_arg);
		if(nextHandler != null) {
			nextHandler.execute(_arg);
		}
	}
	
	protected Map<String,Object> attributes = new HashMap<String,Object>();

	public void putAttribute(String _key, Object _value) {
		attributes.put(_key, _value);
	}
	
	public Object getAttribute(String _key) {
		return attributes.get(_key);
	}
//	public void setAttributes(Map<String,Object> _attributes) {
//		attributes = _attributes;
//	}
//	
//	public Map<String,Object> getAttributes() {
//		return attributes;
//	}
//	
//	public Object getAttribute(String _key) {
//		if(attributes == null) {
//			return null;
//		}
//		
//		return attributes.get(_key);
//	}
}
