package com.jd.binaryproto.impl;

import java.lang.reflect.Method;

import com.jd.binaryproto.BinarySliceSpec;
import com.jd.binaryproto.FieldSpec;

import utils.io.BytesOutputBuffer;
import utils.io.NumberMask;

/**
 * 针对可转换为原生类型 short[] 数组的字段类型的编码器；
 * 
 * @author huanghaiquan
 *
 */
public class DynamicShortArrayFieldEncoder extends DynamicArrayFieldEncoder {
	
	public static final short[] EMPTY_VALUES = {};

	public DynamicShortArrayFieldEncoder(BinarySliceSpec sliceSpec, FieldSpec fieldSpec, Method reader,
			DynamicValueConverter valueConverter) {
		super(sliceSpec, fieldSpec, reader, valueConverter);
	}

	@Override
	protected int encodeArrayDynamic(Object values, BytesOutputBuffer buffer) {
		int size = 0;
		
		short[] valuesArray = values == null ? EMPTY_VALUES : (short[])values;
		int count = valuesArray.length;
		byte[] countBytes = NumberMask.NORMAL.generateMask(count);
		buffer.write(countBytes);
		size += countBytes.length;

		for (int i = 0; i < count; i++) {
			size += valueConverter.encodeDynamicValue(valuesArray[i], buffer);
		}

		return size;
	}
}
