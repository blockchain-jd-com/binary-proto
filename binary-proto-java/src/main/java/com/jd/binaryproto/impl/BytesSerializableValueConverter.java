package com.jd.binaryproto.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.jd.binaryproto.DataContractException;

import utils.io.BytesOutputBuffer;
import utils.io.BytesSerializable;
import utils.io.BytesSlice;
import utils.io.BytesUtils;

public class BytesSerializableValueConverter extends AbstractDynamicValueConverter {

	private Constructor<?> constructor;

	public BytesSerializableValueConverter(Class<?> valueType) {
		super(valueType);
		if (!BytesSerializable.class.isAssignableFrom(valueType)) {
			throw new IllegalArgumentException("The specified type cann't be assigned as BytesSerializable!");
		}
		// 检查是否存在以 byte[] 为参数的构造器；
		try {
			constructor = valueType.getConstructor(byte[].class);
			constructor.setAccessible(true);
		} catch (NoSuchMethodException e) {
			throw new DataContractException("No constructor with byte's array argument! --" + e.getMessage(), e);
		} catch (SecurityException e) {
			throw new DataContractException(e.getMessage(), e);
		}
	}

	@Override
	public Object decodeValue(BytesSlice dataSlice) {
		if (valueType == BytesSlice.class) {
			return dataSlice;
		}
		if (dataSlice.getSize() == 0) {
			return null;
		}
		byte[] bytes = dataSlice.getBytesCopy();
		try {
			return constructor.newInstance(bytes);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new DataContractException(e.getMessage(), e);
		}
	}

	@Override
	public int encodeDynamicValue(Object value, BytesOutputBuffer buffer) {
		byte[] bytes = value == null ? BytesUtils.EMPTY_BYTES : ((BytesSerializable) value).toBytes();
		int size = bytes.length;
		size += writeSize(size, buffer);
		buffer.write(bytes);
		return size;
	}

}
