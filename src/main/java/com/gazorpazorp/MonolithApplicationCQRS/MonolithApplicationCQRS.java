package com.gazorpazorp.MonolithApplicationCQRS;

import javax.annotation.PostConstruct;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.gazorpazorp.shoppingCart.aggregate.ShoppingCartAggregate;

@SpringBootApplication(scanBasePackages="com.gazorpazorp")
@EntityScan(basePackages={"com.gazorpazorp", "org.axonframework.eventsourcing.eventstore.jpa", "org.axonframework.eventhandling.saga.repository.jpa", "org.axonframework.eventhandling.tokenstore.jpa"})
@EnableJpaRepositories("com.gazorpazorp")
public class MonolithApplicationCQRS {
	
	
	@PostConstruct
	public void getDbManager(){
	   DatabaseManagerSwing.main(
		new String[] { "--url", "jdbc:hsqldb:mem:test://localhost/axontest?characterEncoding=UTF-8", "--user", "SA", "--password", ""});
	}

	public static void main(String[] args) {
		SpringApplication.run(MonolithApplicationCQRS.class, args);
	}
	
	@Configuration
	public static class AmqpConfiguration {
		
		@Bean
		public Exchange eventsExchange () {
			return ExchangeBuilder.fanoutExchange("events").build();
		}
		@Bean
		public Queue cartsEventsQueue () {
			return QueueBuilder.durable("cart-events").build();
		}
		@Bean
		public Binding cartsEventsBinding () {
			return BindingBuilder.bind(cartsEventsQueue()).to(eventsExchange()).with("*").noargs();
		}
		
		@Autowired
		public void configure (AmqpAdmin admin) {
			admin.declareExchange(eventsExchange());
			admin.declareQueue(cartsEventsQueue());
			admin.declareBinding(cartsEventsBinding());
		}
		
//		@Bean
//		public SpringAMQPMessageSource cartEvents (Serializer serializer) {
//			return new SpringAMQPMessageSource(serializer) {
//				@RabbitListener(queues = "cart-events")
//				@Override
//				public void onMessage(Message message, Channel channel) throws Exception {
//					super.onMessage(message, channel);
//				}
//			};
//		}
		
		
		

	}
}
