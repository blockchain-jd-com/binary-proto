package test.com.jd.binaryproto;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.PrimitiveType;

import utils.Bytes;

@DataContract(code= KVSetOperation.TX_OP_DATA_ACC_SET)
public interface KVSetOperation2 extends Operation {
	
	@DataField(order=2, primitiveType=PrimitiveType.BYTES)
	Bytes getAccountAddress();
	
	@DataField(order=3, list=true, refContract=true)
	KVWriteEntry[] getWriteSet();
	
	
	@DataContract(code=KVSetOperation.TX_OP_DATA_ACC_SET_KV)
	public static interface KVWriteEntry{

		@DataField(order=1, primitiveType=PrimitiveType.TEXT)
		String getKey();

		@DataField(order=2, primitiveType = PrimitiveType.BYTES)
		byte[] getValue();

		@DataField(order=3, primitiveType=PrimitiveType.INT64)
		long getExpectedVersion();
	}

}
