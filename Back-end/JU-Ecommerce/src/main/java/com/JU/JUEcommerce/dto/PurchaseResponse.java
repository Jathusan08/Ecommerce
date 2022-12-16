package com.JU.JUEcommerce.dto;



import lombok.Data;

@Data
public class PurchaseResponse { // pojo class to send back a java object as JSON 
	//when we post if the data if successful it will send it as JSON response with order tracking number
	
	
	private final String orderTrackingNumber; // Lombok @Data will generate constructor for final fields

}
