package com.jd.binaryproto.impl;

import utils.io.BytesOutputBuffer;
import utils.io.BytesSlice;
import utils.io.NumberMask;

public abstract class NumberEncodingConverter implements DynamicValueConverter {

	private NumberMask numberMask;

	private Class<?> valueType;

	public NumberEncodingConverter(NumberMask numberMask, Class<?> valueType) {
		this.numberMask = numberMask;
		this.valueType = valueType;
	}

	@Override
	public Class<?> getValueType() {
		return valueType;
	}
	
	public NumberMask getNumberMask() {
		return numberMask;
	}

	@Override
	public int encodeDynamicValue(Object value, BytesOutputBuffer buffer) {
		long number = encode(value);
		return numberMask.writeMask(number, buffer);
	}

	@Override
	public Object decodeValue(BytesSlice dataSlice) {
		long value = numberMask.resolveMaskedNumber(dataSlice);
		return decode(value);
	}

	protected abstract long encode(Object value);

	protected abstract Object decode(long value);

}
