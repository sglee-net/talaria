package net.sglee.talaria.websocket.client;

public class HandlerReceiverSimple extends Handler<Message>{
//	private String id = null;
//	
//	@SuppressWarnings("unused")
//	private HandlerReceiverSimple() {
//	}
//
//	public HandlerReceiverSimple(String _id) {
//		id = (String)_id;
//		
//		MessageQueueRepository mqRepository = MessageQueueRepository.getInstance();
//		MessageQueue mq = new MessageQueue(_id);
//		mqRepository.put(_id,mq);
//	}
	
//	@Override
//	public void setAttribute(Object _obj) throws Exception {
//		if(_obj.getClass() != String.class) {
//			throw new ClassNotFoundException("Attribute should be String type");
//		} else {
//			_id = (String)_obj;
//		}
//	}
	
	@Override
	public Object execute(Message _obj) throws Exception {
		System.out.println(_obj.toString());
//		if(id == null) {
//			throw new NullPointerException();
//		}
//		
//		MessageQueueRepository mqRepository=MessageQueueRepository.getInstance();
//    	MessageQueue mq=mqRepository.get(id);
//    	int size = mq.size();
//		for(int i=0; i<size; i++) {
//			Message message = mq.poll();
//			System.out.println(message.toString());
//		}
//		
//		mq.add(_obj);
		
		return null;
	}
}
