package net.sglee.websocket.client;

public class ShutdownHook extends Thread {
	JettyWSConnectionPool connectionPool=null;
	public ShutdownHook(JettyWSConnectionPool _threadPool) {
		this.connectionPool=_threadPool;
	}
	
	public void run() {
		System.out.println("ShutdownHook is executing");
		this.connectionPool.stop();
	}
}
