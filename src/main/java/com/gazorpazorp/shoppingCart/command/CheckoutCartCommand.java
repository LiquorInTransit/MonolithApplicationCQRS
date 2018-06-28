package com.gazorpazorp.shoppingCart.command;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractCommand;

public class CheckoutCartCommand extends AuditableAbstractCommand {

	public CheckoutCartCommand(String targetId, AuditEntry auditEntry) {
		super(targetId, auditEntry);
	}
}
