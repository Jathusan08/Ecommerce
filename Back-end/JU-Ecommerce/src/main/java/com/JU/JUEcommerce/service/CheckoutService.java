package com.JU.JUEcommerce.service;

import com.JU.JUEcommerce.dto.Purchase;
import com.JU.JUEcommerce.dto.PurchaseResponse;

public interface CheckoutService {
	
	PurchaseResponse placeOrder(Purchase purchase); // setting the parameter of data transfer object

}
