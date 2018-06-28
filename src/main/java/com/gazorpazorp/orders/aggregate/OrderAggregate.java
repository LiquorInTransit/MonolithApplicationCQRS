package com.gazorpazorp.orders.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.Date;
import java.util.Map;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import com.gazorpazorp.common.events.order.OrderInitiatedEvent;
import com.gazorpazorp.orders.aggregate.OrderStatus.Status;
import com.gazorpazorp.orders.command.CreateOrderCommand;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

	@AggregateIdentifier
	private String id;
	private String customerId;
	private Date orderDate;
	//OrderStatus will be a full object, with a status enum and a reason
	private OrderStatus status;
	Map<String, Integer> items;
	
	
	/*-------------CQRS------------*/
	//Initialization
	@CommandHandler
	public OrderAggregate (CreateOrderCommand command) {
		apply (new OrderInitiatedEvent(command.getTargetId(), command.getCustomerId(), command.getOrderDate(), command.getItems(), command.getAuditEntry()));
	}
	@EventSourcingHandler
	public void on (OrderInitiatedEvent event) {
		this.id = event.getAggregateId();
		this.customerId = event.getCustomerId();
		this.orderDate = event.getOrderDate();
		this.items = event.getItems();
		this.status = new OrderStatus(Status.CREATED);
	}
}
