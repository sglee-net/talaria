package net.sglee.talaria.websocket.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWSConnectionPool {
	private static final Logger logger = 
			LoggerFactory.getLogger(JettyWSConnectionPool.class);
	
	private class Terminator implements java.lang.Runnable {
		private long waitingTimeToStop=10;
		
		public long getWaitingTimeToStop() {
			return waitingTimeToStop;
		}
		
		public void setWaitingTimeToStop(long _ms) {
			this.waitingTimeToStop = _ms;
		}
		
		@SuppressWarnings("unused")
		private Terminator() { }
		
		public Terminator(JettyWSConnection _e,long _ms) {
			setClient(_e);
			setWaitingTimeToStop(_ms);
		}

		private JettyWSConnection connection = null;
		public void setClient(JettyWSConnection _e) {
			connection=_e;
		}
		
		public void run() {
			assert(connection != null);
			connection.terminate();
			while(connection.isConnected()) {
				logger.info("A terminator is waiting until a work will finish");
				try {
					Thread.sleep(getWaitingTimeToStop());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private long waitingTimeToStop=100; // ms

	public long getWaitingTimeToStop() {
		return waitingTimeToStop;
	}

	public void setWaitingTimeToStop(long waitingTimeToStop) {
		this.waitingTimeToStop = waitingTimeToStop;
	}

	private long waitingTimeDuringRunning=100; // ms
	
	public long getWaitingTimeDuringRunning() {
		return waitingTimeDuringRunning;
	}

	public void setWaitingTimeDuringRunning(long waitingTimeDuringRunning) {
		this.waitingTimeDuringRunning = waitingTimeDuringRunning;
	}

	private long timeout=2000; //ms
	
	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	private boolean isStopped=false;
//	private Thread runningThread=null;
	
	public synchronized void stop() {
		// stop this thread
		this.isStopped=true;
		
		// stop connection threads
		// all runningConnections will move to freeConnections
		Iterator<Entry<String,JettyWSConnection>> iterator = 
				this.runningConnections.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String,JettyWSConnection> entry=iterator.next();
			JettyWSConnection connection=entry.getValue();
			try {
				// running & free connection lists are cleared
//				this.deactivateConnection(connection);
				this.returnConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(this.executor!=null) {
			this.executor.shutdown();
			try {
				if (!this.executor.awaitTermination(5, TimeUnit.SECONDS)) {
					logger.info("아직 처리중인 작업 존재");
					logger.info("작업 강제 종료 실행");
			        this.executor.shutdownNow();
			        if (!this.executor.awaitTermination(5, TimeUnit.SECONDS)) {
			            logger.info("여전히 종료하지 않은 작업 존재");
			        }
			    }
			} catch (InterruptedException e1) {
				this.executor.shutdownNow();
			    Thread.currentThread().interrupt();
			}
		} else {
			logger.info("executor is null");
		}
		
		List<Runnable> terminatorList=new ArrayList<Runnable>();
		Iterator<JettyWSConnection> freeIterator=this.freeConnections.iterator();
		while(freeIterator.hasNext()) {
			JettyWSConnection connection=freeIterator.next();
			// close session
			connection.terminate();
			if(connection.isConnected()) {
				Runnable terminator=new Terminator(connection,getWaitingTimeToStop());
				terminatorList.add(terminator);
				logger.info("The thread to terminate a client connection is created");
			}
		}
		
		// start terminator thread
		List<Thread> threadList=new ArrayList<Thread>();
		for(Runnable runnable : terminatorList) {
			Thread thread=new Thread(runnable);
			thread.start();
			threadList.add(thread);
		}
		
		// wait until threads end
		for(Thread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		terminatorList.clear();
		threadList.clear();
		
		this.runningConnections.clear();
		this.freeConnections.clear();
		
		logger.info("connection stop");
	}
	
	public synchronized boolean isStopped() {
		return this.isStopped;
	}
		
	private ExecutorService executor=null;
	private URI uri=null;
	private LinkedBlockingQueue<JettyWSConnection> freeConnections = 
			new LinkedBlockingQueue<JettyWSConnection>();
	private Map<String,JettyWSConnection> runningConnections = 
			new ConcurrentHashMap<String,JettyWSConnection>();
	private int freeConnectionCount=0;
	
	@SuppressWarnings("unused")
	private JettyWSConnectionPool() {}
	
	public JettyWSConnectionPool(
			String _connectionId,
			URI _uri,
			int _connectionSize,
			long _waitingTimeToStop,long _waitingTimeDuringRunning,long _timeout) {
		// set properties
		this.uri=_uri;
		this.setWaitingTimeToStop(_waitingTimeToStop);
		this.setWaitingTimeDuringRunning(_waitingTimeDuringRunning);
		this.setTimeout(_timeout);
		
		long currentTime = System.currentTimeMillis();
		// create connections
		for(int i=0;i<_connectionSize;i++) {
			WebSocketClient client = new WebSocketClient();
	        ClientUpgradeRequest request = new ClientUpgradeRequest();
	        String connectionId = String.valueOf(currentTime) + _connectionId + String.valueOf(i);
	        JettyWSConnection connection=
					new JettyWSConnection(
							connectionId,
							i,
							client,
							_uri,
							request,
							this.getWaitingTimeToStop(),
							this.getWaitingTimeDuringRunning(),
							this.getTimeout());
			this.putFreeConnection(connection);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean success=true;
		Iterator<JettyWSConnection> iterator=this.freeConnections.iterator();
		while(iterator.hasNext()) {
			JettyWSConnection connection=iterator.next();
			if(!connection.isConnected()) {
				success=false;
				break;
			}
		}
		
		if(!success) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			success=true;
			iterator=this.freeConnections.iterator();
			while(iterator.hasNext()) {
				JettyWSConnection connection=iterator.next();
				if(!connection.isConnected()) {
					success=false;
					break;
				}
			}
		}
		
		if(success) {
			executor=Executors.newFixedThreadPool(_connectionSize+(int)(_connectionSize*0.3));
		} else {
			logger.info("connection failed");
			this.stop();
		}
	}
	
	private void putFreeConnection(JettyWSConnection _connection) {
		this.freeConnections.add(_connection);
		synchronized(this) {
			this.freeConnectionCount++;
		}
	}
	
	private JettyWSConnection takeFreeConnection() {
		if(freeConnectionCount<=0) {
			return null;
		}
		
		JettyWSConnection rt=null;
		try {
			rt=this.freeConnections.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(rt!=null) {
			synchronized(this) {
				this.freeConnectionCount--;
			}
		}
		return rt;
	}
	
	public JettyWSConnection getConnection() {
		JettyWSConnection connection=this.takeFreeConnection();
		if(connection==null) {
			return null;
		}
		
		this.runningConnections.put(connection.getId(), connection);
		try {
			if(!connection.isConnected()) {
				connection.reconnect();
			}
		} catch (Exception e) {
			// rollback
			this.putFreeConnection(connection);
			this.runningConnections.remove(connection.getId());
			return null;
		}
		connection.activate();
		return connection;
	}
	
	public void returnConnection(JettyWSConnection _connection) {
		JettyWSConnection rt=this.runningConnections.remove(_connection.getId());
		if(rt!=_connection || rt==null) {
			logger.error("Connection closing error, connection id mismatch in runningConnections");
			return;
		}
		_connection.deactivate();
//		_connection.setBynaryMessageHandler(null);
//		_connection.setTextMessageHandler(null);
		this.putFreeConnection(_connection);
	}
}
