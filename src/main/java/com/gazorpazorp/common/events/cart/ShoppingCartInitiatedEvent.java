package com.gazorpazorp.common.events.cart;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

import lombok.Getter;

@Getter
public class ShoppingCartInitiatedEvent extends AuditableAbstractEvent {
	
	String customerId;
	
	public ShoppingCartInitiatedEvent (String aggregateId, String customerId, AuditEntry auditEntry) {
		super(aggregateId, auditEntry);
		this.customerId = customerId;
	}
	
	
}
