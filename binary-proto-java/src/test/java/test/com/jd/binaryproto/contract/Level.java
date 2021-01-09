package test.com.jd.binaryproto.contract;

import com.jd.binaryproto.EnumContract;
import com.jd.binaryproto.EnumField;
import com.jd.binaryproto.PrimitiveType;

@EnumContract(code=0x0100)
public enum Level {

	V1((byte) 1),

	V2((byte) 2);

	@EnumField(type=PrimitiveType.INT8)
	public final byte CODE;
	public byte getCode() {
		return CODE;
	}
	private Level(byte code) {
		this.CODE = code;
	}

}


