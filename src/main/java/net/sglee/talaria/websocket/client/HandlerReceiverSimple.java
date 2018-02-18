package net.sglee.talaria.websocket.client;

public class HandlerReceiverSimple<T> extends Handler<T>{

	@Override
	public Object execute(T _obj) throws Exception {
		System.out.println(_obj.toString());
		
		return _obj;
	}
}
