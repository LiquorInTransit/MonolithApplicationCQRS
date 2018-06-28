package com.gazorpazorp.common.events.cart;

import java.util.Map;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

import lombok.Value;

@Value
public class CartCheckedOutEvent extends AuditableAbstractEvent {

	String customerId;
	Map<String, Integer> items;
	
	public CartCheckedOutEvent(String aggregateId, String customerId, Map<String, Integer> items, AuditEntry auditEntry) {
		super(aggregateId, auditEntry);
		this.customerId = customerId;
		this.items = items;
	}

}
