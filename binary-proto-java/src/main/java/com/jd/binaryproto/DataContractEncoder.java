package com.jd.binaryproto;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import utils.io.BytesInputStream;
import utils.io.BytesOutputBuffer;

/**
 * 二进制编码器；
 * 
 * @author huanghaiquan
 *
 */
public interface DataContractEncoder {

	/**
	 * 数据契约的格式标准；
	 * 
	 * @return
	 */
	DataSpecification getSpecification();

	/**
	 * 数据契约的接口类型；
	 * 
	 * @return
	 */
	Class<?> getContractType();
	
	/**
	 * 按照数据格式标准序列化输出指定的数据对象；
	 * 
	 * @param dataContract
	 *            数据对象；
	 * @param buffer
	 *            要写入的缓冲区；
	 * @return 返回写入的字节数；
	 */
	int encode(Object dataContract, BytesOutputBuffer buffer);
	
	default void encode(Object data, OutputStream out) {
		BytesOutputBuffer buffer = new BytesOutputBuffer();
		encode(data, buffer);
		buffer.writeTo(out);
	}
	
	default byte[] encode(Object data) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		encode(data, out);
		return out.toByteArray();
	}


	/**
	 * 按照数据格式标准将指定的二进制输入流反序列化生成数据对象；
	 * 
	 * @param bytesStream
	 * @return
	 */
	<T> T decode(BytesInputStream bytesStream);
	
	/**
	 * 按照数据格式标准将指定的二进制输入流反序列化生成数据对象；
	 * 
	 * @param bytesStream
	 * @return
	 */
	default <T> T decode(byte[] bytes) {
		return decode(new BytesInputStream(bytes, 0, bytes.length));
	}
	
	/**
	 * 按照数据格式标准将指定的二进制输入流反序列化生成数据对象；
	 * 
	 * @param bytesStream
	 * @return
	 */
	default <T> T decode(byte[] bytes, int offset, int length) {
		return decode(new BytesInputStream(bytes, offset, length));
	}
}
