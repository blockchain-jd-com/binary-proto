package com.jd.binaryproto.impl;

import utils.io.NumberMask;

public class Int16ShortWrapperEncodingConverter extends NumberEncodingConverter{
	
	public Int16ShortWrapperEncodingConverter(NumberMask numberMask) {
		super(numberMask, Short.class);
	}
	
	@Override
	public Object getDefaultValue() {
		return 0;
	}

	@Override
	protected long encode(Object value) {
		return (short)value;
	}

	@Override
	protected Object decode(long value) {
		return (short)value;
	}


}
