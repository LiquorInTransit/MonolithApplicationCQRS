package com.gazorpazorp.shoppingCart.configuration;

import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gazorpazorp.common.configuration.EventTypeSnapshotTriggerDefinition;
import com.gazorpazorp.common.events.cart.CartClearedEvent;

@Configuration
public class ShoppingCartAxonConfig {
	@Bean
	public SnapshotTriggerDefinition shoppingCartTriggerDefinition(Snapshotter snapshotter){
		return new EventTypeSnapshotTriggerDefinition(snapshotter, CartClearedEvent.class);
	}	
	
}
