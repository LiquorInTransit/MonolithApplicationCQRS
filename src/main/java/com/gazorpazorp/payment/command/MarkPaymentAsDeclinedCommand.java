package com.gazorpazorp.payment.command;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractCommand;

public class MarkPaymentAsDeclinedCommand extends AuditableAbstractCommand {

	public MarkPaymentAsDeclinedCommand(String targetId, AuditEntry auditEntry) {
		super(targetId, auditEntry);
	}

}
