package test.com.jd.binaryproto;

public class PrimitiveArrayEncodingTestData implements PrimitiveArrayEncodingData {

	private long[] values;

	@Override
	public long[] getValues() {
		return values;
	}

	public void setValues(long[] values) {
		this.values = values;
	}

}
