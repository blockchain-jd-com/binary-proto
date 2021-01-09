package com.jd.binaryproto.impl;

import utils.io.BytesSlice;
import utils.io.BytesUtils;

public class Int32IntWrapperConverter implements FixedValueConverter{

	@Override
	public Class<?> getValueType() {
		return Integer.class;
	}

	@Override
	public Object getDefaultValue() {
		return null;
	}

	@Override
	public int encodeValue(Object value, byte[] buffer, int offset) {
		BytesUtils.toBytes((int)value, buffer, offset);
		return 4;
	}

	@Override
	public Object decodeValue(BytesSlice dataSlice) {
		return dataSlice.getInt();
	}

}
