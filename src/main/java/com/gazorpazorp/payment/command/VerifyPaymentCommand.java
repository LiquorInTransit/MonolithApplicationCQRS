package com.gazorpazorp.payment.command;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractCommand;

import lombok.Value;

@Value
public class VerifyPaymentCommand extends AuditableAbstractCommand{

	private String customerStripeId;
	private Integer total;
	private Integer deliveryFee;
	private Integer serviceFee;
	
	public VerifyPaymentCommand (String targetId, String customerStripeId, Integer total, Integer deliveryFee, Integer serviceFee, AuditEntry auditEntry) {
		super(targetId, auditEntry);
		this.customerStripeId = customerStripeId;
		this.total = total;
		this.deliveryFee = deliveryFee;
		this.serviceFee = serviceFee;
	}

}
