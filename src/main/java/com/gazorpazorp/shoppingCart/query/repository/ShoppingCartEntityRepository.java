package com.gazorpazorp.shoppingCart.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.gazorpazorp.shoppingCart.query.model.ShoppingCartEntity;

@RepositoryRestResource(collectionResourceRel="cart", path="cart")
public interface ShoppingCartEntityRepository extends JpaRepository <ShoppingCartEntity, String> {

	@Override
	@RestResource(exported=false)
	ShoppingCartEntity save (ShoppingCartEntity entity);
	
	@Override
	@RestResource(exported=false)
	void deleteById(String id);
	
	@Override
	@RestResource(exported=false)
	void delete (ShoppingCartEntity entity);
	
}
