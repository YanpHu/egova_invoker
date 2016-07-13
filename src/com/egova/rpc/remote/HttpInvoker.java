package com.egova.rpc.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.egova.rpc.exception.RemoteException;
import com.egova.rpc.support.RemoteInvocation;
import com.egova.rpc.support.RemoteInvocationResult;
import com.egova.rpc.util.SerializeUtils;

/**
 * 远程调用服务端调用invoker方法即可
 * @author Administrator
 *
 */
public abstract class HttpInvoker {

	public static void invoker(HttpServletRequest request, HttpServletResponse respone, InvokerProcess invokerProcess)
			throws IOException {
		RemoteInvocationResult result;
		try {
			InputStream is = request.getInputStream();
			ObjectInputStream ios = new ObjectInputStream(is);
			Object obj = ios.readObject();
			if (obj instanceof RemoteInvocation) {
				
				Object resultObject = invokerProcess.process((RemoteInvocation) obj);
				
				if (!(resultObject instanceof Serializable)) {
					throw new RemoteException("返回结果必须实现 Serializable接口");
				}
				result = new RemoteInvocationResult(resultObject);

			} else {
				throw new RemoteException("RemoteInvocation ObjectStream required ");
			}
		} catch (Exception e) {
			result = new RemoteInvocationResult(e);
		}
		ByteArrayOutputStream baos = SerializeUtils.getByteArrayOutputStream(result);
		baos.writeTo(respone.getOutputStream());

	}

}
