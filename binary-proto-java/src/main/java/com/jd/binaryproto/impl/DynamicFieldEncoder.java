package com.jd.binaryproto.impl;

import java.lang.reflect.Method;

import com.jd.binaryproto.BinarySliceSpec;
import com.jd.binaryproto.FieldSpec;

import utils.io.BytesInputStream;
import utils.io.BytesOutputBuffer;
import utils.io.BytesSlices;
import utils.io.NumberMask;
import utils.io.SingleBytesSliceArray;

public class DynamicFieldEncoder extends AbstractFieldEncoder {

	private DynamicValueConverter valueConverter;
	
	private NumberMask numberMask;

	public DynamicFieldEncoder(BinarySliceSpec sliceSpec, FieldSpec fieldSpec, Method reader,
			DynamicValueConverter valueConverter) {
		super(sliceSpec, fieldSpec, reader);
		this.valueConverter = valueConverter;
		if (valueConverter instanceof NumberEncodingConverter) {
			numberMask = ((NumberEncodingConverter) valueConverter).getNumberMask();
		}
	}

	@Override
	public int encode(Object dataContract, BytesOutputBuffer buffer) {
		int size = 0;
		Object value = readValue(dataContract);
		size += valueConverter.encodeDynamicValue(value, buffer);

		return size;
	}

	@Override
	public BytesSlices decode(BytesInputStream bytesStream) {
		if (numberMask != null) {
			return SingleBytesSliceArray.resolveNumber(numberMask, bytesStream);
		}else {
			return SingleBytesSliceArray.resolveDynamic(bytesStream);
		}
	}

	@Override
	public Object decodeField(BytesSlices fieldBytes) {
		// 非数组的字段，最多只有一个数据片段；
		if (null == fieldBytes || fieldBytes.getCount() == 0) {
			return valueConverter.getDefaultValue();
		}
		return valueConverter.decodeValue(fieldBytes.getDataSlice(0));
	}

}
