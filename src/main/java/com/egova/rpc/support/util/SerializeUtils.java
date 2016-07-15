package com.egova.rpc.support.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Java序列化工具类
 * @author Administrator
 *
 */
public class SerializeUtils {
	
	private static final int BYTE_SIZE = 1024;
	
	public static ByteArrayOutputStream getByteArrayOutputStream(Serializable serializable) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(BYTE_SIZE);
		writeRemoteInvocation(serializable, baos);
		return baos;
	}

	static void writeRemoteInvocation(Serializable serializable, OutputStream os) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(os);
		try {
			oos.writeObject(serializable);
		} finally {
			oos.close();
		}
	}
}
