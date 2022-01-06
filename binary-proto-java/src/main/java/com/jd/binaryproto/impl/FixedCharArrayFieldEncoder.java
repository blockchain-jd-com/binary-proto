package com.jd.binaryproto.impl;

import java.lang.reflect.Method;

import com.jd.binaryproto.BinarySliceSpec;
import com.jd.binaryproto.FieldSpec;

import utils.io.*;

public class FixedCharArrayFieldEncoder extends FixedArrayFieldEncoder {
	
	private static final short[] EMPTY_ARRAY = {};
	
	public FixedCharArrayFieldEncoder(BinarySliceSpec sliceSpec, FieldSpec fieldSpec, Method reader,
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
		short[] valueArray = values == null ? EMPTY_ARRAY : (short[]) values;
		
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

	@Override
	public short[] decodeField(BytesSlices fieldBytes) {
		short[] values = new short[fieldBytes.getCount()];
		BytesSlice itemSlice;
		for (int i = 0; i < values.length; i++) {
			itemSlice = fieldBytes.getDataSlice(i);
			if (itemSlice.getSize() == 0) {
				values[i] = (short) valueConverter.getDefaultValue();
			} else {
				values[i] = (short) valueConverter.decodeValue(itemSlice);
			}
		}
		return values;
	}

}
