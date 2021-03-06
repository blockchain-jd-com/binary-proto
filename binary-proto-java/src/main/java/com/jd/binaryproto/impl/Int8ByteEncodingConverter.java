package com.jd.binaryproto.impl;

import utils.io.NumberMask;

public class Int8ByteEncodingConverter extends NumberEncodingConverter{
	
	public Int8ByteEncodingConverter(NumberMask numberMask) {
		super(numberMask, byte.class);
	}
	
	@Override
	public Object getDefaultValue() {
		return 0;
	}

	@Override
	protected long encode(Object value) {
		return (byte)value;
	}

	@Override
	protected Object decode(long value) {
		return (byte)value;
	}


}
