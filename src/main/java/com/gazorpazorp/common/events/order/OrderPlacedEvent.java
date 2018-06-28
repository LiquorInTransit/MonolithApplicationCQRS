package com.gazorpazorp.common.events.order;

import java.util.Map;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

import lombok.Value;

@Value
public class OrderPlacedEvent extends AuditableAbstractEvent {

	String customerId;
	Map<String, Integer> items;
	
	public OrderPlacedEvent(String aggregateId, String customerId, Map<String, Integer> items, AuditEntry auditEntry) {
		super(aggregateId, auditEntry);
		this.customerId = customerId;
		this.items = items;
	}

}
