package com.gazorpazorp.shoppingCart.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

	
	
	public void prepareForCheckout (Map<String, Integer> items, String customerId) throws Exception {
		//Check order service for an active order. throw ActiveOrderException if there is one.
	}
}
