package com.jd.binaryproto.impl;

import com.jd.binaryproto.BinarySliceSpec;
import com.jd.binaryproto.FieldSpec;
import utils.io.BytesOutputBuffer;
import utils.io.BytesSlice;
import utils.io.BytesSlices;
import utils.io.NumberMask;

import java.lang.reflect.Method;

public class FixedLongArrayFieldEncoder extends FixedArrayFieldEncoder {

    private static final long[] EMPTY_ARRAY = {};

    public FixedLongArrayFieldEncoder(BinarySliceSpec sliceSpec, FieldSpec fieldSpec, Method reader,
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
        long[] valueArray = values == null ? EMPTY_ARRAY : (long[]) values;

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
    public long[] decodeField(BytesSlices fieldBytes) {
        long[] values = new long[fieldBytes.getCount()];
        BytesSlice itemSlice;
        for (int i = 0; i < values.length; i++) {
            itemSlice = fieldBytes.getDataSlice(i);
            if (itemSlice.getSize() == 0) {
                values[i] = (long) valueConverter.getDefaultValue();
            } else {
                values[i] = (long) valueConverter.decodeValue(itemSlice);
            }
        }
        return values;
    }

}
