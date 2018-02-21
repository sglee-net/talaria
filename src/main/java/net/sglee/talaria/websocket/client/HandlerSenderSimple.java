package net.sglee.talaria.websocket.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.websocket.api.Session;

public class HandlerSenderSimple<T> extends Handler<T> {
	
	@Override
	public Object execute(T _obj) throws Exception {
		Session session = (Session)attributes.get(JettyWSConnection.KEY_NAME_SESSION);
		long timeout = (long)attributes.get(JettyWSConnection.KEY_NAME_TIMEOUT);
		
		if(session==null) {
    		throw new NullPointerException();
    	}
    	
    	Future<Void> fut;
        fut = session.getRemote().sendStringByFuture("/topic/greetings" + _obj.toString());
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
