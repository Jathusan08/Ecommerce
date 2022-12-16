package com.JU.JUEcommerce.service;

import java.util.List; 
import java.util.Set;

import com.JU.JUEcommerce.entity.OrderItem;
import com.JU.JUEcommerce.entity.Product;



public interface ProductService {

	public List<Product> getProductQuantity(long productID);
	
	public void updateQuantity(Set<OrderItem> orderItem );
	
}
