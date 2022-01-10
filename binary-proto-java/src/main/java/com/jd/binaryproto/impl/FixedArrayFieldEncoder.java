package com.jd.binaryproto.impl;

import com.jd.binaryproto.BinarySliceSpec;
import com.jd.binaryproto.FieldSpec;
import utils.io.*;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class FixedArrayFieldEncoder extends AbstractFieldEncoder {

    protected FixedValueConverter valueConverter;

    public FixedArrayFieldEncoder(BinarySliceSpec sliceSpec, FieldSpec fieldSpec, Method reader,
                                  FixedValueConverter valueConverter) {
        super(sliceSpec, fieldSpec, reader);
        this.valueConverter = valueConverter;
    }

    @Override
    public int encode(Object dataContract, BytesOutputBuffer buffer) {
        int size = 0;

        Object values = readValue(dataContract);
        size += encodeArray(values, buffer);

        return size;
    }

    /**
     * 对数组类型的值进行固定长度的编码；
     *
     * @param values
     * @param buffer
     * @return
     */
    protected int encodeArray(Object values, BytesOutputBuffer buffer) {
        int count = values == null ? 0 : Array.getLength(values);

        int counterSize = NumberMask.NORMAL.getMaskLength(count);
        int elementSize = sliceSpec.getLength();

        int size = counterSize + elementSize * count;
        byte[] outbuff = new byte[size];
        NumberMask.NORMAL.writeMask(count, outbuff, 0);

        for (int i = 0; i < count; i++) {
            valueConverter.encodeValue(Array.get(values, i), outbuff, counterSize + elementSize * i);
        }

        buffer.write(outbuff);
        return size;
    }

    @Override
    public BytesSlices decode(BytesInputStream bytesStream) {
        return FixedBytesSliceArray.resolve(bytesStream, sliceSpec.getLength());
    }

    @Override
    public Object decodeField(BytesSlices fieldBytes) {
        int fieldCount = null != fieldBytes ? fieldBytes.getCount() : 0;
        Object[] values;
        if (null != valueConverter.getArrayConstructor()) {
            values = (Object[]) valueConverter.getArrayConstructor().apply(fieldCount);
        } else {
            values = (Object[]) Array.newInstance(valueConverter.getValueType(), fieldCount);
        }
        BytesSlice itemSlice;
        for (int i = 0; i < values.length; i++) {
            itemSlice = fieldBytes.getDataSlice(i);
            if (itemSlice.getSize() == 0) {
                values[i] = valueConverter.getDefaultValue();
            } else {
                values[i] = valueConverter.decodeValue(itemSlice);
            }
        }
        return values;
    }

}
