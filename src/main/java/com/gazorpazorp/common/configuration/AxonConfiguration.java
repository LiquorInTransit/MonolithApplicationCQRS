package com.gazorpazorp.common.configuration;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfiguration {

//	@Bean
//	CommandBus commandBus (TransactionManager transactionManager) {
//		SimpleCommandBus commandBus = new SimpleCommandBus(transactionManager, NoOpMessageMonitor.INSTANCE);
//		commandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
//		return commandBus;
//	}
	@Bean
	public SpringAggregateSnapshotterFactoryBean snapshotterFactoryBean() {
		return new SpringAggregateSnapshotterFactoryBean();
	}	
}
