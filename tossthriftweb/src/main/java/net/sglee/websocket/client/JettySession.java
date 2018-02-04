package net.sglee.websocket.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.sglee.websocket.Session;
import net.sglee.websocket.common.Message;

public class JettySession implements Session {
	@SuppressWarnings("unused")
	private JettySession() {}
	
	private org.eclipse.jetty.websocket.api.Session session=null;
	
	public org.eclipse.jetty.websocket.api.Session getSession() {
		return session;
	}

//	public void setSession(org.eclipse.jetty.websocket.api.Session session) {
//		this.session = session;
//	}
	
	long timeout=3000;

	public JettySession(org.eclipse.jetty.websocket.api.Session _session,long _timeout) {
		session=_session;
		timeout=_timeout;
	}
	
	@Override
	public void sendMessage(Message _msg) {
		if(session==null) {
			return;
		}
		Future<Void> fut;
        fut = session.getRemote().sendStringByFuture(_msg.getMessage());
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
	}

	@Override
	public void sendMessage(String _str) {
		if(session==null) {
			return;
		}
		Future<Void> fut;
        fut = session.getRemote().sendStringByFuture(_str);
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
	}

}
