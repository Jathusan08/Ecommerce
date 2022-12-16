package com.JU.JUEcommerce.dto;

import java.util.Set;

import com.JU.JUEcommerce.entity.Address;
import com.JU.JUEcommerce.entity.Customer;
import com.JU.JUEcommerce.entity.Order;
import com.JU.JUEcommerce.entity.OrderItem;

import lombok.Data;

@Data
public class Purchase {
	
	// purchase has relationship to our customer, shippind address, billing address, order and collection of order items
	
	private Customer customer;
	private Address shippingAddress;
	private Address billingAddress;
	private Order order;
	private Set<OrderItem> orderItems; // when we pass this to over the REST API in json, 
	//JSON will take these collection and make use of array JSON

	

	
}
