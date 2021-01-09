package com.jd.binaryproto;

public interface BytesConverter<T> {
	
	T instanceFrom(byte[] bytes);
	
	byte[] serializeTo(T object);

}
