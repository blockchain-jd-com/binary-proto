package test.com.jd.binaryproto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jd.binaryproto.impl.DataContractContext;

import test.com.jd.binaryproto.KVSetOperation.KVWriteEntry;
import utils.Bytes;
import utils.io.BytesUtils;
import utils.security.RandomUtils;

/**
 * 测试同一个编号的数据契约的不同版本的兼容反序列化；
 * 
 * @author Hike
 *
 */
public class DataContractCompatibilityTest {

	/**
	 * 测试对 {@link KVWriteEntry#getValue()} 返回类型由 {@link BytesValue} 更改为 byte[]
	 * 之后，数据反序列化的兼容性；
	 */
	@Test
	public void testCompatibility() {
		Bytes address = new Bytes(RandomUtils.generateRandomBytes(20));

		// 在序列化上下文1 中注册旧的类型，并进行序列化；
		DataContractContext context1 = new DataContractContext();
		KVSetOpTemplate op1 = new KVSetOpTemplate(address);
		String key = "dataKey001";
		BytesValue value = new TypedValue(DataType.TEXT, BytesUtils.toBytes("dataValue001"));
		long expVersion = -1;
		op1.set(key, value, expVersion);

		byte[] opBytes = context1.register(KVSetOperation.class).encode(op1);

		// 在序列化上下文2 中注册新类型并进行反序列化；
		DataContractContext context2 = new DataContractContext();
		KVSetOperation2 op2 = context2.register(KVSetOperation2.class).decode(opBytes);

		// 验证结果的一致性；
		assertEquals(op1.getAccountAddress(), op2.getAccountAddress());
		assertEquals(1, op1.getWriteSet().length);
		assertEquals(op1.getWriteSet().length, op2.getWriteSet().length);
		assertEquals(op1.getWriteSet()[0].getKey(), op2.getWriteSet()[0].getKey());
		assertEquals(op1.getWriteSet()[0].getExpectedVersion(), op2.getWriteSet()[0].getExpectedVersion());
		byte[] valueBytes = context1.lookup(BytesValue.class).encode(op1.getWriteSet()[0].getValue());
		assertArrayEquals(valueBytes, op2.getWriteSet()[0].getValue());
	}

	private static class TypedValue implements BytesValue {

		public static final BytesValue NIL = new TypedValue();

		private DataType type;
		private Bytes value;

		private TypedValue(DataType type, byte[] bytes) {
			if (null != bytes && bytes.length > 0) {
				this.type = type;
				this.value = new Bytes(bytes);
			} else {
				this.type = DataType.NIL;
			}
		}

		private TypedValue(DataType type, Bytes bytes) {
			if (null != bytes && bytes.size() > 0) {
				this.type = type;
				this.value = bytes;
			} else {
				this.type = DataType.NIL;
			}
		}

		private TypedValue(BytesValue bytesValue) {
			if (bytesValue == null || DataType.NIL == bytesValue.getType() || bytesValue.getBytes().size() == 0) {
				this.type = DataType.NIL;
			} else {
				this.type = bytesValue.getType();
				this.value = bytesValue.getBytes();
			}
		}

		private TypedValue() {
			this.type = DataType.NIL;
		}

		@Override
		public DataType getType() {
			return this.type;
		}

		@Override
		public Bytes getBytes() {
			return this.value;
		}
	}
}
