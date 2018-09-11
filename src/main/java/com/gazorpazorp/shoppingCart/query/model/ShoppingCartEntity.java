package com.gazorpazorp.shoppingCart.query.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="CART")
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartEntity {
	@Id
	@Column(name = "id", length = 36)
	private String id;
	@Column(name = "customer_id", length = 36)
	private String customerId;
	@Version
	private Long version;
	@NotNull
	private Long aggregateVersion;	
	
	@ElementCollection
//	@Embedded
	private Map<String, Integer> items;	
	
	public ShoppingCartEntity (String id, String customerId, Long aggregateVersion) {
		this.id = id;
		this.customerId = customerId;
		this.aggregateVersion = aggregateVersion;
		items = new HashMap<>();
	}
	
	public ShoppingCartEntity prepareForJSON() {
		return new ShoppingCartEntity(getId(), getCustomerId(), getVersion(), getAggregateVersion(), getItems());
	}
}
