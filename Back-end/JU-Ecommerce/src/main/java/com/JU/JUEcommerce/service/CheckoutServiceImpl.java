package com.JU.JUEcommerce.service;

import java.util.Set;   
import java.util.UUID;

import javax.transaction.Transactional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JU.JUEcommerce.dao.CustomerRepository;
import com.JU.JUEcommerce.dto.Purchase;
import com.JU.JUEcommerce.dto.PurchaseResponse;
import com.JU.JUEcommerce.entity.Customer;
import com.JU.JUEcommerce.entity.Order;
import com.JU.JUEcommerce.entity.OrderItem;



@Service // spring will scan this as Service inheirts from component annotation
public class CheckoutServiceImpl implements CheckoutService {
	
	
	private CustomerRepository customerRepository;
	
	
	public CheckoutServiceImpl() {}
	
	@Autowired
	public CheckoutServiceImpl(CustomerRepository customerRepository) { // injecting the dependency
		
		this.customerRepository = customerRepository;
		
	}

	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {
		
		
		// need to retrieve the order infro from data transfer object	
		Order order = purchase.getOrder();
		
		
		// generete tracking number
		String orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);
		
		// populate order with orderItems
		Set<OrderItem> orderItems = purchase.getOrderItems();
		
		for (OrderItem OrderItem : orderItems) {
			
			order.add(OrderItem);
		    
		}
		
		// populate order with billingAddress 
		order.setBillingAddress(purchase.getBillingAddress());
		
		// populate order with ShippingAddress
		order.setShippingAddress(purchase.getShippingAddress());
		
		// populate customer with order
		Customer customer = purchase.getCustomer();
		
		// check if this is an existing customer
		String customerEmail = customer.getEmail();
		
		Customer customerFromDB = this.customerRepository.findByEmail(customerEmail);
		
		// IF is is found we'll assign them
		
		if(customerFromDB!=null) {
			
			customer = customerFromDB;
		}
		
		// add the order to the customer
		customer.add(order);
		
		// save to the database
		this.customerRepository.save(customer);
		
	
		
		// return response
		return new PurchaseResponse(orderTrackingNumber);
	}

	private String generateOrderTrackingNumber() {
	// this method will return a unique id that is hard to guess and random
		
		// don;t use nano there could be chance of duplicate id
		
		// UUID: Universally Unique IDentifier

			// Standardized methods for generating unique IDs
		
		// generate a random UUID number (UUID version-4)
		return ("JUECOMMERCE" + UUID.randomUUID().toString());
	}

}
