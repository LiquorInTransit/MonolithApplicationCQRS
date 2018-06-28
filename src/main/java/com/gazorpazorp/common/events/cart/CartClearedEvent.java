package com.gazorpazorp.common.events.cart;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

import lombok.Getter;

@Getter
public class CartClearedEvent extends AuditableAbstractEvent {
	
	public CartClearedEvent (String aggregateIdentifier, AuditEntry auditEntry) {
		super(aggregateIdentifier, auditEntry);
	}
	
	
}
