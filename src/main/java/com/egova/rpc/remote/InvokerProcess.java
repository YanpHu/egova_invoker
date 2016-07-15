package com.egova.rpc.remote;

import com.egova.rpc.support.RemoteInvocation;

/**
 * 服务端处理方法
 * @author Administrator
 *
 */
public interface InvokerProcess {
	
	public Object process(RemoteInvocation invocation);
	
}
