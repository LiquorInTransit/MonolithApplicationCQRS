package com.gazorpazorp.payment.command;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractCommand;

import lombok.Value;

@Value
public class CreatePaymentCommand extends AuditableAbstractCommand{

	private String customerId;
	private Integer total;
	private Integer deliveryFee;
	private Integer serviceFee;
	
	public CreatePaymentCommand (String targetId, String customerId, Integer total, Integer deliveryFee, Integer serviceFee, AuditEntry auditEntry) {
		super(targetId, auditEntry);
		this.customerId = customerId;
		this.total = total;
		this.deliveryFee = deliveryFee;
		this.serviceFee = serviceFee;
	}
}
