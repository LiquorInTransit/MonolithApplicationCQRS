package com.gazorpazorp.payment.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import com.gazorpazorp.common.events.payment.PaymentInitiatedEvent;
import com.gazorpazorp.payment.command.CreatePaymentCommand;

import lombok.NoArgsConstructor;
@Aggregate
@NoArgsConstructor
public class PaymentAggregate {
	
	@AggregateIdentifier
	private String id;
	private String customerId;
	private String customerStripeId;
	private Integer total;
	//Fee to driver
	private Integer deliveryFee;
	//Fee to LIT
	private Integer serviceFee;
	
	

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
	}

}
