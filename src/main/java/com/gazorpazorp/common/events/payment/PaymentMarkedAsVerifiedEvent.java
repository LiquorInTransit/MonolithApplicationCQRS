package com.gazorpazorp.common.events.payment;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

public class PaymentMarkedAsVerifiedEvent extends AuditableAbstractEvent {

	public PaymentMarkedAsVerifiedEvent(String aggregateId, AuditEntry auditEntry) {
		super(aggregateId, auditEntry);
	}

}
