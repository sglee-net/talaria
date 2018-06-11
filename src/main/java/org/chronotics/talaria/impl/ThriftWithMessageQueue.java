package org.chronotics.talaria.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.thrift.TException;
import org.chronotics.talaria.common.MessageQueue;
import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.thrift.MessageToJson;
import org.chronotics.talaria.thrift.ThriftService;
import org.chronotics.talaria.thrift.gen.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftWithMessageQueue implements ThriftService {
	private static final Logger logger = 
			LoggerFactory.getLogger(MessageQueue.class);

	@Override
	public void writeMessage(Message _v) throws TException { 
		String id = _v.get_sender_id();
		MessageQueueMap mqMap = MessageQueueMap.getInstance();
		MessageQueue<String> mq = 
				(MessageQueue<String>) mqMap.getMessageQueue(id);
		if(mq == null) {
			mq = new MessageQueue<String>(
						String.class,
						MessageQueue.default_maxQueueSize,
						MessageQueue.OVERFLOW_STRATEGY.DELETE_FIRST);
		}
		mq.add(MessageToJson.convert(_v));
		mqMap.put(id, mq);
	}

	@Override
	public void writeBool(String _id, boolean _v) throws TException {
		MessageQueue<Boolean> mq = 
				(MessageQueue<Boolean>) 
				MessageQueueMap.getInstance().
				getMessageQueue(_id);
		assert(mq != null);
		if(mq != null) {
			mq.add(_v);
		}
	}

	@Override
	public void writeI16(String _id, short _v) throws TException {
		MessageQueue<Short> mq = 
				(MessageQueue<Short>) 
				MessageQueueMap.getInstance().
				getMessageQueue(_id);
		assert(mq != null);
		if(mq != null) {
			mq.add(_v);
		}
	}

	@Override
	public void writeI32(String _id, int _v) throws TException {
		MessageQueue<Integer> mq = 
				(MessageQueue<Integer>) 
				MessageQueueMap.getInstance().
				getMessageQueue(_id);
		assert(mq != null);
		if(mq != null) {
			mq.add(_v);
		}
	}

	@Override
	public void writeI64(String _id, long _v) throws TException {
		MessageQueue<Long> mq = 
				(MessageQueue<Long>) 
				MessageQueueMap.getInstance().
				getMessageQueue(_id);
		assert(mq != null);
		if(mq != null) {
			mq.add(_v);
		}
	}

	@Override
	public void writeDouble(String _id, double _v) throws TException {
		MessageQueue<Double> mq = 
				(MessageQueue<Double>) 
				MessageQueueMap.getInstance().
				getMessageQueue(_id);
		assert(mq != null);
		if(mq != null) {
			mq.add(_v);
		}
	}

	@Override
	public void writeString(String _id, String _v) throws TException {
		// TODO Auto-generated method stub
		MessageQueue<String> mq = 
				(MessageQueue<String>) 
				MessageQueueMap.getInstance().
				getMessageQueue(_id);
		assert(mq != null);
		if(mq != null) {
			mq.add(_v);
		}
	}

	@Override
	public Message readMessage(String _id) throws TException {
//		MessageQueue<Message> mq = 
//				(MessageQueue<Message>) 
//				MessageQueueMap.getInstance().
//				getMessageQueue(_id);
//		assert(mq != null);
//		if(mq == null) {
//			throw new TException("There is no matching queue with id");
//		}
//		Message rt = mq.poll();
//		if(rt == null) {
//			throw new TException("Queue is empty");
//		} else {
//			return rt;
//		}
		
		// TODO Auto-generated method stub
		Message message = new Message();
		String _payload = "Starting the simple server...";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		message.set_timestamp(timestamp.toString());
		message.set_sender_id("sender");
//		message.set_receiver_id(_receiver_id);
//		message.set_subject("subject");
//		message.set_sequence_no(0);
//		message.set_list_bool(new ArrayList<Boolean>());
//		message.set_list_double(new ArrayList<Double>());
//		message.set_list_i16(new ArrayList<Short>());
//		message.set_list_i32(new ArrayList<Integer>());
//		message.set_list_i64(new ArrayList<Long>());
//		message.set_list_string(new ArrayList<String>());
//		message.set_binary(new byte[1]);
		message.set_payload(_payload);
		return message;
	}

	@Override
	public boolean readBool(String _id) throws TException {
		MessageQueue<Boolean> mq = 
				(MessageQueue<Boolean>) 
				MessageQueueMap.getInstance().
				getMessageQueue(_id);
		assert(mq != null);
		if(mq == null) {
			throw new TException("There is no matching queue with id");
		}
		Boolean rt = mq.poll();
		if(rt == null) {
			throw new TException("Queue is empty");
		} else {
			return rt;
		}
	}

	@Override
	public short readI16(String _id) throws TException {
		MessageQueue<Short> mq = 
					(MessageQueue<Short>) 
					MessageQueueMap.getInstance().
					getMessageQueue(_id);
		assert(mq != null);
		if(mq == null) {
			throw new TException("There is no matching queue with id");
		}
		Short rt = mq.poll();
		if(rt == null) {
			throw new TException("Queue is empty");
		} else {
			return rt;
		}
	}

	@Override
	public int readI32(String _id) throws TException {
		MessageQueue<Integer> mq = 
					(MessageQueue<Integer>) 
					MessageQueueMap.getInstance().
					getMessageQueue(_id);
		assert(mq != null);
		if(mq == null) {
			throw new TException("There is no matching queue with id");
		}
		Integer rt = mq.poll();
		if(rt == null) {
			throw new TException("Queue is empty");
		} else {
			return rt;
		}
	}

	@Override
	public long readI64(String _id) throws TException {
		MessageQueue<Long> mq = 
					(MessageQueue<Long>) 
					MessageQueueMap.getInstance().
					getMessageQueue(_id);
		assert(mq != null);
		if(mq == null) {
			throw new TException("There is no matching queue with id");
		}
		Long rt = mq.poll();
		if(rt == null) {
			throw new TException("Queue is empty");
		} else {
			return rt;
		}
	}

	@Override
	public double readDouble(String _id) throws TException {
		MessageQueue<Double> mq = 
					(MessageQueue<Double>) 
					MessageQueueMap.getInstance().
					getMessageQueue(_id);
		assert(mq != null);
		if(mq == null) {
			throw new TException("There is no matching queue with id");
		}
		Double rt = mq.poll();
		if(rt == null) {
			throw new TException("Queue is empty");
		} else {
			return rt;
		}
	}

	@Override
	public String readString(String _id) throws TException {
		MessageQueue<String> mq = 
					(MessageQueue<String>) 
					MessageQueueMap.getInstance().
					getMessageQueue(_id);
		assert(mq != null);
		if(mq == null) {
			throw new TException("There is no matching queue with id");
		}
		String rt = mq.poll();
		if(rt == null) {
			throw new TException("Queue is empty");
		} else {
			return rt;
		}
	}

	@Override
	public boolean writeId(String _id) throws TException {
		MessageQueue<String> mq = 
				new MessageQueue<String>(
						String.class,
						MessageQueue.default_maxQueueSize,
						MessageQueue.OVERFLOW_STRATEGY.DELETE_FIRST);
		MessageQueueMap mqMap = MessageQueueMap.getInstance();
		return mqMap.put(_id, mq);
	}

	@Override
	public List<String> readId() throws TException {
		MessageQueueMap mqMap = MessageQueueMap.getInstance();
		return mqMap.getKeys();
	}
}
