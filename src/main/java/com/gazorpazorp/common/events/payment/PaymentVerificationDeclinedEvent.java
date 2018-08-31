package com.gazorpazorp.common.events.payment;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

public class PaymentVerificationDeclinedEvent extends AuditableAbstractEvent {

	public PaymentVerificationDeclinedEvent(String aggregateId, AuditEntry auditEntry) {
		super(aggregateId, auditEntry);
	}

}
