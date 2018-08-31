package com.gazorpazorp.payment.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import com.gazorpazorp.common.events.payment.PaymentInitiatedEvent;
import com.gazorpazorp.common.events.payment.PaymentMarkedAsDeclinedEvent;
import com.gazorpazorp.common.events.payment.PaymentMarkedAsVerifiedEvent;
import com.gazorpazorp.common.events.payment.PaymentVerificationSucceededEvent;
import com.gazorpazorp.payment.command.CreatePaymentCommand;
import com.gazorpazorp.payment.command.MarkPaymentAsDeclinedCommand;
import com.gazorpazorp.payment.command.MarkPaymentAsVerifiedCommand;
import com.gazorpazorp.payment.command.VerifyPaymentCommand;
import com.gazorpazorp.payment.service.PaymentService;

import lombok.NoArgsConstructor;
@Aggregate
@NoArgsConstructor
public class PaymentAggregate {
	
	@AggregateIdentifier
	private String id;
	private String customerId;
	private Integer total;
	//Fee to driver
	private Integer deliveryFee;
	//Fee to LIT
	private Integer serviceFee;
	private PaymentStatus status;
	
	public enum PaymentStatus {
		STARTED,
		VERIFIED,
		PAYED,
		DECLINED
	}

	@CommandHandler
	public PaymentAggregate(CreatePaymentCommand command) {
		apply (new PaymentInitiatedEvent(command.getTargetId(), command.getCustomerId(), command.getTotal(), command.getDeliveryFee(), command.getServiceFee(), command.getAuditEntry()));
	}
	
	@EventSourcingHandler
	public void on (PaymentInitiatedEvent event) {
		this.id = event.getAggregateId();
		this.customerId = event.getCustomerId();
		this.total = event.getTotal();
		this.deliveryFee = event.getDeliveryFee();
		this.serviceFee = event.getServiceFee();
		this.status = PaymentStatus.STARTED;
	}

	@CommandHandler
	public void verifyPayment (VerifyPaymentCommand command, PaymentService paymentService) {
		try {
			paymentService.verifyPayment(command.getCustomerStripeId(), command.getTotal());
			apply (new PaymentVerificationSucceededEvent(command.getTargetId(), command.getAuditEntry()));
		} catch (Exception e) {
			apply (new MarkPaymentAsDeclinedCommand(command.getTargetId(), command.getAuditEntry()));
		}
	}
	
	@CommandHandler
	public void markPaymentAsVerified (MarkPaymentAsVerifiedCommand command) {
		apply (new PaymentMarkedAsVerifiedEvent(command.getTargetId(), command.getAuditEntry()));
	}
	
	@EventSourcingHandler
	public void on (PaymentMarkedAsVerifiedEvent event) {
		if (this.status == PaymentStatus.STARTED)
			this.status = PaymentStatus.VERIFIED;
	}
	
	@CommandHandler
	public void markPaymentAsDeclined (MarkPaymentAsDeclinedCommand command) {
		apply (new PaymentMarkedAsDeclinedEvent(command.getTargetId(), command.getAuditEntry()));
	}
}
