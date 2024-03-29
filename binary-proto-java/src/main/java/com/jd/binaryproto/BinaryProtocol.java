package com.jd.binaryproto;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jd.binaryproto.impl.DataContractContext;
import com.jd.binaryproto.impl.DataContractProxy;
import com.jd.binaryproto.impl.HeaderEncoder;

import utils.io.BytesSlice;
import utils.io.BytesUtils;

public class BinaryProtocol {

	private static final Object DYNAMIC_CONTRACT_TYPE_MUTEX = new Object();
	private static Map<Class<?>, Class<?>> dynamicContractTypeMapping = new ConcurrentHashMap<>();

	/**
	 * 返回指定的数据契约类型的映射；
	 * 
	 * @param contractType
	 * @return
	 */
	public static DataTypeMapping getDataType(Class<?> contractType) {
		return DataContractRegistry.getDataType(contractType);
	}

	/**
	 * 返回指定的数据契约类型的映射；
	 * 
	 * @param contractType
	 * @return
	 */
	public static DataTypeMapping getDataType(int code, long version) {
		// TODO: Not implemented!;
		throw new IllegalStateException("Not implemented!");
	}

	/**
	 * 返回全部的数据契约类型的映射；
	 * 
	 * @return
	 */
	public static List<DataTypeMapping> getDataTypes() {
		// TODO: Not implemented!;
		throw new IllegalStateException("Not implemented!");
	}

	/**
	 * 指定类型是否声明为 DataContract;
	 * 
	 * @param contractType
	 * @return
	 */
	public static boolean isDataContractType(Class<?> contractType) {
		return DataContractContext.isDataContractType(contractType);
	}

	public static void encode(Object data, Class<?> contractType, OutputStream out) {
		DataContractEncoder encoder = DataContractRegistry.register(contractType);
		if (encoder == null) {
			throw new IllegalArgumentException("Contract Type not exist!--" + contractType.getName());
		}
		encoder.encode(data, out);
	}

	public static byte[] encode(Object data) {
		Class<?> dataType = data.getClass();
		Class<?> contractType = dynamicContractTypeMapping.get(dataType);
		if (contractType == null) {
			synchronized (DYNAMIC_CONTRACT_TYPE_MUTEX) {
				contractType = dynamicContractTypeMapping.get(dataType);
				if (contractType == null) {
					contractType = findDataContractType(dataType);
					if (contractType == null) {
						throw new DataContractException(
								"No data contract is declared in type[" + dataType.getName() + "]!");
					}
					dynamicContractTypeMapping.put(dataType, contractType);
				}
			}
		}
		return encode(data, contractType);
	}

	private static Class<?> findDataContractType(Class<?> dataType) {
		// TODO: 未检测声明为多个 DataContract 类型的情况；
		Class<?>[] interfaces = dataType.getInterfaces();
		for (Class<?> itf : interfaces) {
			if (isDataContractType(itf)) {
				return itf;
			}
		}
		Class<?> contractType = null;
		for (Class<?> itf : interfaces) {
			contractType = findDataContractType(itf);
			if (contractType != null) {
				return contractType;
			}
		}
		return contractType;
	}

	public static byte[] encode(Object data, Class<?> contractType) {
		DataContractEncoder encoder = DataContractRegistry.register(contractType);
		if (encoder == null) {
			throw new IllegalArgumentException("Contract Type not exist!--" + contractType.getName());
		}
		return encoder.encode(data);
	}

	public static <T> T decode(InputStream in) {
		byte[] bytes = BytesUtils.copyToBytes(in);
		return decode(bytes);
	}

	public static <T> T decode(byte[] dataSegment) {
		BytesSlice bytes = new BytesSlice(dataSegment, 0, dataSegment.length);
		int code = HeaderEncoder.resolveCode(bytes);
		long version = HeaderEncoder.resolveVersion(bytes);

		DataContractEncoder encoder = DataContractRegistry.getEncoder(code, version);
		if (encoder == null) {
			throw new DataContractException(
					String.format("No data contract was registered with code[%s] and version[%s]!", code, version));
		}
		return encoder.decode(dataSegment);
	}

	public static <T> T decode(byte[] dataSegment, Class<T> contractType) {
		return decode(dataSegment, contractType, true);
	}

	public static <T> T decode(byte[] dataSegment, Class<T> contractType, boolean autoRegister) {
		BytesSlice bytes = new BytesSlice(dataSegment, 0, dataSegment.length);
		int code = HeaderEncoder.resolveCode(bytes);
		long version = HeaderEncoder.resolveVersion(bytes);

		DataContractEncoder codec = DataContractRegistry.getEncoder(code, version);
		if (codec == null) {
			codec = DataContractRegistry.getEncoder(contractType);
		} else {
			if (!contractType.isAssignableFrom(codec.getContractType())) {
				throw new DataContractException("The specified contract type[" + contractType.getName()
						+ "] is incompatible the origin contract type[" + codec.getContractType().getName()
						+ "] of the data bytes!");
			}
		}

		if (codec == null) {
			if (autoRegister) {
				codec = DataContractRegistry.register(contractType);
			} else {
				throw new DataContractException("Contract type is not registered! --" + contractType.toString());
			}
		}
		return codec.decode(dataSegment);
	}

	public static boolean isProxy(Object obj) {
		return obj instanceof DataContractProxy;
	}

	/**
	 * 不推荐使用；替换为 {@link #decode(byte[], Class)};
	 * 
	 * @param <T>
	 * @param dataSegment
	 * @param contractType
	 * @return
	 */
	@Deprecated
	public static <T> T decodeAs(byte[] dataSegment, Class<T> contractType) {
		return decode(dataSegment, contractType, true);
	}

	/**
	 * 不推荐使用；替换为 {@link #decode(byte[], Class, boolean)};
	 * 
	 * @param <T>
	 * @param dataSegment
	 * @param contractType
	 * @param autoRegister
	 * @return
	 */
	@Deprecated
	public static <T> T decodeAs(byte[] dataSegment, Class<T> contractType, boolean autoRegister) {
		return decode(dataSegment, contractType, autoRegister);
	}

}
