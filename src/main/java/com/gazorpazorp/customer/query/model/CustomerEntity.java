package com.gazorpazorp.customer.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="CUSTOMER")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

	@Id
	@Column(name="id", length=36)
	private String id;
	@Column(name="stripe_id")
	private String stripeId;
	@Column(name="name")
	private String name;
	@Version
	private Long version;
	@NotNull
	private Long aggregateVersion;
}
