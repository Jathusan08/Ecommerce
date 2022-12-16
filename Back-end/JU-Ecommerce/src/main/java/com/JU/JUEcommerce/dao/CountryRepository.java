package com.JU.JUEcommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.JU.JUEcommerce.entity.Country;




//@CrossOrigin("http://localhost:4200") //- configured in the MyDataRestConfig
@RepositoryRestResource(collectionResourceRel = "countries", path = "countries")// adding customisation for this given repositiry
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
