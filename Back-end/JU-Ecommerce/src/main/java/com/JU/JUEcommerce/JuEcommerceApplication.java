package com.JU.JUEcommerce;

import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.JU.JUEcommerce.entity.State;
import com.JU.JUEcommerce.dto.Purchase;
import com.JU.JUEcommerce.entity.Address;
import com.JU.JUEcommerce.entity.Country;
import com.JU.JUEcommerce.entity.Customer;
import com.JU.JUEcommerce.entity.Order;
import com.JU.JUEcommerce.entity.OrderItem;
import com.JU.JUEcommerce.entity.Product;
import com.JU.JUEcommerce.entity.ProductCategory;

@SpringBootApplication
public class JuEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuEcommerceApplication.class, args);
	}
	
	@Bean(name="state")
	@Scope(value = "prototype")
	State getState() {
		return new State();
	}
	
	@Bean(name="purchase")
	@Scope(value = "prototype")
	Purchase getPurchase() {
		return new Purchase();
	}
	
	@Bean(name="address")
	@Scope(value = "prototype")
	Address getAddress() {
		return new Address();
	}
	
	@Bean(name="order")
	@Scope(value = "prototype")
	Order getOrder() {
		return new Order();
	}
	
	@Bean(name="orderItem")
	@Scope(value = "prototype")
	OrderItem getOrderItem() {
		return new OrderItem();
	}
	
	
	@Bean(name="customer")
	@Scope(value = "prototype")
	Customer getCustomer() {
		return new Customer();
	}
	
	@Bean(name="product")
	@Scope(value = "prototype")
	Product getProduct() {
		return new Product();
	}
	
	@Bean(name="productCategory")
	@Scope(value = "prototype")
	ProductCategory getProductCategory() {
		return new ProductCategory();
	}

	
	@Bean(name="country")
	@Scope(value = "prototype")
	Country getCountry() {
		return new Country();
	}

}
