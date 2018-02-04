package net.sglee.websocket.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActorWriteBytesFile implements Actor<byte[], String> {
	private String filepath;
	
	public ActorWriteBytesFile(String _filepath) {
		filepath = _filepath;
	}

	@Override
	public void receive(byte[] _obj) {
		// TODO Auto-generated method stub
		FileOutputStream ostream = null;
		try {
			ostream = new FileOutputStream(filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ostream.write(_obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String send(String _obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
