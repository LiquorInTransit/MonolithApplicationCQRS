package com.gazorpazorp.payment.aggregate;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.gazorpazorp.common.events.payment.PaymentInitiatedEvent;

@Saga
public class PaymentSaga {

	private boolean paymentId = false;
	private boolean paymentVerified = false;
	private boolean paymentPayed = false;
	
	@Autowired
	private transient CommandGateway commandGateway;
	
	@StartSaga
	@SagaEventHandler(associationProperty="aggregateId")
	public void on (PaymentInitiatedEvent event) {
		//ValidateCustomerPaymentIdCommand command = new ValidateCustomerPaymentIdCommand(event.getCustomerId(), event.getAuditEntry());
	}
}
