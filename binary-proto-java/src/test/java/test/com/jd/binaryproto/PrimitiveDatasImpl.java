package test.com.jd.binaryproto;

import utils.Bytes;
import utils.net.NetworkAddress;

/**
 * Created by zhangshuang3 on 2018/7/11.
 */
public class PrimitiveDatasImpl implements PrimitiveDatas {

	private boolean enable;

	private Byte boy;
	private short age;
	private int number;
	private int id;
	
	private long[] sizes;
	
	private String name;

	private long value;

	private char flag;

	private byte[] image;

	private Bytes config;
	
	private Bytes setting;

	private NetworkAddress networkAddress;

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public short getAge() {
		return age;
	}

	public void setAge(short age) {
		this.age = age;
	}

	@Override
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	@Override
	public long[] getSizes() {
		return sizes;
	}
	
	public void setSizes(long[] sizes) {
		this.sizes = sizes;
	}
	
	@Override
	public byte isBoy() {
		return this.boy;
	}

	public void setBoy(byte boy) {
		this.boy = boy;
	}

	@Override
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	public long getValue() {
		return value;
	}

	@Override
	public char getFlag() {
		return flag;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	@Override
	public byte[] getImage() {
		return image;
	}

	@Override
	public Bytes getConfig() {
		return config;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setConfig(Bytes config) {
		this.config = config;
	}

	public void setSetting(Bytes setting) {
		this.setting = setting;
	}

	@Override
	public Bytes getSetting() {
		return setting;
	}

	@Override
	public NetworkAddress getNetworkAddr() {
		return networkAddress;
	}

	public void setNetworkAddress(NetworkAddress networkAddress) {
		this.networkAddress = networkAddress;
	}
}
