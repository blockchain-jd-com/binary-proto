package com.jd.binaryproto.impl;

import utils.io.BytesSlice;
import utils.io.BytesUtils;

public class Int16ShortWrapperConverter implements FixedValueConverter{

	@Override
	public Class<?> getValueType() {
		return Short.class;
	}

	@Override
	public Object getDefaultValue() {
		return null;
	}

	@Override
	public int encodeValue(Object value, byte[] buffer, int offset) {
		BytesUtils.toBytes((short)value, buffer, offset);
		return 2;
	}

	@Override
	public Object decodeValue(BytesSlice dataSlice) {
		return dataSlice.getShort();
	}

}
