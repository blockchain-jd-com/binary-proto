package com.jd.binaryproto.impl;

import java.lang.reflect.Method;

import com.jd.binaryproto.BinarySliceSpec;
import com.jd.binaryproto.FieldSpec;

import utils.io.BytesOutputBuffer;
import utils.io.BytesSlice;
import utils.io.BytesSlices;
import utils.io.FixedBytesSliceArray;
import utils.io.NumberMask;

public class FixedByteArrayFieldEncoder extends FixedArrayFieldEncoder {
	
	private static final byte[] EMPTY_ARRAY = {};
	
	public FixedByteArrayFieldEncoder(BinarySliceSpec sliceSpec, FieldSpec fieldSpec, Method reader, FixedValueConverter valueConverter) {
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
		byte[] valueArray = values == null ? EMPTY_ARRAY : (byte[]) values;
		
		int count = valueArray.length;

		int counterSize = NumberMask.NORMAL.getMaskLength(count);
//		int elementSize = sliceSpec.getLength();
//		int size = counterSize + elementSize * count;
		int size = counterSize + count;
		byte[] outbuff = new byte[size];
		NumberMask.NORMAL.writeMask(count, outbuff, 0);

//		for (int i = 0; i < count; i++) {
//			valueConverter.encodeValue(valueArray[i], outbuff, counterSize + elementSize * i);
//		}

		// 优化：直接复制数组；
		System.arraycopy(valueArray, 0, outbuff, counterSize, count);
		
		buffer.write(outbuff);
		return size;
	}

	@Override
	public Object decodeField(BytesSlices fieldBytes) {
		byte[] values = new byte[fieldBytes.getCount()];
		BytesSlice fieldBytesSlice = ((FixedBytesSliceArray) fieldBytes).getDataSlice(0, values.length);
		fieldBytesSlice.copyTo(values, 0, values.length);
		return values;
	}

}
