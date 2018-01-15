package net.sglee.thrift_service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;

import org.apache.thrift.transport.TServerTransport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FactoryTServerTransport {
	private static class Holder {
		private static final FactoryTServerTransport theInstance=new FactoryTServerTransport();
	}
	
	public static FactoryTServerTransport getInstance() {
		return Holder.theInstance;
	}
	
	private Map<String,TServerTransport> map=new ConcurrentHashMap<String,TServerTransport>();
	
	public TServerTransport put(String _key,TServerTransport _value) {
		return map.put(_key,_value);
	}
	
	public TServerTransport get(String _key) {
		return map.get(_key);
	}
	
//	Parameters:
//	key - key whose mapping is to be removed from the map
//	Returns:
//	the previous value associated with key, or null if there was no mapping for key
	public TServerTransport remove(String _key) {
		TServerTransport transport=map.get(_key);
		if(transport==null) {
			return null;
		} else {
			transport.close();
			return map.remove(_key);
		}
	}
	
	@PreDestroy
	protected void close() {
		for(Map.Entry<String,TServerTransport> entry : map.entrySet()) {
			entry.getValue().close();
		}
		map.clear();
		
		System.out.println("finalize() is called");
	}
}
