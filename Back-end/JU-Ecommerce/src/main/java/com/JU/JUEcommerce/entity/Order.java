package com.JU.JUEcommerce.entity;


import java.math.BigDecimal; 
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity // to tell spring this is entity class that is mapped to database
@Table(name="orders")  // using the same table name as we created in the database in order for this class to be mapped to
//NOTE!! - @Data -> Known bug whenever you make use of relationship such as OneToMany and ManyToOne, 
		//the bug may cause an infinite loop. Eventually the app would have an out-of-memory exception.
		// https://stackoverflow.com/questions/33234546/spring-boot-jpa-onetomany-relationship-causes-infinite-loop/37727206#37727206
@Getter
@Setter
public class Order {
	
	// define fields

	@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
	@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
	@Column(name="orders_id") // to map the fields to the customer table columns and need to match exact column name in the table
	private Long orderId;	
	
	@Column(name="order_tracking_number") // to map the fields to the customer table columns and need to match exact column name in the table
	private String orderTrackingNumber;
	
	@Column(name="total_quantity") // to map the fields to the customer table columns and need to match exact column name in the table
	private int totalQuantity;
	
	@Column(name="total_price") // to map the fields to the customer table columns and need to match exact column name in the table
	private BigDecimal totalPrice;
	
	@Column(name="status") // to map the fields to the customer table columns and need to 
	private String status;
	
	@Column(name="date_created") // to map the fields to the customer table columns and need to 
	@CreationTimestamp // these are special annotations Hibernate will automatically manage the timestamps, 
	//so there no need for the developer to mannually call these method or set these fields here
	private Date dateCreated;
	
	@Column(name="last_updated") // to map the fields to the customer table columns and need to 
	@UpdateTimestamp // these are special annotations Hibernate will automatically manage the timestamps, 
	//so there no need for the developer to manually call these method or set these fields here
	private Date lastUpdated;
	
	
	// a order has a collection of orderItems
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order") // 1 order can have many order items per an order, 'mappedBy = "order" ' 'order' is the same field reference name we need to use to decleare realtionshp
	private Set<OrderItem> orderItems = new HashSet<>();
	
	//////////////////////////////////////////////////
	
	@OneToOne(cascade = CascadeType.ALL) // 1 order belongs to 1 billing address
	@JoinColumn(name = "billing_address_id", referencedColumnName ="address_id") // foreign key, referencedColumnName is the id defined in the  actual address table
	private Address billingAddress;
	
	@OneToOne(cascade = CascadeType.ALL) // 1 order belongs to 1 shipping address
	@JoinColumn(name = "shipping_address_id", referencedColumnName ="address_id") // foreign key
	private Address shippingAddress;
	
	@ManyToOne // many orders belong to 1 customer meaning 1 customer can have more than 1 orders
	@JoinColumn(name = "customer_id") // foreign key
	private Customer customer;
	
	
	public void add(OrderItem item) {
		
		if(item !=null) {
			
			if(this.orderItems == null) {
				
				this.orderItems = new HashSet<>();
			}
			
			this.orderItems.add(item);
			item.setOrder(this); // bi-directional realationship set up
			
		}
		else {
			
			System.out.println("orderItems is empty!!");
		}
	}
	
	

}
