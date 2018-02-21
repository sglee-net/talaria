package org.chronotics.talaria.websocket.javax.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/text", //"/chat/{username}", 
decoders = MessageDecoder.class, 
encoders = MessageEncoder.class)
public class ServerEndPoint {
	private Session session;
    private static Set<ServerEndPoint> endpoints 
      = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();
 
	@OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
//    		@PathParam("username") String username) throws IOException, EncodeException {
        // Get session and WebSocket connection
		this.session = session;
		endpoints.add(this);
//        users.put(session.getId(), username);
 
        Message message = new Message();
//        message.setFrom(username);
        message.setContent("Connected!");
        broadcast(message);
    }
 
    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        // Handle new messages
    	message.setFrom(users.get(session.getId()));
        broadcast(message);
    }
 
    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
    
    private static void broadcast(Message message) 
    	      throws IOException, EncodeException {
    	  
    	        endpoints.forEach(endpoint -> {
    	            synchronized (endpoint) {
    	                try {
    	                    endpoint.session.getBasicRemote().
    	                      sendObject(message);
    	                } catch (IOException | EncodeException e) {
    	                    e.printStackTrace();
    	                }
    	            }
    	        });
    	    }
}
