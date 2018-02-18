package net.sglee.talaria.websocket.client;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.websocket.api.Session;

public class HandlerSenderRunnable<T> extends Handler<T> implements Runnable {

	private ConcurrentLinkedQueue<T> linkedQueue = new ConcurrentLinkedQueue<T>();
	
	@Override
	public void run() {
		synchronized(linkedQueue){
			if(!linkedQueue.isEmpty()) {
				T obj = linkedQueue.poll();
				try {
					this.execute(obj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public synchronized void put(T _obj) throws Exception {
		linkedQueue.add(_obj);
	}
	
	@Override
	public Object execute(T _obj) throws Exception {
		Session session = (Session)attributes.get(JettyWSConnection.KEY_NAME_SESSION);
		long timeout = (long)attributes.get(JettyWSConnection.KEY_NAME_TIMEOUT);
		
		if(session==null) {
    		throw new NullPointerException();
    	}
    	
    	Future<Void> fut;
        fut = session.getRemote().sendStringByFuture(_obj.toString());
        try {
			fut.get(timeout,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // wait for send to complete.
        
        return null;
	}

}
