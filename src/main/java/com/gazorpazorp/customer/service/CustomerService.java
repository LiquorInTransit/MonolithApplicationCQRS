package com.gazorpazorp.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gazorpazorp.customer.query.model.CustomerEntity;
import com.gazorpazorp.customer.query.repo.CustomerEntityRepository;

@Service
public class CustomerService {
	
	private CustomerEntityRepository customerRepo;
	
	@Autowired
	public CustomerService(CustomerEntityRepository customerRepo) {
		this.customerRepo = customerRepo;
	}
	
	public CustomerEntity getCustomer(String customerId) {
		return customerRepo.getOne(customerId);
	}

}
