package com.gazorpazorp.shoppingCart.aggregate;

import static org.axonframework.eventhandling.saga.SagaLifecycle.associateWith;

import java.util.Date;
import java.util.UUID;

import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.gazorpazorp.common.events.cart.CartCheckedOutEvent;
import com.gazorpazorp.common.exception.ActiveOrderException;
import com.gazorpazorp.orders.command.CreateOrderCommand;
@Saga
public class CheckoutSaga {
	

	@Autowired
	private transient CommandGateway commandGateway;
	
	@StartSaga
	@SagaEventHandler(associationProperty="aggregateId")
	public void on (CartCheckedOutEvent event) throws ActiveOrderException {
		String orderId = UUID.randomUUID().toString();
		associateWith("orderId", orderId);
		commandGateway.send(new CreateOrderCommand(orderId, event.getCustomerId(), new Date(), event.getItems(), event.getAuditEntry()), LoggingCallback.INSTANCE);
	}
	
	//TODO: This should be on the order cancelled event, or the order completed event. Should publish the OpenShoppingCartForCheckoutCommand.
//	@EndSaga
//	@SagaEventHandler(associationProperty="aggregateId")
//	public void on (CartOpenedEvent event) {
//		
//	}
	
	

}
