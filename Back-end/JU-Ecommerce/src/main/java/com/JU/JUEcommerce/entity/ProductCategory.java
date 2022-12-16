package com.JU.JUEcommerce.entity;

import javax.persistence.CascadeType;    
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Entity // to tell spring this is entity class that is mapped to database
@Table(name="Product_Category")  // using the same table name as we created in the database in order for this class to be mapped to
// NOTE!! - @Data -> Known bug whenever you make use of relationship such as OneToMany and ManyToOne, 
		//the bug may cause an infinite loop. Eventually the app would have an out-of-memory exception.
		// https://stackoverflow.com/questions/33234546/spring-boot-jpa-onetomany-relationship-causes-infinite-loop/37727206#37727206
@Getter
@Setter
public class ProductCategory {
	
	// define fields

	@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
	@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
	@Column(name="productCategory_id") // to map the fields to the customer table columns and need to match exact column name in the table
	private Long productCategoryId;	
	
	@Column(name="category_name")// to map the fields to the customer table columns and need to match exact column name in the table
	private String categoryName;   
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prodcutCateogryID") // one product category has many products, mappedBY is variable name that it has foreign key
	private Set<Product> products;// is an unordered collection of objects in which duplicate values cannot be stored.
	
}
