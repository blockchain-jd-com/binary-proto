package com.jd.binaryproto.impl;

import com.jd.binaryproto.BinarySliceSpec;
import com.jd.binaryproto.FieldSpec;
import utils.io.BytesOutputBuffer;
import utils.io.BytesSlice;
import utils.io.BytesSlices;
import utils.io.NumberMask;

import java.lang.reflect.Method;

/**
 * 针对可转换为原生类型 byte[] 数组的字段类型的编码器；
 *
 * @author huanghaiquan
 */
public class DynamicByteArrayFieldEncoder extends DynamicArrayFieldEncoder {

    public static final byte[] EMPTY_VALUES = {};

    public DynamicByteArrayFieldEncoder(BinarySliceSpec sliceSpec, FieldSpec fieldSpec, Method reader,
                                        DynamicValueConverter valueConverter) {
        super(sliceSpec, fieldSpec, reader, valueConverter);
    }

    @Override
    protected int encodeArrayDynamic(Object values, BytesOutputBuffer buffer) {
        int size = 0;

        byte[] valuesArray = values == null ? EMPTY_VALUES : (byte[]) values;
        int count = valuesArray.length;
        byte[] countBytes = NumberMask.NORMAL.generateMask(count);
        buffer.write(countBytes);
        size += countBytes.length;

        for (int i = 0; i < count; i++) {
            size += valueConverter.encodeDynamicValue(valuesArray[i], buffer);
        }

        return size;
    }

    @Override
    public byte[] decodeField(BytesSlices fieldBytes) {
        int fieldCount = null != fieldBytes ? fieldBytes.getCount() : 0;
        byte[] values = new byte[fieldCount];
        BytesSlice itemSlice;
        for (int i = 0; i < fieldCount; i++) {
            itemSlice = fieldBytes.getDataSlice(i);
            if (itemSlice.getSize() == 0) {
                values[i] = 0;
            } else {
                values[i] = itemSlice.getByte();
            }
        }
        return values;
    }
}
