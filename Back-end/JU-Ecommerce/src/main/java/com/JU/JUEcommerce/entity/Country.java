package com.JU.JUEcommerce.entity;

import java.util.List;  


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity // to tell spring this is entity class that is mapped to database
@Table(name="country")  // 
@Getter
@Setter
public class Country {
	
	// define fields

	@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
	@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
	@Column(name="country_id") // to map the fields to the customer table columns and need to match exact column name in the table
	private int countryId;	
	
	@Column(name="code") 
	private String code;
	
	@Column(name="name") 
	private String name;
	
	// 1 country has many states
	@OneToMany(mappedBy="country")
	@JsonIgnore// this will ignore the states
	private List<State> states;

}
