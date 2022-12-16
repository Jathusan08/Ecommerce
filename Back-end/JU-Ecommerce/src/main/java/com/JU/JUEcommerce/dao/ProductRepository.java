package com.JU.JUEcommerce.dao;

import org.springframework.data.domain.Page;   
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.stereotype.Repository;

import com.JU.JUEcommerce.entity.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

import javax.transaction.Transactional;


//@Repository // add '@Repository' annotaion when a repository interface extending to JpaReposiroty interface
//@CrossOrigin("http://localhost:4200") //- configured in the MyDataRestConfig
@RepositoryRestResource(collectionResourceRel = "products", path = "products")// adding customisation for this given repositiry
public interface ProductRepository extends  JpaRepository<Product,Long>{
	
	// by  extending to the 'JpaRepository' interface it provide some methods CRUD methods as well as other methods
	// to interact with our database
	
	//				    (1)		 (2)
	// JpaRepository<Product, Long>
	
	// JpaRepository is generic type, where you need to insert the following things in order :
	
	// (1) you provide the entity type which is the class that maps to the table and it annotated with 
	// '@Entity' annotation
	
	// (2) you provide the  primary key, in our case the Product table our id type is number so is long. 
	// 		However you can't provide non-primitve type so you need to use it wrapper class long --> Long
	
	Page<Product> findByProdcutCateogryIDProductCategoryId (@Param("id") Long id, Pageable pageable); 
	
	Page<Product> findByNameContaining (@Param("name") String name, Pageable pageable); 
	
	
	@Query(value ="SELECT * FROM Product where product_id = :productID",nativeQuery= true)
	@Modifying
	@Transactional
	public List<Product> getProductQuantity(@Param("productID")Long productID) ;
	
	
	@Query(value ="UPDATE Product set units_in_stock = :updateQuantity where product_id = :productID",nativeQuery= true)
	@Modifying
	@Transactional
	public int updateProductQuantity(@Param("updateQuantity")int updateQuantity, @Param("productID")long productID) ;


}
