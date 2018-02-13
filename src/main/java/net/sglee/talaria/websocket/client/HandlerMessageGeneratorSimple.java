package net.sglee.talaria.websocket.client;

public class HandlerMessageGeneratorSimple extends Handler<Message> {

	long i = 0;
	@Override
	public Object execute(Message _obj) throws Exception {
		return new String("This is client message" + String.valueOf(i));
	}

}
