package com.gazorpazorp.common.events.cart;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

import lombok.Value;

@Value
public class ItemAddedToCartEvent extends AuditableAbstractEvent {

	String productId;
	int qty;
	
	public ItemAddedToCartEvent(String aggregateIdentifier, String productId, int qty, AuditEntry auditEntry) {
		super(aggregateIdentifier, auditEntry);
		this.productId = productId;
		this.qty = qty;
	}

}
