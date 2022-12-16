package com.JU.JUEcommerce.service;

import java.util.List;  
import java.util.Set;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.JU.JUEcommerce.dao.ProductRepository;
import com.JU.JUEcommerce.entity.OrderItem;
import com.JU.JUEcommerce.entity.Product;



@Service // spring will scan this as Service inheirts from component annotation
@Transactional
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepository;
	
	
	public ProductServiceImpl() {}
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) { // injecting the dependency
		
		this.productRepository =  productRepository;
		
	}

	@Override
	public List<Product> getProductQuantity(long productID) {
		// TODO Auto-generated method stub
		return this.productRepository.getProductQuantity(productID);
	}

	@Override
	public void updateQuantity(Set<OrderItem> orderItem) {
		
		List<Product> product = null;
		
		int update = 0;
		
		for (OrderItem orderItemsSet : orderItem) {

			
			product = this.getProductQuantity(orderItemsSet.getProductId());
			
//			System.out.println("productquantity: " + product.get(0).getUnitsInStock());
//			
//			System.out.println("orderQuantity: " + orderItemsSet.getQuantity() );
//			
			update = product.get(0).getUnitsInStock() - orderItemsSet.getQuantity();
			
//			System.out.println("New Quantity: " + update );
			
	this.productRepository.updateProductQuantity(update,product.get(0).getProductId());
			

			}

		
	}


	
	
	

}
