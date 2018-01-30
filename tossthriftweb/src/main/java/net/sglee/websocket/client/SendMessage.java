package net.sglee.websocket.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.websocket.api.Session;

import net.sglee.websocket.Message;

public class SendMessage implements Runnable {

	private Session session;
	private Message message;
	public void setMessage(Session _session,String _msg) {
		session=_session;
		message=new Message(_msg,_msg);
	}
	public void run() {
		// TODO Auto-generated method stub
		Future<Void> fut;
        fut = session.getRemote().sendStringByFuture("Hello, this is a client, index is "+message);
        try {
			fut.get(2,TimeUnit.SECONDS);
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
