package net.sglee.talaria.websocket.client;

import net.sglee.util.collection.GenericConcurrentHashMap;

public class SessionRepository extends GenericConcurrentHashMap<String, Session> {
	private static class Holder {
		private static final SessionRepository theInstance=new SessionRepository();
	}
	
	public static SessionRepository getInstance() {
		return Holder.theInstance;
	}
}
