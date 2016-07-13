package com.egova.rpc.exception;
/**
 * 远程调用异常
 * @author Administrator
 *
 */
public class RemoteException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4903507898724696682L;

	
	public RemoteException(Throwable e){
		this.initCause(e);
	}
	
	public RemoteException(String exception){
		super(exception);
	}
}

