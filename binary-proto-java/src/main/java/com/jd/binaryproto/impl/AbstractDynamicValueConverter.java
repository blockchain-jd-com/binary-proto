package com.jd.binaryproto.impl;

import utils.io.BytesInputStream;
import utils.io.BytesOutputBuffer;
import utils.io.NumberMask;

public abstract class AbstractDynamicValueConverter implements DynamicValueConverter {
	
	protected Class<?> valueType;
	
	private static final NumberMask SIZE_HEAD = NumberMask.NORMAL;
	
	private static final byte[] NULL_HEAD = new byte[1];
	
	static {
		SIZE_HEAD.writeMask(0, NULL_HEAD, 0);
	}
	
	public AbstractDynamicValueConverter(Class<?> valueType) {
		this.valueType = valueType;
	}
	
	@Override
	public Class<?> getValueType() {
		return valueType;
	}
	
	@Override
	public Object getDefaultValue() {
		return null;
	}
	
	protected int writeSize(int size, BytesOutputBuffer buffer) {
		int len = SIZE_HEAD.getMaskLength(size);
		byte[] headerBytes = new byte[len];
		SIZE_HEAD.writeMask(size, headerBytes, 0);
		buffer.write(headerBytes);
		return len;
	}
	
	protected int readSize(BytesInputStream bytesStream) {
		return (int)SIZE_HEAD.resolveMaskedNumber(bytesStream);
	}

}
