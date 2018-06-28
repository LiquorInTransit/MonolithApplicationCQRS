package com.gazorpazorp.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "You already have an active order")
public class ActiveOrderException extends Exception {

	public ActiveOrderException() {
		// TODO Auto-generated constructor stub
	}

}
