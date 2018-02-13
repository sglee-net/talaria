package net.sglee.talaria.websocket.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HandlerReceiverBinaryMessage extends Handler<Message> {
	private ConcurrentLinkedQueue<String> messageQueue;
	
	@Override
	public Object execute(Message _obj) throws Exception {		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootObject = null;
		if(_obj.getPayload() != null) {
			try {
				rootObject = mapper.readTree(_obj.getPayload());
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
		
		return null;
	}
	
	private void handleHead() {
		messageQueue = new ConcurrentLinkedQueue<String>();
	}
	
	private void handleBody(String _str) {
		messageQueue.add(_str);
	}
	
	private void handleTail() {
		if(postHandler.size() == 0 ) {
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
		
		Iterator<Handler<?>> itr = postHandler.iterator();
		while(itr.hasNext()) {
			@SuppressWarnings("unchecked")
			Handler<byte[]> handler = (Handler<byte[]>) itr.next();
			try {
				handler.execute(baos.toByteArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
