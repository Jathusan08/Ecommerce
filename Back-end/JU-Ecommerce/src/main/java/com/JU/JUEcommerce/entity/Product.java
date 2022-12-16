package com.JU.JUEcommerce.entity;

import java.math.BigDecimal;  
import java.util.Date;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity // to tell spring this is entity class that is mapped to database
@Table(name="product")  // using the same table name as we created in the database in order for this class to be mapped to
@Data // lombook
public class Product {

	
	// define fields

	@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
	@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
	@Column(name="product_id") // to map the fields to the customer table columns and need to match exact column name in the table
	private Long productId;	
	
	@Column(name="sku") // to map the fields to the customer table columns and need to match exact column name in the table
	private String sku;
	
	@Column(name="name") // to map the fields to the customer table columns and need to match exact column name in the table
	private String name; // https://www.shopify.com/sg/retail/what-is-a-sku-numbers
	
	@Column(name="description") // to map the fields to the customer table columns and need to match exact column name in the table
	private String description;
	
	@Column(name="unit_price") // to map the fields to the customer table columns and need to match exact column name in the table
	private BigDecimal unitPrice;
	
	@Column(name="image_url") // to map the fields to the customer table columns and need to match exact column name in the table
	private String imageURL;
	
	@Column(name="active") // to map the fields to the customer table columns and need to match exact column name in the table
	private boolean active;
	
	@Column(name="units_in_stock") // to map the fields to the customer table columns and need to match exact column name in the table
	private int unitsInStock;
	
	@Column(name="date_created") // to map the fields to the customer table columns and need to match exact column name in the table
	@CreationTimestamp // these are special annotations Hibernate will automatically manage the timestamps, 
					//so there no need for the developer to mannually call these method or set these fields here
	private Date dateCreated;
	
	@Column(name="last_updated") // to map the fields to the customer table columns and need to match exact column name in the table
	@UpdateTimestamp // these are special annotations Hibernate will automatically manage the timestamps, 
	//so there no need for the developer to manually call these method or set these fields here
	private Date lastUpdated;
	
	@ManyToOne() // many products belongs to one category. THis is the foreign key
	@JoinColumn(name="productCategory_id", nullable = false) // to map the fields to the customer table columns and // need to match exact column name in the table
	private ProductCategory prodcutCateogryID;
//	
//	private void test() {
//		
//	}
	
}
