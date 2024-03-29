package com.jd.binaryproto.impl;

import java.lang.reflect.Method;

import com.jd.binaryproto.BinarySliceSpec;
import com.jd.binaryproto.FieldSpec;

import utils.io.BytesInputStream;
import utils.io.BytesOutputBuffer;
import utils.io.BytesSlices;
import utils.io.SingleBytesSliceArray;

public class FixedFieldEncoder extends AbstractFieldEncoder {
	
	private FixedValueConverter valueConverter;

	public FixedFieldEncoder(BinarySliceSpec sliceSpec, FieldSpec fieldSpec, Method reader,  FixedValueConverter valueConverter) {
		super(sliceSpec, fieldSpec, reader);
		this.valueConverter = valueConverter;
	}

	@Override
	public int encode(Object dataContract, BytesOutputBuffer buffer) {
		int size = 0;
		Object value = readValue(dataContract);
		size += encodeValue(value, buffer);

		return size;
	}

	/**
	 * 把固定长度的值序列化到指定的缓冲区；
	 * 
	 * @param value
	 * @param buffer
	 * @return
	 */
	private int encodeValue(Object value, BytesOutputBuffer buffer) {
		byte[] valueBytes = new byte[sliceSpec.getLength()];
		int size  = valueConverter.encodeValue(value, valueBytes, 0);
		buffer.write(valueBytes);
		return size;
	}

	@Override
	public BytesSlices decode(BytesInputStream bytesStream) {
		return SingleBytesSliceArray.create(bytesStream, sliceSpec.getLength());
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
