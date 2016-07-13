package com.egova.rpc.support;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.egova.rpc.util.ClassUtils;

/**
 * 远程调用封装类
 * @author Administrator
 *
 */
public class RemoteInvocation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6095716781880547339L;

	private Class<?> interfaceClazz;

	private String methodName;

	private Class<?>[] parameterTypes;

	private Object[] arguments;

	public RemoteInvocation(Class<?> interfaceClazz, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		this.interfaceClazz = interfaceClazz;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.arguments = arguments;
	}

	public RemoteInvocation() {
	}
	
	public Class<?> getInterfaceClazz() {
		return interfaceClazz;
	}

	public void setInterfaceClazz(Class<?> interfaceClazz) {
		this.interfaceClazz = interfaceClazz;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Class<?>[] getParameterTypes() {
		return this.parameterTypes;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public Object[] getArguments() {
		return this.arguments;
	}

	public Object invoke(Object targetObject)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		Method method = targetObject.getClass().getMethod(this.methodName, this.parameterTypes);
		return method.invoke(targetObject, this.arguments);
	}

	@Override
	public String toString() {
		return "RemoteInvocation: method name '" + this.methodName + "'; parameter types "
				+ ClassUtils.classNamesToString(this.parameterTypes);
	}
}
