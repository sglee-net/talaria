package net.sglee.thrift_service;

import org.apache.thrift.TException;

public class TransferServiceHandler implements TransferService.Iface {

	@Override
	public void writeMessageBegin(Message _v) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeMessage(Message _v) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeMessageEnd() throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeBool(boolean _v) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeI16(short _v) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeI32(int _v) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeI64(long _v) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeDouble(double _v) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeString(String _v) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message readMessageBegin(String _receiver_id, String _subject) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message readMessage(String _receiver_id, String _subject) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean readMessageEnd(String _receiver_id, String _subject) throws TException {
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
