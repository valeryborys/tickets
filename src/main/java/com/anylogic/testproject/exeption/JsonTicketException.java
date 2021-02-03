package com.anylogic.testproject.exeption;

public class JsonTicketException extends Exception {

	private static final long serialVersionUID = 8540927565024795968L;

	public JsonTicketException() {
		super();
	}

	public JsonTicketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

	public JsonTicketException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonTicketException(String message) {
		super(message);
	}

	public JsonTicketException(Throwable cause) {
		super(cause);
	}


}
