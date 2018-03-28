package org.chronotics.talaria.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SG Lee
 * @since 3/20/2015
 * @description
 * This Class is to execute your own task with the input <T>
 * You can register multiple Handlers and they will run sequentially.
 */

public abstract class Handler<T> {

	@SuppressWarnings("unused")
	private Handler() {}
	
	protected Handler(Handler <T> _nextHandler) {
		nextHandler = _nextHandler;
	}
	
	protected Handler<T> nextHandler = null; 

	// You can execute many tasks with added handlers
	// The code will be run like 
	// this.executeImp() => nextHandler.executeImp() => nextHandler.executeImp()
	public void addNextHandler(Handler<T> _nextHandler) {
		nextHandler = _nextHandler;
	}

	// After deriving this class, you have to implement executeImp()
	// argument
	// _arg : might be the value to execute Object of attributes.
	protected abstract void executeImp(T _arg) throws Exception;

	// for external interface
	public final void execute(T _arg) throws Exception {
		executeImp(_arg);
		if(nextHandler != null) {
			nextHandler.execute(_arg);
		}
	}

	// You can register attributes whose type is Object
	// with which you execute your task in derived executeImp()
	protected Map<String,Object> attributes = new HashMap<String,Object>();

	public void putAttribute(String _key, Object _value) {
		attributes.put(_key, _value);
	}
	
	public Object getAttribute(String _key) {
		return attributes.get(_key);
	}
}
