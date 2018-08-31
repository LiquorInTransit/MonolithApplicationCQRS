package com.gazorpazorp.common.events.payment;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

public class PaymentMarkedAsDeclinedEvent extends AuditableAbstractEvent {

	public PaymentMarkedAsDeclinedEvent(String aggregateId, AuditEntry auditEntry) {
		super(aggregateId, auditEntry);
	}

}
