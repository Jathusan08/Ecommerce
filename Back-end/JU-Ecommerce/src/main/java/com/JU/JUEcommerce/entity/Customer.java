package com.JU.JUEcommerce.entity;


import java.util.HashSet; 
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

@Entity // to tell spring this is entity class that is mapped to database
@Table(name="customer")  // using the same table name as we created in the database in order for this class to be mapped to
//NOTE!! - @Data -> Known bug whenever you make use of relationship such as OneToMany and ManyToOne, 
		//the bug may cause an infinite loop. Eventually the app would have an out-of-memory exception.
		// https://stackoverflow.com/questions/33234546/spring-boot-jpa-onetomany-relationship-causes-infinite-loop/37727206#37727206
@Getter
@Setter
public class Customer {
	
	// define fields

			@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
			@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
			@Column(name="customer_id") // to map the fields to the customer table columns and need to match exact column name in the table
			private Long customerId;	
			
			@Column(name="first_name") // to map the fields to the customer table columns and need to match exact column name in the table
			private String firstName;
			
			@Column(name="last_name") // to map the fields to the customer table columns and need to match exact column name in the table
			private String lastName;
			
			@Column(name="email") // to map the fields to the customer table columns and need to match exact column name in the table
			private String email;
			
			
			@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer") // 1 customer have many orders
			private Set<Order> orders = new HashSet<>();
			
			
			public void add(Order order) {
				
				if(order !=null) {
					
					if(this.orders == null) {
						
						this.orders = new HashSet<>();
					}
					
					this.orders.add(order);
			
					order.setCustomer(this); // bi-directional realationship set up
					
				}
				else {
					
					System.out.println("orderItems is empty!!");
				}
			}
			
			
}
