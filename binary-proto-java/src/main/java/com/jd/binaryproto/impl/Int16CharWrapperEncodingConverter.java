package com.jd.binaryproto.impl;

import utils.io.NumberMask;

public class Int16CharWrapperEncodingConverter extends NumberEncodingConverter {

	public Int16CharWrapperEncodingConverter(NumberMask numberMask) {
		super(numberMask, Character.class);
	}

	@Override
	public Object getDefaultValue() {
		return '\u0000';
	}

	@Override
	protected long encode(Object value) {
		return (char) value;
	}

	@Override
	protected Object decode(long value) {
		return (char) value;
	}

}
