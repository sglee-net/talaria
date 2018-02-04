package net.sglee.websocket.common;

public class Message {
	@SuppressWarnings("unused")
	private Message() {}
	
	enum MESSAGE_TYPE {
		STRING,
		BINARY
	}
	
	private MESSAGE_TYPE type;
	
	public MESSAGE_TYPE getType() {
		return type;
	}
	
	public Message(String _message,String _paylaod) {
		this.setMessage(_message);
		this.setPayload(_paylaod);
		timeStamp = System.currentTimeMillis();
		referenceCount=0;
		
		type=MESSAGE_TYPE.STRING;
	}
	
	public Message(byte[] _bytes,int _offset,int _length) {
		bytes=_bytes;
		offset=_offset;
		length=_length;
		
		type=MESSAGE_TYPE.BINARY;
	}
	
	private String message=null;
	private String payload=null;

	private byte[] bytes;
	private int offset;
	private int length;

	public String getMessage() {
		assert(type==MESSAGE_TYPE.STRING);
		return message;
	}
	
	public byte[] getBytes() {
		assert(type==MESSAGE_TYPE.BINARY);
		return bytes;
	}
	
	public int getOffset() {
		assert(type==MESSAGE_TYPE.BINARY);
		return offset;
	}
	
	public int getLength() {
		assert(type==MESSAGE_TYPE.BINARY);
		return length;
	}

	public void setMessage(String _message) {
		this.message = new String(_message);
	}
	
	public void setMessage(byte[] _bytes,int _offset,int _length) {
		bytes=_bytes;
		offset=_offset;
		length=_length;
	}
	
	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	long timeStamp;

	public long getTimeStamp() {
		return timeStamp;
	}

//	public void setTimeStamp(long timestamp) {
//		this.timeStamp = timestamp;
//	}
	
	int referenceCount=0;

	public int getReferenceCount() {
		return referenceCount;
	}

//	public void setReferenceCount(int referenceCount) {
//		this.referenceCount = referenceCount;
//	}
	
	public void increaseReferenceCount() {
		referenceCount++;
	}
	
	public void decreaseReferenceCount() {
		referenceCount--;
		if(referenceCount < 0) {
			referenceCount=0;
		}
	}
}
