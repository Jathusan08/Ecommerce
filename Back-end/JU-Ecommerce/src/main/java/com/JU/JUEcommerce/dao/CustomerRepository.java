package com.JU.JUEcommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JU.JUEcommerce.entity.Customer; 




public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	// customer has a collection of orders, so when a customer purchase something, we want to grab the customer and assemble accordingly
	// and then we'll save the actual customer using Customer Repository
	
	Customer findByEmail(String customerEmail); // if is not found it will return null and we do our validation

}
