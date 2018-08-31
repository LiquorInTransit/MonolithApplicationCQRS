package com.gazorpazorp.payment.command;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractCommand;

public class MarkPaymentAsVerifiedCommand extends AuditableAbstractCommand {

	public MarkPaymentAsVerifiedCommand(String targetId, AuditEntry auditEntry) {
		super(targetId, auditEntry);
	}

}
