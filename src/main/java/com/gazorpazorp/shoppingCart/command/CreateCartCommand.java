package com.gazorpazorp.shoppingCart.command;

import java.util.UUID;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractCommand;

import lombok.Value;

@Value
public class CreateCartCommand extends AuditableAbstractCommand {

	private String customerId;
	
	public CreateCartCommand(String customerId, AuditEntry auditEntry) {
		super(UUID.randomUUID().toString(), auditEntry);
		this.customerId = customerId;
	}
}
