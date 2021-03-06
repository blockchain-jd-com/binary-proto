package com.jd.binaryproto.impl;

import utils.io.BytesSlice;
import utils.io.BytesUtils;

public class Int64LongConverter implements FixedValueConverter{

	@Override
	public Class<?> getValueType() {
		return Long.class;
	}

	@Override
	public Object getDefaultValue() {
		return 0;
	}

	@Override
	public int encodeValue(Object value, byte[] buffer, int offset) {
		BytesUtils.toBytes((long)value, buffer, offset);
		return 8;
	}

	@Override
	public Object decodeValue(BytesSlice dataSlice) {
		return dataSlice.getLong();
	}

}
