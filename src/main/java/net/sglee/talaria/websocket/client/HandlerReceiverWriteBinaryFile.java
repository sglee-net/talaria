package net.sglee.talaria.websocket.client;

import java.io.FileOutputStream;

public class HandlerReceiverWriteBinaryFile extends Handler<byte[]>{
	private String filepath;
	
	@SuppressWarnings("unused")
	private HandlerReceiverWriteBinaryFile() {
	}
	
	public HandlerReceiverWriteBinaryFile(String _obj) throws ClassNotFoundException {
		if(_obj.getClass() != String.class) {
			throw new ClassNotFoundException("Attribute should be String type");
		} else {
			filepath = (String)_obj;
		}
	}
	
//	@Override
//	public void setAttribute(Object _obj) throws Exception {
//		if(_obj.getClass() != String.class) {
//			throw new ClassNotFoundException("Attribute should be String type");
//		} else {
//			filepath = (String)_obj;
//		}
//	}
	
	@Override
	public Object execute(byte[] _obj) throws Exception {
		FileOutputStream ostream = new FileOutputStream(filepath);
		ostream.write(_obj);
		
//		try {
//			ostream = new FileOutputStream(filepath);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			ostream.write(_obj);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return null;
	}

}
