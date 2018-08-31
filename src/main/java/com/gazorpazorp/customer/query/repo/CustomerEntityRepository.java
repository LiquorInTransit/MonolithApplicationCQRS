package com.gazorpazorp.customer.query.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.gazorpazorp.customer.query.model.CustomerEntity;
import com.gazorpazorp.shoppingCart.query.model.ShoppingCartEntity;

@RepositoryRestResource(collectionResourceRel="customer", path="customer")
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, String>{

	@Override
	@RestResource(exported=false)
	CustomerEntity save (CustomerEntity entity);
	
	@Override
	@RestResource(exported=false)
	void deleteById(String id);
	
	@Override
	@RestResource(exported=false)
	void delete (CustomerEntity entity);

}
