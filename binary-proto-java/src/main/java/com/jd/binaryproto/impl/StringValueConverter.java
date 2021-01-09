package com.jd.binaryproto.impl;

import utils.io.BytesOutputBuffer;
import utils.io.BytesSlice;
import utils.io.BytesUtils;

public class StringValueConverter extends AbstractDynamicValueConverter {

	public StringValueConverter() {
		super(String.class);
	}

	@Override
	public Object decodeValue(BytesSlice dataSlice) {
		return dataSlice.getString();
	}

	@Override
	public int encodeDynamicValue(Object value, BytesOutputBuffer buffer) {
		byte[] bytes = value == null ? BytesUtils.EMPTY_BYTES : BytesUtils.toBytes((String) value);
		int size = bytes.length;
		size += writeSize(bytes.length, buffer);
		buffer.write(bytes);
		return size;
	}

}
