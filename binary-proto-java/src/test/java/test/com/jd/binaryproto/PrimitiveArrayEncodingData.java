package test.com.jd.binaryproto;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.NumberEncoding;
import com.jd.binaryproto.PrimitiveType;

@DataContract(code = 0x12, name = "PrimitiveArray", description = "")
public interface PrimitiveArrayEncodingData {

	@DataField(order = 8, primitiveType = PrimitiveType.INT64, list = true, numberEncoding = NumberEncoding.LONG)
	long[] getValues();

}
