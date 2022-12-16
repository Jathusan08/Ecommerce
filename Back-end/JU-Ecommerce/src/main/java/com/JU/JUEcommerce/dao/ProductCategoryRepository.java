package com.JU.JUEcommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.JU.JUEcommerce.entity.ProductCategory;




//@CrossOrigin("http://localhost:4200") // - configured in the MyDataRestConfig
@RepositoryRestResource(collectionResourceRel = "ProductCategory", path = "product-category")// adding customisation for this given repositiry
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
	
	
	// by extending to the 'JpaRepository' interface it provide some methods CRUD methods as well as other methods
	// to interact with our database
	
	//				    (1)		 (2)
	// JpaRepository<ProductCategory, Long>
	
	// JpaRepository is generic type, where you need to insert the following things in order :
	
	// (1) you provide the entity type which is the class that maps to the table and it annotated with 
	// '@Entity' annotation
	
	// (2) you provide the  primary key, in our case the Product table our id type is number so is long. 
	// 		However you can't provide non-primitve type so you need to use it wrapper class long --> Long
	

}
