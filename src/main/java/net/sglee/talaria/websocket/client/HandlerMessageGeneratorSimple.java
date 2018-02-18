package net.sglee.talaria.websocket.client;

public class HandlerMessageGeneratorSimple<T> extends Handler<T> {

	long i = 0;
	@Override
	public Object execute(T _obj) throws Exception {
		return new String("This is client message" + String.valueOf(i));
	}

}
