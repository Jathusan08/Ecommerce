package com.JU.JUEcommerce.dao;

import java.util.List;  

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.JU.JUEcommerce.entity.State;



//@CrossOrigin("http://localhost:4200") // - configured in the MyDataRestConfig
@RepositoryRestResource(collectionResourceRel = "states", path = "states")// adding customisation for this given repositiry
public interface StateRepository extends JpaRepository<State, Integer> {
	
	// I don't want all states to show up , just want to show states for a given country
	
	List<State> findByCountryCode(@Param("code") String code);

}
