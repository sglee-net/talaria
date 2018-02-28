package org.chronotics.talaria.thrift;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.chronotics.talaria.taskhandler.Handler;
import org.chronotics.talaria.thrift.gen.Message;
import org.chronotics.talaria.thrift.gen.TransferService;

public class ThriftHandler implements TransferService.Iface {
	public static String function = "function";
	public static String readBool = "readBool";
	public static String readDouble = "readDouble";
	public static String readI16 = "readI16";
	public static String readI32 = "readI32";
	public static String readI64 = "readI64";
	public static String readMessage = "readMessage";
	public static String readMessageBegin = "readMessageBegin";
	public static String readMessageEnd = "readMessageEnd";
	public static String readString = "readString";
	public static String writeBool = "writeBool";
	public static String writeDouble = "writeDouble";
	public static String writeI16 = "writeI16";
	public static String writeI32 = "writeI32";
	public static String writeI64 = "writeI64";
	public static String writeMessage = "writeMessage";
	public static String writeMessageBegin = "writeMessageBegin";
	public static String writeMessageEnd = "writeMessageEnd";
	public static String writeString = "writeString";
	public static String arg1 = "arg1";
	public static String arg2 = "arg2";
	
	private Handler<Map<String,Object>> handler;
	
	public void setHandler(Handler<Map<String,Object>> _handler) {
		handler = _handler;
	}

	private Object executeHandler(Map<String,Object> _object) throws Exception {
		return handler.execute(_object);
	}
	
	@Override
	public void writeMessageBegin(Message _v) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.writeMessageBegin);
			map.put(ThriftHandler.arg1,(Object)_v);
			executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void writeMessage(Message _v) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.writeMessage);
			map.put(ThriftHandler.arg1,(Object)_v);
			executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void writeMessageEnd() throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.writeMessageEnd);
			executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void writeBool(boolean _v) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.writeBool);
			map.put(ThriftHandler.arg1,(Object)_v);
			executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void writeI16(short _v) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.writeI16);
			map.put(ThriftHandler.arg1,(Object)_v);
			executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void writeI32(int _v) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.writeI32);
			map.put(ThriftHandler.arg1,(Object)_v);
			executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void writeI64(long _v) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.writeI64);
			map.put(ThriftHandler.arg1,(Object)_v);
			executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void writeDouble(double _v) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.writeDouble);
			map.put(ThriftHandler.arg1,(Object)_v);
			executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void writeString(String _v) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.writeString);
			map.put(ThriftHandler.arg1,(Object)_v);
			executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Message readMessageBegin(String _receiver_id, String _subject) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.readMessageBegin);
			map.put(ThriftHandler.arg1,(Object)_receiver_id);
			map.put(ThriftHandler.arg2,(Object)_subject);
			return (Message)executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Message readMessage(String _receiver_id, String _subject) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.readMessage);
			map.put(ThriftHandler.arg1,(Object)_receiver_id);
			map.put(ThriftHandler.arg2,(Object)_subject);
			return (Message)executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean readMessageEnd(String _receiver_id, String _subject) throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.readMessageEnd);
			map.put(ThriftHandler.arg1,(Object)_receiver_id);
			map.put(ThriftHandler.arg2,(Object)_subject);
			return (boolean)executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean readBool() throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.readBool);
			return (boolean)executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public short readI16() throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.readI16);
			return (short)executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int readI32() throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.readI32);
			return (short)executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long readI64() throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.readI64);
			return (short)executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public double readDouble() throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.readDouble);
			return (short)executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String readString() throws TException {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(ThriftHandler.function, ThriftHandler.readString);
			return (String)executeHandler(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
