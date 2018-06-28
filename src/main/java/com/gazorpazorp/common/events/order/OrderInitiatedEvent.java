package com.gazorpazorp.common.events.order;

import java.util.Date;
import java.util.Map;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractEvent;

import lombok.Value;

@Value
public class OrderInitiatedEvent extends AuditableAbstractEvent {

	private String customerId;
	private Date orderDate;
	Map<String, Integer> items;
	
	public OrderInitiatedEvent(String aggregateId, String customerId, Date orderDate, Map<String, Integer> items, AuditEntry auditEntry) {
		super(aggregateId, auditEntry);
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.items = items;
	}

}
