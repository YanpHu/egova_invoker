package com.egova.rpc.proxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.egova.rpc.exception.RemoteException;
import com.egova.rpc.http.HttpInvokerClient;
import com.egova.rpc.support.HttpInvokerClientConfiguration;
import com.egova.rpc.support.RemoteInvocation;
import com.egova.rpc.support.RemoteInvocationResult;
import com.egova.rpc.util.SerializeUtils;

/**
 * 代理类，暂时只支持JDK的动态代理
 * @author Administrator
 *
 */
public class ProxyFactory {

	/**
	 * 默认的绑定的远程访问地址
	 */
	static String defaultBindRemoteUrl = "https://www.baidu.com";

	/**
	 * 当远程调用时是否抛出异常
	 */
	static boolean ifErrorThrow = false;

	public static void setBindRemoteUrl(String bindRemoteUrl) {
		ProxyFactory.defaultBindRemoteUrl = bindRemoteUrl;
	}

	public static void setIfErrorThrow(boolean ifErrorThrow) {
		ProxyFactory.ifErrorThrow = ifErrorThrow;
	}

	public static <T> T proxy(Class<T> clazz) {
		return proxy(clazz, defaultBindRemoteUrl);
	}

	@SuppressWarnings("unchecked")
	public static <T> T proxy(Class<T> clazz, String bindUrl) {

		if (!clazz.isInterface()) {
			throw new RuntimeException("参数必须是接口");
		}
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		return (T) Proxy.newProxyInstance(loader, new Class[] { clazz }, new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				RemoteInvocation remoteInvocation = new RemoteInvocation();
				remoteInvocation.setInterfaceClazz(clazz);
				remoteInvocation.setMethodName(method.getName());
				remoteInvocation.setArguments(args);
				remoteInvocation.setParameterTypes(method.getParameterTypes());
				RemoteInvocationResult result;
				try {
					result = executeRequest(bindUrl, remoteInvocation);
					return recreateRemoteInvocationResult(result);
				} catch (Throwable ex) {
					if (ifErrorThrow) {
						throw new RemoteException(ex);
					}
				}
				return null;
			}

		});
	}

	static RemoteInvocationResult executeRequest(String bindUrl, RemoteInvocation remoteInvocation)
			throws IOException, ClassNotFoundException {
		ByteArrayOutputStream baos = SerializeUtils.getByteArrayOutputStream(remoteInvocation);
		HttpInvokerClient client = new HttpInvokerClient();
		HttpInvokerClientConfiguration config = new HttpInvokerClientConfiguration() {

			@Override
			public String getServiceUrl() {
				return bindUrl;
			}
		};
		return client.doExecuteRequest(config, baos);
	}

	static Object recreateRemoteInvocationResult(RemoteInvocationResult result) throws Throwable {
		return result.recreate();
	}
}
