package com.jd.binaryproto.impl;

import utils.io.BytesOutputBuffer;
import utils.io.BytesSlice;
import utils.io.BytesUtils;

public class BytesValueConverter extends AbstractDynamicValueConverter {

	public BytesValueConverter() {
		super(byte[].class);
	}

	@Override
	public int encodeDynamicValue(Object value, BytesOutputBuffer buffer) {
		byte[] bytes =value == null ? BytesUtils.EMPTY_BYTES :  (byte[]) value;
		int size = bytes.length;
		size += writeSize(size, buffer);

		buffer.write(bytes);
		return size;
	}

	@Override
	public Object decodeValue(BytesSlice dataSlice) {
		return dataSlice.getBytesCopy();
	}

}
