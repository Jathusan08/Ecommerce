package com.JU.JUEcommerce.controller;


//import java.util.Set;  

import org.springframework.beans.factory.annotation.Autowired;   

//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JU.JUEcommerce.dto.Purchase;
import com.JU.JUEcommerce.dto.PurchaseResponse;
import com.JU.JUEcommerce.service.CheckoutService;
import com.JU.JUEcommerce.service.ProductService;



//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/JUEcommerce-api/checkout")
public class CheckoutController {
	
	
	private CheckoutService checkoutService;
	private ProductService productService;

	
	public CheckoutController() {}
	
	@Autowired 
	public CheckoutController(CheckoutService checkoutService,ProductService productService ) {
		
		this.checkoutService = checkoutService;
		this.productService = productService;
	}
	
	
	@PostMapping("/purchase")
	public PurchaseResponse placeOrder(@RequestBody Purchase purchase) { // The JSON will come over in the request body
		

		this.productService.updateQuantity(purchase.getOrderItems());
		
		PurchaseResponse purchaseResponse = this.checkoutService.placeOrder(purchase); // delegate to my service
		
		
		
		return purchaseResponse;
	}
	

	
}
