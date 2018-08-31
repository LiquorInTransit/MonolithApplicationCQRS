package com.gazorpazorp.common.events.payment;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

public class PaymentVerificationSucceededEvent extends AuditableAbstractEvent {

	public PaymentVerificationSucceededEvent(String aggregateId, AuditEntry auditEntry) {
		super(aggregateId, auditEntry);
		
	}

}
