
package com.egova.rpc.support;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import com.egova.rpc.support.util.RemoteInvocationUtils;

/**
 * 远程调用结果包装类
 * @author Administrator
 *
 */
public class RemoteInvocationResult implements Serializable {

	private static final long serialVersionUID = 2138555143707773549L;

	private Object value;

	private Throwable exception;

	public RemoteInvocationResult(Object value) {
		this.value = value;
	}

	public RemoteInvocationResult(Throwable exception) {
		this.exception = exception;
	}

	public RemoteInvocationResult() {

	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return this.value;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public Throwable getException() {
		return this.exception;
	}

	public boolean hasException() {
		return (this.exception != null);
	}

	public boolean hasInvocationTargetException() {
		return (this.exception instanceof InvocationTargetException);
	}

	public Object recreate() throws Throwable {
		if (this.exception != null) {
			Throwable exToThrow = this.exception;
			if (this.exception instanceof InvocationTargetException) {
				exToThrow = ((InvocationTargetException) this.exception).getTargetException();
			}
			RemoteInvocationUtils.fillInClientStackTraceIfPossible(exToThrow);
			throw exToThrow;
		} else {
			return this.value;
		}
	}

}
