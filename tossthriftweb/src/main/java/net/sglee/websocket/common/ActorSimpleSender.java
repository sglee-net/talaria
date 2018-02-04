package net.sglee.websocket.common;

public class ActorSimpleSender implements Actor<Message,String> {

	@Override
	public void receive(Message _msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String send(String _obj) {
		// TODO Auto-generated method stub
		return new String("this is a client");
	}

}
