package com.jd.binaryproto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.jd.binaryproto.impl.DataContractContext;

import utils.provider.Provider;
import utils.provider.ProviderManager;

/**
 * 数据实体注册表；
 * 
 * @author huanghaiquan
 *
 */
public class DataContractRegistry {

	private static ProviderManager pm = new ProviderManager();

	private static final DataContractRegistry INSTANCE = new DataContractRegistry();

	private static final DataContractContext CONTEXT = new DataContractContext();

	static {
		autoRegister();
	}

	public static DataContractRegistry getInstance() {
		return INSTANCE;
	}

	private static void autoRegister() {
		// 从当前类型的类加载器加载服务提供者；
		pm.installAllProviders(DataContractAutoRegistrar.class, BinaryProtocol.class.getClassLoader());
		// 从线程上下文类加载器加载服务提供者；（多次加载避免由于类加载器的原因产生遗漏，ProviderManager 内部会过滤重复加载）；
		pm.installAllProviders(DataContractAutoRegistrar.class, Thread.currentThread().getContextClassLoader());

		Iterable<Provider<DataContractAutoRegistrar>> providers = pm.getAllProviders(DataContractAutoRegistrar.class);
		List<DataContractAutoRegistrar> autoRegistrars = new ArrayList<DataContractAutoRegistrar>();
		for (Provider<DataContractAutoRegistrar> provider : providers) {
			autoRegistrars.add(provider.getService());
		}

		// 排序；
		autoRegistrars.sort(new Comparator<DataContractAutoRegistrar>() {
			@Override
			public int compare(DataContractAutoRegistrar o1, DataContractAutoRegistrar o2) {
				return o1.order() - o2.order();
			}
		});

		for (DataContractAutoRegistrar registrar : autoRegistrars) {
			registrar.initContext(DataContractRegistry.getInstance());
		}
	}

	private DataContractRegistry() {
	}

	/**
	 * @param contractType
	 * @return
	 */
	public static DataContractEncoder register(Class<?> contractType) {
		return CONTEXT.register(contractType);
	}
	
	public static DataContractEncoder getEncoder(Class<?> contractType) {
		return CONTEXT.lookup(contractType);
	}
	
	public static DataTypeMapping getDataType(Class<?> contractType) {
		return CONTEXT.lookupDataType(contractType);
	}

	public static DataContractEncoder getEncoder(int contractCode, long version) {
		return CONTEXT.lookup(contractCode, version);
	}

	public static <T> void registerBytesConverter(Class<T> javaType, BytesConverter<T> converter) {
		CONTEXT.registerBytesConverter(javaType, converter);
	}

}
