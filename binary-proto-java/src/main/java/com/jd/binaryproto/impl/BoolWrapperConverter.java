package com.jd.binaryproto.impl;

import utils.io.BytesSlice;

public class BoolWrapperConverter implements FixedValueConverter{

	@Override
	public Class<?> getValueType() {
		return Boolean.class;
	}

	@Override
	public Object getDefaultValue() {
		return null;
	}

	@Override
	public int encodeValue(Object value, byte[] buffer, int offset) {
		buffer[offset] = ((Boolean)value).booleanValue() ? (byte)1 : (byte)0;
		return 1;
	}

	@Override
	public Object decodeValue(BytesSlice dataSlice) {
		return dataSlice.getByte() == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

}
