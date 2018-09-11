package com.gazorpazorp.shoppingCart.query.handler;

import java.util.Map;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.SequenceNumber;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gazorpazorp.common.events.cart.CartClearedEvent;
import com.gazorpazorp.common.events.cart.ItemAddedToCartEvent;
import com.gazorpazorp.common.events.cart.ItemRemovedFromCartEvent;
import com.gazorpazorp.common.events.cart.ShoppingCartInitiatedEvent;
import com.gazorpazorp.shoppingCart.query.model.FetchCartByIdQuery;
import com.gazorpazorp.shoppingCart.query.model.ShoppingCartEntity;
import com.gazorpazorp.shoppingCart.query.repository.ShoppingCartEntityRepository;


@ProcessingGroup("default")
@Component
public class ShoppingCartEventHandler {

	private ShoppingCartEntityRepository cartRepo;
	
	@Autowired
	public ShoppingCartEventHandler (ShoppingCartEntityRepository cartRepo) {
		this.cartRepo = cartRepo;
	}
	
	@EventHandler
	public void handle (ShoppingCartInitiatedEvent event, @SequenceNumber Long aggregateVersion) {
		System.out.println("new shopping cart");
		cartRepo.save(new ShoppingCartEntity(event.getAggregateId(), event.getCustomerId(), aggregateVersion));
	}
	
	@EventHandler
	public void handle (ItemAddedToCartEvent event, @SequenceNumber Long aggregateVersion) {
		ShoppingCartEntity cart = cartRepo.getOne(event.getAggregateId());
		cart.setAggregateVersion(aggregateVersion);
		Map<String, Integer> items = cart.getItems();
		Integer itemQty = items.get(event.getProductId());
		if (itemQty == null) {
			items.put(event.getProductId(), event.getQty());
		} else {
			itemQty += event.getQty();
			items.replace(event.getProductId(), itemQty);
		}
		cart.setItems(items);
		cartRepo.save(cart);
	}
	
	@EventHandler
	public void handle (ItemRemovedFromCartEvent event, @SequenceNumber Long aggregateVersion) {
		ShoppingCartEntity cart = cartRepo.getOne(event.getAggregateId());
		cart.setAggregateVersion(aggregateVersion);
		Map<String, Integer> items = cart.getItems();
		Integer itemQty = items.get(event.getProductId());
		if (itemQty != null) {
			itemQty -= event.getQty();
			if (itemQty < 1)
				items.remove(event.getProductId());
			else
				items.replace(event.getProductId(), itemQty);
		}
		cart.setItems(items);
		cartRepo.save(cart);
	}
	
	@EventHandler
	public void handle (CartClearedEvent event, @SequenceNumber Long aggregateVersion) {
		ShoppingCartEntity cart = cartRepo.getOne(event.getAggregateId());
		cart.setAggregateVersion(aggregateVersion);
		Map<String, Integer> items = cart.getItems();
		items.clear();
		cart.setItems(items);
		cartRepo.save(cart);
	}
	
	@QueryHandler
	public ShoppingCartEntity handle (FetchCartByIdQuery query) {
		return cartRepo.getOne(query.getId()).prepareForJSON();
	}
	
//	@EventHandler
//	public void handle (ShoppingCartUpdatedEvent event, @SequenceNumber Long aggregateVersion) {
//		ShoppingCartEntity cart = cartRepo.findById(event.getAggregateId()).get();
//		cart.setAggregateVersion(aggregateVersion);
//		cart.setNum(event.getNum());
//		cartRepo.save(cart);
//	}
}
