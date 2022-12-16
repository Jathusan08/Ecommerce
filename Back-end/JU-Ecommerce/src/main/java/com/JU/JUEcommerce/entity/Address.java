package com.JU.JUEcommerce.entity;

import javax.persistence.Column;  
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity // to tell spring this is entity class that is mapped to database
@Table(name="address")  // using the same table name as we created in the database in order for this class to be mapped to
//NOTE!! - @Data -> Known bug whenever you make use of relationship such as OneToMany and ManyToOne, 
		//the bug may cause an infinite loop. Eventually the app would have an out-of-memory exception.
		// https://stackoverflow.com/questions/33234546/spring-boot-jpa-onetomany-relationship-causes-infinite-loop/37727206#37727206
@Getter
@Setter
public class Address {
	
	// define fields

				@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
				@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
				@Column(name="address_id") // to map the fields to the customer table columns and need to match exact column name in the table
				private Long addressId;	
				
				@Column(name="city") // to map the fields to the customer table columns and need to match exact column name in the table
				private String city;
				
				@Column(name="country") // to map the fields to the customer table columns and need to match exact column name in the table
				private String country;
				
				@Column(name="state") // to map the fields to the customer table columns and need to match exact column name in the table
				private String state;
				
				@Column(name="street") // to map the fields to the customer table columns and need to match exact column name in the table
				private String street;
				
				@Column(name="post_code") // to map the fields to the customer table columns and need to match exact column name in the table
				private String postCode;
				
				
				// setting up the realationship between address and order
				
				@OneToOne
				@PrimaryKeyJoinColumn // join using primary keys, by default keys have same name
				private Order order;
				
				
			
}