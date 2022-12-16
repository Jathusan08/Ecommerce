package com.JU.JUEcommerce.config;

import java.util.ArrayList;    
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.JU.JUEcommerce.entity.Country;
import com.JU.JUEcommerce.entity.Order;
import com.JU.JUEcommerce.entity.Product;
import com.JU.JUEcommerce.entity.ProductCategory;
import com.JU.JUEcommerce.entity.State;



@Configuration // we're using this annotation as we want this class to be scanned
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	private EntityManager entityManager;
	

	@Value("${allowed.origins}")
	private String[] allowedOrigins;
	
	
	public MyDataRestConfig() {}
	
	// The configuration for MyDataRestConfig only applies to REST APIs provided by Spring Data REST. It does not apply to regular @RestControllers that we create manually.
	// As a result, for your CheckoutController we need to add CORS support, hence the need for MyAppConfig.java.
	
	
	@Autowired
	public MyDataRestConfig(EntityManager entityManager) { // inject the JPA entity manager
		
	this.entityManager = entityManager;
		
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		
	//	System.out.println("basepath: "+ this.basePath);
	//	System.out.println("allowedOrigins: "+ this.allowedOrigins[0]);
		
		
		
		HttpMethod[] theUnsuportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH}; // declare an array to set up a 
													// list of HTTP methods
		
		//disable HTTP methods for Product: PUT, POST and DELETE for this Version 1
        disableHttpMethods(Product.class,config, theUnsuportedActions);

        // disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH for this Version 1
        disableHttpMethods(ProductCategory.class,config, theUnsuportedActions);
        
        // disable HTTP methods for Country: PUT, POST, DELETE and PATCH for this Version 1
        disableHttpMethods(Country.class,config, theUnsuportedActions);
        
     // disable HTTP methods for State: PUT, POST, DELETE and PATCH for this Version 1
        disableHttpMethods(State.class,config, theUnsuportedActions);
        
     // disable HTTP methods for State: PUT, POST, DELETE and PATCH for this Version 1
        disableHttpMethods(Order.class,config, theUnsuportedActions);
        
        // call an internal helper method
        exposeIds(config);
        
     // set up cors mapping
     	cors.addMapping(config.getBasePath() + "/**").allowedOrigins(this.allowedOrigins);
        
	}


	private void disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsuportedActions) {
		config.getExposureConfiguration()
                .forDomainType(theClass) // to specif the class that you want to disble the end points above
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsuportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsuportedActions));
	}
	
	private void exposeIds(RepositoryRestConfiguration config) {
		
		// expose entity ids
		
		// Gets a collection of all entity classes from the entity manager
		Set<EntityType<?>> entities = this.entityManager.getMetamodel().getEntities();
		
		// Create an array of the entity types
		List<Class> entityClasses = new ArrayList<>();
		
		// get the entity types for the entities 
		for(EntityType tempEntityType : entities) {
			
			entityClasses.add(tempEntityType.getJavaType());
			
		}
		
		// expose the entity ids for the array of entity/domain types
		Class[] domainTypes = entityClasses.toArray(new Class[0]); // convert the ArrayList to an array of Class objects. We need: Class[]
		// By using new Class[0], we are telling the arraylist to convert to an array of Class[]. 
		config.exposeIdsFor(domainTypes);
	
		
		
	}
	
}
