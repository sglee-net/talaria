package net.sglee.websocket;

import net.sglee.util.collection.GenericConcurrentHashMap;

public class SessionRepository extends GenericConcurrentHashMap<String, Session> {
	private static class Holder {
		private static final SessionRepository theInstance=new SessionRepository();
	}
	
	public static SessionRepository getInstance() {
		return Holder.theInstance;
	}
}
