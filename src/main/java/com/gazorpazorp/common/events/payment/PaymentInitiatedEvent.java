package com.gazorpazorp.common.events.payment;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

import lombok.Value;

@Value
public class PaymentInitiatedEvent extends AuditableAbstractEvent {

	private String customerId;
	private Integer total;
	private Integer deliveryFee;
	private Integer serviceFee;
	
	public PaymentInitiatedEvent(String aggregateId, String customerId, Integer total, Integer deliveryFee, Integer serviceFee, AuditEntry auditEntry) {
		super(aggregateId, auditEntry);
		this.customerId = customerId;
		this.total = total;
		this.deliveryFee = deliveryFee;
		this.serviceFee = deliveryFee;
	}

}
