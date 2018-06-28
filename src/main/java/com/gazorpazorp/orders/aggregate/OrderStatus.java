package com.gazorpazorp.orders.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderStatus {

	private Status status;
	private String message;
	
	public OrderStatus (Status status) {
		this.status = status;
	}
	
	
	
	
	public enum Status {
		CREATED,
		PAYED,
		COMPLETED,
		CANCELLED;
	}
}
