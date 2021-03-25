package test.com.jd.binaryproto;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jd.binaryproto.DataContractRegistry;

import utils.Bytes;

public class KVSetOpTemplate implements KVSetOperation {
	static {
		DataContractRegistry.register(KVSetOperation.class);
	}

	private Bytes accountAddress;

	private Map<String, KVWriteEntry> kvset = new LinkedHashMap<>();

	public KVSetOpTemplate() {
	}

	public KVSetOpTemplate(Bytes accountAddress) {
		this.accountAddress = accountAddress;
	}

	public KVSetOpTemplate(Bytes accountAddress, Map<String, KVWriteEntry> kvset) {
		this.accountAddress = accountAddress;
		this.kvset = kvset;
	}

	@Override
	public Bytes getAccountAddress() {
		return accountAddress;
	}

	@Override
	public KVWriteEntry[] getWriteSet() {
		return kvset.values().toArray(new KVWriteEntry[kvset.size()]);
	}

	public void setWriteSet(Object[] kvEntries) {
		for (Object object : kvEntries) {
			KVWriteEntry kvEntry = (KVWriteEntry) object;
			set(kvEntry.getKey(), kvEntry.getValue(), kvEntry.getExpectedVersion());
		}
		return;
	}

	public void set(String key, BytesValue value, long expVersion) {
		if (kvset.containsKey(key)) {
			throw new IllegalArgumentException("Cann't set the same key repeatedly!");
		}
		KVData kvdata = new KVData(key, value, expVersion);
		kvset.put(key, kvdata);
	}

	public void set(KVData kvData) {
		if (kvset.containsKey(kvData.getKey())) {
			throw new IllegalArgumentException("Cann't set the same key repeatedly!");
		}
		kvset.put(kvData.getKey(), kvData);
	}

	
	private static class KVData implements KVWriteEntry {

		private String key;

		private BytesValue value;

		private long expectedVersion;

		public KVData(String key, BytesValue value, long expectedVersion) {
			this.key = key;
			this.value = value;
			this.expectedVersion = expectedVersion;
		}

		@Override
		public String getKey() {
			return key;
		}

		@Override
		public BytesValue getValue() {
			return value;
		}

		@Override
		public long getExpectedVersion() {
			return expectedVersion;
		}

	}
}
