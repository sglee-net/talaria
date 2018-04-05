package org.chronotics.talaria.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author SG Lee
 * @since 3/20/2015
 * @description
 * This Class is to execute your own task with the input <T>
 * You can register multiple Handlers and they will run sequentially.
 */

public abstract class Handler<T> implements Callable<T> {
	// for executorService.awaitTermination()
	public static int terminationTimeout = 2000; // ms
	
	// SIMULTANEOUSLY 
	// : execute registered handler simultaneously
	// : the value for each handler is the argument that the first Handler received 
	// STEP_BY_STEP_REGENERATED_ARG
	// : execute registered handler step by step
	// : the value for each handler is the argument that the previous Handler regenerated 
	// STEP_BY_STEP_ORIGINAL_ARG
	// : execute registered handler step by step
	// : the value for each handler is the argument that the first Handler received 
	public static enum PROPAGATION_RULE {
		NOT_DEFINED,
		SIMULTANEOUSLY,
		STEP_BY_STEP_REGENERATED_ARG,
		STEP_BY_STEP_ORIGINAL_ARG
	}

	protected ExecutorService executorService = null;
	
	protected Handler<T> nextHandler = null;
	
	protected int handlerCount = 0;
	
	protected PROPAGATION_RULE propagationRule = PROPAGATION_RULE.NOT_DEFINED;
	
	protected T value;
	
	@SuppressWarnings("unused")
	private Handler() {}
	
	protected Handler(PROPAGATION_RULE _propagationRule, Handler <T> _nextHandler) {
		addNextHandler(_propagationRule, _nextHandler);
	}
	
	private int getHandlerCount() {
		return handlerCount;
	}
	
	protected void increaseHandlerCount() {
		handlerCount++;
	}
	
	private void setPropagationRule(PROPAGATION_RULE _propagationRule) {
		propagationRule = _propagationRule;
	}
	
	public PROPAGATION_RULE getProgatationRule() {
		return propagationRule;
	}
	
	private void setValue(T _value) {
		value = _value;
	}
	
	protected T getValue() {
		return value;
	}

	// You can execute many tasks with added handlers
	// The code will be run like 
	// this.call() => nextHandler.call() => nextHandler.call()
	public void addNextHandler(PROPAGATION_RULE _propagationRule, Handler<T> _nextHandler) {
		this.setPropagationRule(_propagationRule);
		
		nextHandler = _nextHandler;

		if(nextHandler != null) {
			if(nextHandler.getProgatationRule() == PROPAGATION_RULE.NOT_DEFINED) {
				nextHandler.setPropagationRule(this.getProgatationRule());
			}
			
			if(nextHandler.getProgatationRule() != this.getProgatationRule()) {
				throw new IllegalStateException(
						"Propagation rules should be the same"
						);
			}
		}
		
		increaseHandlerCount();
	}

//	 After deriving this class, you have to orveride call()
//	@Override
//	T call() {}
	
	// argument
	// _arg : target to handle
	public final Future<T> execute(T _arg) throws Exception {
		if(executorService == null) {
			executorService = 
					Executors.newSingleThreadExecutor();
					//Executors.newFixedThreadPool(this.getHandlerCount() + (int)(this.getHandlerCount()+0.5));
		}
	
		this.setValue(_arg);
		Future<T> future = executorService.submit(this);

		if(future == null) {
			throw(new NullPointerException());
		}
	
		if(nextHandler == null) {
			return future;
		}
		
		if(propagationRule == PROPAGATION_RULE.SIMULTANEOUSLY) {
			nextHandler.setValue(_arg);
			return nextHandler.execute(_arg);
		} else {
			T prevResult = null;
			try{
				prevResult = future.get(getFutureTimeout(), TimeUnit.MILLISECONDS);
			} catch(Exception e) {
				
			}
			if(propagationRule == PROPAGATION_RULE.STEP_BY_STEP_ORIGINAL_ARG) {
				nextHandler.setValue(_arg);
				return nextHandler.execute(_arg);
			} else if(propagationRule == PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG) {
				nextHandler.setValue(prevResult);
				return nextHandler.execute(prevResult);
			} else {
				throw(new NoSuchMethodException());
			}
		}
	}

	// You can register attributes whose type is Object
	// with which you execute your task in derived executeImp()
	protected Map<String,Object> properties = new HashMap<String,Object>();

	public void putProperty(String _key, Object _value) {
		properties.put(_key, _value);
	}
	
	public Object getProperty(String _key) {
		return properties.get(_key);
	}
	
	public void quitHandler() {
		executorService.shutdown();
        boolean terminationResult = true;
        try {
            terminationResult=
                    executorService.awaitTermination(terminationTimeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	// timeout for future(), time unit should be TimeUnit.MILLISECONDS
	public abstract int getFutureTimeout();
}
