package com.JU.JUEcommerce.dao;

import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.JU.JUEcommerce.entity.Order;



@RepositoryRestResource()
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	Page<Order> findByCustomerEmail (@Param("customerEmail") String name, Pageable pageable); 
	
	
//Page<Order> findByCustomerEmailOrderByDateCreatedDesc (@Param("customerEmail") String name, Pageable pageable); 

}
