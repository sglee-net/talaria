package net.sglee.talaria.websocket.client;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class JettyWSConnection implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(JettyWSConnection.class);

	private Handler<Message> receiver = null;
	public void setReceiver(Handler<Message> _obj) {
		receiver = _obj;
	}
	
	private Handler<Message> messageGenerator = null;
	public void setMessageGenerator(Handler<Message> _obj) {
		messageGenerator = _obj;
	}
	
	private Handler<Message> sender = null;
	public void setSender(Handler<Message> _obj) {
		sender = _obj;
	}
	
	private boolean isActivated=false;

	public synchronized boolean isActivated() {
		return isActivated;
	}

	public synchronized void activate() {
		if(this.isActivated()) {
			logger.info("connection is already activated");
			return;
		}
		this.isActivated = true;
		
	}
	
	public synchronized void deactivate() {
		this.isActivated = false;
	}
	
	private WebSocketClient client=null;
	private URI uri=null;
	private ClientUpgradeRequest request=null;
	private String id=null;
	
	public String getId() {
		return id;
	}
	
	private int channelNo=0;
	
	public int getChannelNo() {
		return channelNo;
	}
	
	private long waitingTimeDuringRunning=1000;
	
	public long getWaitingTimeDuringRunning() {
		return waitingTimeDuringRunning;
	}

	public void setWaitingTimeDuringRunning(long waitingTimeDuringRunning) {
		this.waitingTimeDuringRunning = waitingTimeDuringRunning;
	}

	private long waitingTimeToStop=100; // ms

	public long getWaitingTimeToStop() {
		return waitingTimeToStop;
	}

	public void setWaitingTimeToStop(long waitingTimeToStop) {
		this.waitingTimeToStop = waitingTimeToStop;
	}
	
	private long timeout=2000; // msec
    
	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long _timeout) {
		this.timeout = _timeout;
	}

	private JettyWSConnectionPool connectionPool=null;
	
	public JettyWSConnectionPool getConnectionPool() {
		return connectionPool;
	}

	public void setConnectionPool(JettyWSConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	// event sequence : connect => onConnect
	private synchronized void connect() {
		logger.info("open() is called.");
		
		try {
    		client.start();
			Future<Session> future=client.connect(this,uri,request);
			Session result=future.get();
			if(result==null) {
				logger.error("Websocket client is not connected");
				return;
			}
			this.setSession(result);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	this.setActivated(false);
    	this.activate();
    	
        this.closeLatch = new CountDownLatch(1);
	}
	
	public synchronized void reconnect() {
		// stop
		try {
			client.stop();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// wait until client stop
		while(!client.isStopped()) {
			try {
				Thread.sleep(waitingTimeToStop);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// connect
		try {
    		client.start();
			Future<Session> future=client.connect(this,uri,request);
			Session result=future.get();
			if(result==null) {
				logger.error("Websocket client is not connected");
				return;
			}
			this.setSession(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	this.setActivated(false);
		this.activate();

        this.closeLatch = new CountDownLatch(1);
	}

	public synchronized boolean isConnected() {
		return (this.client.isRunning() && this.session!=null)? true : false ;
	}

//	public synchronized boolean isTerminated() {
//		return client.isRunning()? false: true;
//	}
	// event sequence : terminate => onClose
	public synchronized void terminate() {
		logger.info("connection terminate() is called.");
		
//		this.setActivated(false);
		this.deactivate();
		
		try {
			client.stop();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// wait until client stop
		while(!client.isStopped()) {
			try {
				Thread.sleep(waitingTimeToStop);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(this.session!=null) {
			// invoke onClose
			this.session.close();
		}
	}
	
	private CountDownLatch closeLatch=null;
    private Session session=null;
    
    public Session getSession() {
		return session;
	}

	private void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unused")
	private JettyWSConnection() {
    }
    
    public JettyWSConnection(
    		String _id,
    		int _channelNo,
    		WebSocketClient _client,
    		URI _uri,
    		ClientUpgradeRequest _request,
    		long _waitingTimeToStop,
    		long _waitingTimeDuringRunning,
    		long _timeout) {
    	this.id=new String(_id);
    	this.channelNo=_channelNo;
    	assert(_client!=null);
    	this.client=_client;
    	this.uri=_uri;
    	this.request=_request;
    	this.setWaitingTimeToStop(_waitingTimeToStop);
    	this.setWaitingTimeDuringRunning(_waitingTimeDuringRunning);
    	this.setTimeout(_timeout);
    	this.connect();
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration,unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
    	// session.close => onClose
        this.session = null;
        this.closeLatch.countDown(); // trigger latch
        logger.info("Connection closed. "+statusCode+reason);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
    	// client.connect => onConnect
		logger.info("Connection is opened, session : " + session);

//        this.session = session;
        
        try {
            Future<Void> fut;
            fut = session.getRemote().sendStringByFuture("Hello, this is a client. Thnaks for connection");
            fut.get(2,TimeUnit.SECONDS); // wait for send to complete.
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    int i=0;
    
    @OnWebSocketMessage
    public void onMessage(byte[] _bytes,int _offset,int _length) throws Exception {
    	if(!this.isConnected() || !this.isActivated()) {
    		return;
    	}
    	
    	Message message = new Message(_bytes, _offset, _length);
    	
    	if(this.receiver != null) {
    		receiver.execute(message);
    	}
    }
    
    @OnWebSocketMessage
    public void onMessage(String _msg) throws Exception {
    	synchronized(this) {
	    	if(!this.isConnected() || !this.isActivated()) {
	        	return;
	    	}
	    	
	    	Message message = new Message(_msg);
	    	Message result = null;
	    	if(this.receiver != null) {
	    		result = (Message)receiver.execute(message);
	    	}
	    	
	    	Message reply = null;
	    	if(this.messageGenerator != null) {
	    		reply = (Message)messageGenerator.execute(result);
	    	}
	    	
	    	if(this.sender != null && reply != null) {
	    		sender.execute(reply);
	    	}
    	}
    }
    
    public void run() {
    	logger.info("JettyWSConnection is running");
    	
//    	this.setActivated(true);
    	this.activate();
    	while(isActivated()) {
//    		logger.info("JettyWSConnection is running...");

    		try {
				Thread.sleep(this.getWaitingTimeDuringRunning());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
