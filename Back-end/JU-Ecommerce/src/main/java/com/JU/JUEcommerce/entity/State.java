package com.JU.JUEcommerce.entity;


import javax.persistence.Column;  
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity // to tell spring this is entity class that is mapped to database
@Table(name="state")  // using the same table name as we created in the database in order for this class to be mapped to
@Data // lombook
public class State {
	
	// define fields

		@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
		@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
		@Column(name="state_id") // to map the fields to the customer table columns and need to match exact column name in the table
		private int stateId;	
		
		@Column(name="name") 
		private String name;
		
		// Many states belongs to 1 country
		@ManyToOne()
		@JoinColumn(name="country_id")
		private Country country; //  referening to field name in Country class for forign key

}
