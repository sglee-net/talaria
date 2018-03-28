package org.chronotics.talaria.impl;

import java.io.FileOutputStream;

import org.apache.commons.lang3.tuple.Pair;
import org.chronotics.talaria.common.Handler;

public class HandlerWriteBinaryFile extends Handler<Pair<FileOutputStream, byte []>> {

	@Override
	public Object execute(Pair<FileOutputStream, byte[]> _obj) throws Exception {
		_obj.getKey().write(_obj.getValue());
		return null;
	}
}
