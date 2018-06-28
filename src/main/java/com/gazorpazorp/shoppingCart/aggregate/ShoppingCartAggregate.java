package com.gazorpazorp.shoppingCart.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.saga.AssociationValue;
import org.axonframework.eventhandling.saga.SagaRepository;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import com.gazorpazorp.common.events.cart.CartCheckedOutEvent;
import com.gazorpazorp.common.events.cart.CartClearedEvent;
import com.gazorpazorp.common.events.cart.ItemAddedToCartEvent;
import com.gazorpazorp.common.events.cart.ItemRemovedFromCartEvent;
import com.gazorpazorp.common.events.cart.ShoppingCartInitiatedEvent;
import com.gazorpazorp.common.events.order.OrderPlacedEvent;
import com.gazorpazorp.common.exception.ActiveOrderException;
import com.gazorpazorp.common.exception.EmptyCartCheckoutException;
import com.gazorpazorp.common.exception.InsufficientInventoryException;
import com.gazorpazorp.shoppingCart.command.AddItemToCartCommand;
import com.gazorpazorp.shoppingCart.command.CheckoutCartCommand;
import com.gazorpazorp.shoppingCart.command.ClearCartCommand;
import com.gazorpazorp.shoppingCart.command.CreateCartCommand;
import com.gazorpazorp.shoppingCart.command.RemoveItemFromCartCommand;
import com.gazorpazorp.shoppingCart.service.CheckoutService;

import lombok.NoArgsConstructor;


//@Getter
@NoArgsConstructor
@Aggregate(snapshotTriggerDefinition="shoppingCartTriggerDefinition")
public class ShoppingCartAggregate {

	@AggregateIdentifier
	private String id;
	
	private String customerId;
	
	private Map<String, Integer> items;	
	
	private boolean readyToCheckout = true;
	
	/*-------------CQRS------------*/
	//Initialization
	@CommandHandler
	public ShoppingCartAggregate (CreateCartCommand command) {
		apply(new ShoppingCartInitiatedEvent(command.getTargetId(), command.getCustomerId(), command.getAuditEntry()));
	}	
	@EventSourcingHandler
	public void on (ShoppingCartInitiatedEvent event) {
		this.id = event.getAggregateId();
		items = new HashMap<>();
	}
	
	//Add, Remove, Clear
	/**
	 * Add
	 * @param command
	 * @throws Exception
	 */
	@CommandHandler
	public void addItem (AddItemToCartCommand command) {
		apply (new ItemAddedToCartEvent(command.getTargetId(), command.getProductId(), command.getQty(), command.getAuditEntry()));
	}
	@EventSourcingHandler
	public void on (ItemAddedToCartEvent event) throws InsufficientInventoryException {
		Integer itemQty = items.get(event.getProductId());
		if (itemQty == null) {
			items.put(event.getProductId(), event.getQty());
		} else {
			itemQty += event.getQty();
			items.replace(event.getProductId(), itemQty);
		}
	}
	
	/**
	 * Remove
	 * @param command
	 * @throws Exception
	 */
	@CommandHandler
	public void removeItem (RemoveItemFromCartCommand command) {
		apply (new ItemRemovedFromCartEvent(command.getTargetId(), command.getProductId(), command.getQty(), command.getAuditEntry()));
	}
	@EventSourcingHandler
	public void on (ItemRemovedFromCartEvent event) {
		Integer itemQty = items.get(event.getProductId());
		if (itemQty != null) {
			itemQty -= event.getQty();
			if (itemQty < 1)
				items.remove(event.getProductId());
			else
				items.replace(event.getProductId(), itemQty);
		}
	}
	
	/**
	 * Clear Cart
	 * @param command
	 * @throws Exception
	 */
	@CommandHandler
	public void clearCart (ClearCartCommand command) {
		apply (new CartClearedEvent(command.getTargetId(), command.getAuditEntry()));
	}
	@EventSourcingHandler
	public void on (CartClearedEvent event) {
		items.clear();
	}
	
	@CommandHandler
	public void checkout (CheckoutCartCommand command, CheckoutService checkoutService) throws Exception {
		//First, be sure that there's items in the cart
		if (this.items.isEmpty())
			throw new EmptyCartCheckoutException("You cannot checkout with an empty cart");
//		checkoutService.prepareForCheckout(items, customerId);
		//apply(new OrderPlacedEvent(this.id, this.customerId, this.items, command.getAuditEntry()));
		if (!this.readyToCheckout)
			throw new ActiveOrderException();
		apply(new CartCheckedOutEvent(this.id, this.customerId, this.items, command.getAuditEntry()))
			.andThen(()->apply(new OrderPlacedEvent(this.id, this.customerId, this.items, command.getAuditEntry())));
	}
	
	@EventSourcingHandler
	public void on (CartCheckedOutEvent event) {
		this.readyToCheckout = false;
	}

}
