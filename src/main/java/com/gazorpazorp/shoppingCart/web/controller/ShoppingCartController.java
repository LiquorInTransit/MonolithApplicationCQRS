package com.gazorpazorp.shoppingCart.web.controller;

import java.util.UUID;

import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.common.AuditEntry;
import com.gazorpazorp.common.AuditableAbstractCommand;
import com.gazorpazorp.shoppingCart.command.AddItemToCartCommand;
import com.gazorpazorp.shoppingCart.command.CheckoutCartCommand;
import com.gazorpazorp.shoppingCart.command.ClearCartCommand;
import com.gazorpazorp.shoppingCart.command.CreateCartCommand;
import com.gazorpazorp.shoppingCart.command.RemoveItemFromCartCommand;
import com.gazorpazorp.shoppingCart.query.model.FetchCartByIdQuery;
import com.gazorpazorp.shoppingCart.query.model.ShoppingCartEntity;
import com.gazorpazorp.shoppingCart.service.CheckoutService;
import com.gazorpazorp.shoppingCart.web.request.ShoppingCartRequest;

@RestController
@RequestMapping(value = "/api/command")
public class ShoppingCartController {

	private CommandGateway commandGateway;
	private QueryGateway queryGateway;
	
	private CheckoutService checkoutService;
	
	private String getCurrentUser() {
//		if (SecurityContextHolder.getContext().getAuthentication() != null) {
//			return SecurityContextHolder.getContext().getAuthentication().getName();
//		}
//		return null;
		return "user";
	}
	
	private AuditEntry getAuditEntry() {
		return new AuditEntry(getCurrentUser());
	}
	
	@Autowired
	public ShoppingCartController (CommandGateway commandGateway, QueryGateway queryGateway) {
		this.commandGateway = commandGateway;
		this.queryGateway = queryGateway;
	}
	
	@PostMapping("/cart")
	@ResponseStatus(value = HttpStatus.OK)
	public String createCart () {
		CreateCartCommand command = new CreateCartCommand(UUID.randomUUID().toString(), getAuditEntry());
		commandGateway.send(command, LoggingCallback.INSTANCE);
		return command.getTargetId();
	}
	
	@PostMapping("/cart/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public void updateCart (@RequestBody ShoppingCartRequest request, @PathVariable String id) throws Exception {
		AuditableAbstractCommand command;
		switch (request.getType()) {
		case ADD:
			command = new AddItemToCartCommand(id, request.getProductId(), request.getQty(), getAuditEntry());
			break;
		case REMOVE:
			command = new RemoveItemFromCartCommand(id, request.getProductId(), request.getQty(), getAuditEntry());
			break;
		case CLEAR:
			command = new ClearCartCommand(id, getAuditEntry());
			break;
		case CHECKOUT:
			command = new CheckoutCartCommand(id, getAuditEntry());
			break;
		default:
			throw new IllegalArgumentException("You must set a request type");
				
		}
		commandGateway.send(command);
	}
	
	@GetMapping("/cart/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ShoppingCartEntity getCart (@PathVariable String id) throws Exception {
		FetchCartByIdQuery query = new FetchCartByIdQuery(id);
		SubscriptionQueryResult<ShoppingCartEntity, ShoppingCartEntity> result = queryGateway.subscriptionQuery(query, ResponseTypes.instanceOf(ShoppingCartEntity.class), ResponseTypes.instanceOf(ShoppingCartEntity.class));
		ShoppingCartEntity entity = result.initialResult().block();
		return entity;
	}
}
