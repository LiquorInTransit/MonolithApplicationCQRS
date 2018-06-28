package com.gazorpazorp.orders.command;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractCommand;

import lombok.Value;

@Value
public class CreateOrderCommand extends AuditableAbstractCommand {

	private String customerId;
	private Date orderDate;
	Map<String, Integer> items;
	
	public CreateOrderCommand(String targetId, String customerId, Date orderDate, Map<String, Integer> items, AuditEntry auditEntry) {
		super(targetId, auditEntry);
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.items = items;
	}
}
