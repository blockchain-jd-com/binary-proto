package com.jd.binaryproto.impl;

import java.lang.reflect.Method;

import com.jd.binaryproto.BinarySliceSpec;
import com.jd.binaryproto.FieldSpec;

import utils.io.BytesOutputBuffer;
import utils.io.NumberMask;

public class FixedObjectArrayFieldEncoder extends FixedArrayFieldEncoder {
	
	private static final Object[] EMPTY_ARRAY = {};
	
	public FixedObjectArrayFieldEncoder(BinarySliceSpec sliceSpec, FieldSpec fieldSpec, Method reader,
			FixedValueConverter valueConverter) {
		super(sliceSpec, fieldSpec, reader, valueConverter);
	}

	/**
	 * 对数组类型的值进行固定长度的编码；
	 * 
	 * @param values
	 * @param buffer
	 * @return
	 */
	protected int encodeArray(Object values, BytesOutputBuffer buffer) {
		Object[] valueArray = values == null ? EMPTY_ARRAY : (Object[]) values;
		
		int count = valueArray.length;

		int counterSize = NumberMask.NORMAL.getMaskLength(count);
		int elementSize = sliceSpec.getLength();

		int size = counterSize + elementSize * count;
		byte[] outbuff = new byte[size];
		NumberMask.NORMAL.writeMask(count, outbuff, 0);

		for (int i = 0; i < count; i++) {
			valueConverter.encodeValue(valueArray[i], outbuff, counterSize + elementSize * i);
		}

		buffer.write(outbuff);
		return size;
	}


}
