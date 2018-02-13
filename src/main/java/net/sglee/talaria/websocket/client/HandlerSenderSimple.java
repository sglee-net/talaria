package net.sglee.talaria.websocket.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.websocket.api.Session;

public class HandlerSenderSimple extends Handler<Message> {
	private Session session = null;
	private long timeout = 2000; // msec
	
	@SuppressWarnings("unused")
	private HandlerSenderSimple() {
	}
	
	public HandlerSenderSimple(Session _session, long _timeout) {
		session = _session;
		timeout = _timeout;
	}
	
	@Override
	public Object execute(Message _obj) throws Exception {
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
