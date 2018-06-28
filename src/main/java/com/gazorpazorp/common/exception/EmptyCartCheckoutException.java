package com.gazorpazorp.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "You cannot checkout with an empty cart")
public class EmptyCartCheckoutException extends Exception {

	public EmptyCartCheckoutException() {
		// TODO Auto-generated constructor stub
	}

	public EmptyCartCheckoutException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EmptyCartCheckoutException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public EmptyCartCheckoutException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EmptyCartCheckoutException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
