package com.funshion.dobo.base.exception;

/**
 * dobo admin 异常
 * @author lirui
 *
 */
public class DoboAdminException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DoboAdminException(String message){
		super(message);
	}
	
	public DoboAdminException(String message, Throwable cause) {
		super(message, cause);
	}
}
