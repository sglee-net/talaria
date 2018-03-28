package org.chronotics.talaria.impl;

import org.apache.thrift.TException;
import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.thrift.MessageToJson;
import org.chronotics.talaria.thrift.ThriftService;
import org.chronotics.talaria.thrift.gen.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftToMessageQueue implements ThriftService {
	private static final Logger logger = 
			LoggerFactory.getLogger(MessageQueue.class);

	public ThriftToMessageQueue(String _queueMapKey) {
		setQueueMapKey(_queueMapKey);
	}
	
	private String queueMapKey = "";
	String getQueueMapKey() {
		return queueMapKey;
	}
	
	void setQueueMapKey(String _key) {
		queueMapKey = _key;
	}

	@Override
	public void writeMessageBegin(Message _v) throws TException {
		MessageQueue<String> msgqueue = 
				(MessageQueue<String>) 
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		msgqueue.add(MessageToJson.convert(_v));
	}

	@Override
	public void writeMessage(Message _v) throws TException { 
		MessageQueueMap queueMap = MessageQueueMap.getInstance();
		
		MessageQueue<String> msgqueue = 
				(MessageQueue<String>) 
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		msgqueue.add(MessageToJson.convert(_v));
//		System.out.println(_v._timestamp);
	}

	@Override
	public void writeMessageEnd(Message _v) throws TException {
		MessageQueue<String> msgqueue = 
				(MessageQueue<String>) 
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		msgqueue.add(MessageToJson.convert(_v));
	}

	@Override
	public void writeBool(boolean _v) throws TException {
		MessageQueue<Boolean> msgqueue = 
				(MessageQueue<Boolean>) 
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		msgqueue.add(_v);
		
	}

	@Override
	public void writeI16(short _v) throws TException {
		MessageQueue<Short> msgqueue = 
				(MessageQueue<Short>) 
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		msgqueue.add(_v);
		
	}

	@Override
	public void writeI32(int _v) throws TException {
		MessageQueue<Integer> msgqueue = 
				(MessageQueue<Integer>) 
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		msgqueue.add(_v);
		
	}

	@Override
	public void writeI64(long _v) throws TException {
		MessageQueue<Long> msgqueue = 
				(MessageQueue<Long>) 
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		msgqueue.add(_v);
		
	}

	@Override
	public void writeDouble(double _v) throws TException {
		MessageQueue<Double> msgqueue = 
				(MessageQueue<Double>) 
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		msgqueue.add(_v);
		
	}

	@Override
	public void writeString(String _v) throws TException {
		// TODO Auto-generated method stub
		MessageQueue<String> msgqueue = 
				(MessageQueue<String>) 
				MessageQueueMap.getInstance().
				getMessageQueue(queueMapKey);
		assert(msgqueue != null);
		msgqueue.add(_v);
		
	}

	@Override
	public Message readMessageBegin(String _receiver_id) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message readMessage(String _receiver_id) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean readMessageEnd(String _receiver_id) throws TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean readBool() throws TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public short readI16() throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readI32() throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long readI64() throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double readDouble() throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String readString() throws TException {
		// TODO Auto-generated method stub
		return null;
	}	
}
