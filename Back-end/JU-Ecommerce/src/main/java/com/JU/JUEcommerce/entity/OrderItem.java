package com.JU.JUEcommerce.entity;

import java.math.BigDecimal; 


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity // to tell spring this is entity class that is mapped to database
@Table(name="order_item")  // using the same table name as we created in the database in order for this class to be mapped to
//NOTE!! - @Data -> Known bug whenever you make use of relationship such as OneToMany and ManyToOne, 
		//the bug may cause an infinite loop. Eventually the app would have an out-of-memory exception.
		// https://stackoverflow.com/questions/33234546/spring-boot-jpa-onetomany-relationship-causes-infinite-loop/37727206#37727206
@Getter
@Setter
public class OrderItem {
	
	// define fields

	@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
	@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
	@Column(name="orderItem_id") // to map the fields to the order_item table columns and need to match exact column name in the table
	private Long orderItemId;	
	
	@Column(name="image_url") // to map the fields to the order_item table columns and need to match exact column name in the table
	private String imageUrl;
	
	@Column(name="quantity") // to map the fields to the order_item table columns and need to match exact column name in the table
	private int quantity;
	
	@Column(name="unit_price") // to map the fields to the order_item table columns and need to match exact column name in the table
	private BigDecimal unitPrice;
	
	@Column(name="product_id") // to map the fields to the order_item table columns and need to match exact column name in the table
	private Long productId;
	
	@ManyToOne // Many orderitems belone to one order
	@JoinColumn(name = "order_id") // foreign key
	private Order order;

}
