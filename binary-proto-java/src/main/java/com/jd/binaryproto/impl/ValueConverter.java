package com.jd.binaryproto.impl;

import java.util.function.Function;

public interface ValueConverter {

	Class<?> getValueType();

	/**
	 * 返回类型的默认初始值；
	 *
	 * @return
	 */
	Object getDefaultValue();

	/**
	 * 数组创建函数，用于decode
	 * @return
	 */
	default Function<Integer, ?> getArrayConstructor() {
		return null;
	}
}
