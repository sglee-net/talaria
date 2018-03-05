package org.chronotics.talaria.common;

import java.io.FileOutputStream;

import org.apache.commons.lang3.tuple.Pair;

public class HandlerWriteBinaryFile extends Handler<Pair<FileOutputStream, byte []>> {

	@Override
	public Object execute(Pair<FileOutputStream, byte[]> _obj) throws Exception {
		_obj.getKey().write(_obj.getValue());
		return null;
	}
}
