package com.jd.binaryproto.impl;

import utils.io.BytesSlice;
import utils.io.BytesUtils;

public class Int16CharWrapperConverter implements FixedValueConverter{

	@Override
	public Class<?> getValueType() {
		return Character.class;
	}

	@Override
	public Object getDefaultValue() {
		return null;
	}

	@Override
	public int encodeValue(Object value, byte[] buffer, int offset) {
		BytesUtils.toBytes((char)value, buffer, offset);
		return 2;
	}

	@Override
	public Object decodeValue(BytesSlice dataSlice) {
		return dataSlice.getChar();
	}

}
