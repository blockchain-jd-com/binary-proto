package com.jd.binaryproto.impl;

import com.jd.binaryproto.DataContractEncoder;
import utils.io.BytesOutputBuffer;
import utils.io.BytesSlice;

import java.util.function.Function;

public class DataContractValueConverter extends AbstractDynamicValueConverter {

    private DataContractEncoder contractEncoder;
    private Function<Integer, ?> arrayConstructor;

    public DataContractValueConverter(DataContractEncoder contractEncoder, Function<Integer, ?> arrayConstructor) {
        super(contractEncoder.getContractType());
        this.contractEncoder = contractEncoder;
        this.arrayConstructor = arrayConstructor;
    }

    @Override
    public int encodeDynamicValue(Object value, BytesOutputBuffer buffer) {
        BytesOutputBuffer contractBuffer = new BytesOutputBuffer();
        int size = contractEncoder.encode(value, contractBuffer);

        size += writeSize(size, buffer);

        buffer.write(contractBuffer);
        return size;
    }

    @Override
    public Object decodeValue(BytesSlice dataSlice) {
        return contractEncoder.decode(dataSlice.getInputStream());
    }

    @Override
    public Function<Integer, ?> getArrayConstructor() {
        return arrayConstructor;
    }
}
