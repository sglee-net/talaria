package net.sglee.talaria.conn.websocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActorSimpleReceiver implements Actor<Message,String> {
	private ConcurrentLinkedQueue<String> messageQueue;
	private Actor<Object,byte[]> postActionActor = null;
	
	public void setPostActionActor(Actor<Object, byte[]> postActionActor) {
		this.postActionActor = postActionActor;
	}

	private void handleHead() {
		messageQueue = new ConcurrentLinkedQueue<String>();
	}
	
	private void handleBody(String _str) {
		messageQueue.add(_str);
	}
	
	private void handleTail() {
		if(postActionActor == null) {
			return;
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for(String str : messageQueue) {
			byte[] bytes = Base64.getDecoder().decode(str);
			try {
				baos.write(bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(postActionActor != null) {
			postActionActor.receive(baos.toByteArray());
		}
	}
	
	@Override
	public void receive(Message _msg) {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootObject = null;
		if(_msg.getPayload() != null) {
			try {
				rootObject = mapper.readTree(_msg.getPayload());
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			assert(rootObject != null);
			if(rootObject != null) {
				JsonNode node = rootObject.get("type");
				switch(node.asText()) {
				case "head":
					handleHead();
					break;
				case "body":
					handleBody(rootObject.get("body").asText());
					break;
				case "tail":
					handleTail();
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public String send(String _str) {
		// TODO Auto-generated method stub
		return null;
	}

}
