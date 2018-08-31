package com.gazorpazorp.payment.aggregate;

import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.gazorpazorp.common.events.payment.PaymentInitiatedEvent;
import com.gazorpazorp.common.events.payment.PaymentVerificationDeclinedEvent;
import com.gazorpazorp.common.events.payment.PaymentVerificationSucceededEvent;
import com.gazorpazorp.customer.service.CustomerService;
import com.gazorpazorp.payment.command.MarkPaymentAsDeclinedCommand;
import com.gazorpazorp.payment.command.MarkPaymentAsVerifiedCommand;
import com.gazorpazorp.payment.command.VerifyPaymentCommand;

@Saga
public class PaymentSaga {

	private boolean paymentVerified = false;
	private boolean paymentPayed = false;
	
	@Autowired
	private transient CommandGateway commandGateway;
	
	@StartSaga
	@SagaEventHandler(associationProperty="aggregateId")
	public void on (PaymentInitiatedEvent event, CustomerService customerService) {
		String customerStripeId = customerService.getCustomer(event.getCustomerId()).getStripeId();
		VerifyPaymentCommand verifyCustomerPaymentCommand = new VerifyPaymentCommand(event.getAggregateId(), customerStripeId, event.getTotal(), event.getDeliveryFee(), event.getServiceFee(), event.getAuditEntry());
		commandGateway.send(verifyCustomerPaymentCommand, LoggingCallback.INSTANCE);
	}
	
	@SagaEventHandler(associationProperty="aggregateId")
	public void on (PaymentVerificationSucceededEvent event) {
		MarkPaymentAsVerifiedCommand verifiedCommand = new MarkPaymentAsVerifiedCommand(event.getAggregateId(), event.getAuditEntry());
		commandGateway.send(verifiedCommand, LoggingCallback.INSTANCE);
	}
	
	@EndSaga
	@SagaEventHandler(associationProperty="aggregateId")
	public void on (PaymentVerificationDeclinedEvent event) {
		MarkPaymentAsDeclinedCommand declinedCommand = new MarkPaymentAsDeclinedCommand(event.getAggregateId(), event.getAuditEntry());
		commandGateway.send(declinedCommand, LoggingCallback.INSTANCE);
	}
}
