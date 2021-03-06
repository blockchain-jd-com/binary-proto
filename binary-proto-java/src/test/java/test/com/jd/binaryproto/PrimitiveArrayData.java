package test.com.jd.binaryproto;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.PrimitiveType;

@DataContract(code = 0x11, name = "PrimitiveArray", description = "")
public interface PrimitiveArrayData {
	
	@DataField(order = 8, primitiveType = PrimitiveType.INT64, list = true)
	long[] getValues();
	
}
