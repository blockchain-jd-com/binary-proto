package com.jd.binaryproto.impl;

import java.lang.reflect.Method;

import com.jd.binaryproto.FieldSpec;

import utils.io.BytesSlices;

public interface FieldEncoder extends SliceEncoder {

	Method getReader();

	FieldSpec getFieldSpecification();

	Object decodeField(BytesSlices fieldBytes);

}
