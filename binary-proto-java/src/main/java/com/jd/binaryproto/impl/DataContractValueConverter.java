package com.jd.binaryproto.impl;

import com.jd.binaryproto.DataContractEncoder;

import utils.io.BytesOutputBuffer;
import utils.io.BytesSlice;

public class DataContractValueConverter extends AbstractDynamicValueConverter {
	
	private DataContractEncoder contractEncoder;
	
	public DataContractValueConverter(DataContractEncoder contractEncoder) {
		super(contractEncoder.getContractType());
		this.contractEncoder =contractEncoder;
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
	
}
