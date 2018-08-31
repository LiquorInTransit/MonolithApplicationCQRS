package com.gazorpazorp.orders.aggregate;

import static org.axonframework.eventhandling.saga.SagaLifecycle.associateWith;
import static org.axonframework.eventhandling.saga.SagaLifecycle.end;

import java.util.Date;
import java.util.UUID;

import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.gazorpazorp.common.events.order.OrderInitiatedEvent;
import com.gazorpazorp.common.events.order.OrderPlacedEvent;
import com.gazorpazorp.common.exception.ActiveOrderException;
import com.gazorpazorp.orders.command.CreateOrderCommand;
import com.gazorpazorp.orders.utils.OrderFeeCharges;
import com.gazorpazorp.payment.command.CreatePaymentCommand;

@Saga
public class OrderCreationSaga {

	
	private boolean orderCancelled = false;
	private boolean deliveryCancelled = false;
	private String orderId;
	private String shoppingCartId;
	
	@Autowired
	private transient CommandGateway commandGateway;
	
	@StartSaga
	@SagaEventHandler(associationProperty="aggregateId")
	public void on (OrderPlacedEvent event) throws ActiveOrderException {
		String orderId = UUID.randomUUID().toString();
		this.shoppingCartId = event.getAggregateId();
		this.orderId = orderId;
		associateWith("orderId", orderId);
		associateWith("shoppingCartId", event.getAggregateId());
		commandGateway.send(new CreateOrderCommand(orderId, event.getCustomerId(), new Date(), event.getItems(), event.getAuditEntry()), LoggingCallback.INSTANCE);
	}
	
//	@StartSaga
	@SagaEventHandler(associationProperty="aggregateId", keyName="orderId")
	public void on (OrderInitiatedEvent event) throws Exception {
		String paymentId = UUID.randomUUID().toString();
		String deliveryId = UUID.randomUUID().toString();
		associateWith("paymentId", paymentId);
		associateWith("deliveryId", deliveryId);
		
		
		//TODO: swap the map<STRING, INTEGER> to actual line items with prices and stuff in them.
		Integer total = event.getItems().entrySet().stream().mapToInt(e -> e.getValue()*e.getKey().length()).sum();
		CreatePaymentCommand createPaymentCommand = new CreatePaymentCommand(paymentId, event.getCustomerId(), total, OrderFeeCharges.DELIVERY_FEE, OrderFeeCharges.SERVICE_FEE, event.getAuditEntry());
//		CreateDeliveryCommand createDeliveryCommand = new CreateDeliveryCommand(event.getAggregateId());
		commandGateway.send(createPaymentCommand, LoggingCallback.INSTANCE);
//		commandGateway.send(createDeliveryCommand, LoggingCallback.INSTANCE);
	}
	
//	@SagaEventHandler (associationProperty="aggregateId", keyName="paymentId")
//	public void on (PaymentVerificationSucceededEvent event) {
//		MarkOrderAsActiveCommand activeCommand = new MarkOrderAsActiveCommand();
//		commandGateway.send(activeCommand, LoggingCallback.INSTANCE);
//	}
//	
//	@SagaEventHandler(associationProperty="aggregateId", keyName="paymentId")
//	public void on (PaymentVerificationDeclinedEvent event) {
//		MarkOrderAsCancelledCommand cancelledCommand = new MarkOrderAsCancelledCommand();
//		commandGateway.send(cancelledCommand, LoggingCallback.INSTANCE);
//	}
//	
//	@SagaEventHandler(associationProperty="aggregateId", keyName="orderId")
//	public void on (OrderCancelledEvent event) {
//		orderCancelled = true;
//		if (!deliveryCancelled) {
//			MarkDeliveryAsCancelledCommand deliveryCancelledCommand = new MarkDeliveryAsCancelledCommand();
//			commandGateway.send(deliveryCancelledCommand, LoggingCallback.INSTANCE);
//		} else {
//			OpenShoppingCartForCheckoutCommand openShoppingCartCommand = new OpenShoppingCartForCheckoutCommand();
//			commandGateway.send(openShoppingCartCommand, LoggingCallback.INSTANCE);
//			end();
//		}
//	}
//	
//	@SagaEventHandler(associationProperty="aggregateId", keyName="deliveryId")
//	public void on (DeliveryCancelledEvent event) {
//		deliveryCancelled = true;
//		if (!orderCancelled) {
//			MarkOrderAsCancelledCommand cancelledCommand = new MarkOrderAsCancelledCommand();
//			commandGateway.send(cancelledCommand, LoggingCallback.INSTANCE);
//		} else {
//			OpenShoppingCartForCheckoutCommand openShoppingCartCommand = new OpenShoppingCartForCheckoutCommand();
//			commandGateway.send(openShoppingCartCommand, LoggingCallback.INSTANCE);
//			end();
//		}
//	}
}
