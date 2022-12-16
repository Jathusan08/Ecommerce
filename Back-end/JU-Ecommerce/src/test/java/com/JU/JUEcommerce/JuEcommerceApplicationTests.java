package com.JU.JUEcommerce;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.JU.JUEcommerce.dao.CountryRepository;
import com.JU.JUEcommerce.dao.CustomerRepository;
import com.JU.JUEcommerce.dao.OrderRepository;
import com.JU.JUEcommerce.dao.ProductCategoryRepository;
import com.JU.JUEcommerce.dao.ProductRepository;
import com.JU.JUEcommerce.dao.StateRepository;
import com.JU.JUEcommerce.dto.Purchase;
import com.JU.JUEcommerce.entity.Address;
import com.JU.JUEcommerce.entity.Country;
import com.JU.JUEcommerce.entity.Customer;
import com.JU.JUEcommerce.entity.Order;
import com.JU.JUEcommerce.entity.OrderItem;
import com.JU.JUEcommerce.entity.Product;
import com.JU.JUEcommerce.entity.ProductCategory;
import com.JU.JUEcommerce.entity.State;
import com.JU.JUEcommerce.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;



@TestPropertySource("/application-test.properties")// this will allow me to load properties during testing
@AutoConfigureMockMvc
@SpringBootTest(classes=JuEcommerceApplication.class)
class JuEcommerceApplicationTests {
	
	 
	 //inject the JPA Entity Manager
	 @PersistenceContext
	 private EntityManager entityManager;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objecMapper; // to do rest API test
	
	//public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON; // we'll use this applicatoin as JSON constant when we verify the actual JSON response
	
	private static final MediaType contentType = new MediaType("application", "hal+json");
	
	public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON; // we'll use this applicatoin as JSON constant when we verify the actual JSON response
	
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private ProductRepository productDao;
	
	@Autowired
	private CustomerRepository customerDao;
	
	@Autowired
	private CountryRepository countryDao;
	
	@Autowired
	private OrderRepository orderDao;	
	
	@Autowired
	private StateRepository stateDao;
	
	@Autowired
	private ProductCategoryRepository producCategorytDao;
	
	@Autowired
	private JdbcTemplate jdbc; // from springframework that will allow us to execute JDBC operations
							// JdbcTempl

	@Test
	void contextLoads() {
	}
	
	@BeforeEach()
	void setupDatabase() {
		
		// insert sample data- we'll make use of JDBC template and execute JDBC operations
		
		// set up category data
		this.jdbc.execute("insert into Product_Category(productCategory_id, category_name) values (1, 'SMART WATCHES')");
		this.jdbc.execute("insert into Product_Category(productCategory_id, category_name) values (2, 'CAMERAS')");
		this.jdbc.execute("insert into Product_Category(productCategory_id, category_name) values (3, 'COFEE MACHINES')");

	
		// insert sample products data for  SMART WATCHES Category
		this.jdbc.execute("insert into product(product_id, sku, name, description, image_url, active, units_in_stock, "
				+ "unit_price, productCategory_id, date_created) " + 
		"values (1,'SMARTWATCH-1000', 'Apple Watch Series 8 GPS 41mm Alu Case/Midnight Sport Band', "
		+ "'For the first time, Apple Watch Series 8 is able to detect if you are in a severe car crash', "
		+ "'assets/images/products/SMARTWATCH/SMARTWATCH-1000.png', 1, 100, 419.00, 1, NOW())");
		
		this.jdbc.execute("insert into product(product_id, sku, name, description, image_url, active, units_in_stock, "
				+ "unit_price, productCategory_id, date_created) " + 
		"values (2,'SMARTWATCH-1001', ' Huawei Smart Watch GT3 42mm - Black', "
		+ "'HUAWEI WATCH GT3 42mm is a smartwatch with 7-day battery life and 5 ATM water resistence.', "
		+ "'assets/images/products/SMARTWATCH/SMARTWATCH-1001.png', 1, 100, 159.99, 1, NOW())");
		
		
		// insert sample products data for  CAMERAS Category
		this.jdbc.execute("insert into product(product_id, sku, name, description, image_url, active, units_in_stock, "
				+ "unit_price, productCategory_id, date_created) " + 
		"values (3,'CAMERA-1000', 'Canon EOS 2000D 18-55MM Camera Kit', "
		+ "'Unleash your creativity with this easy to use DSLR.', "
		+ "'assets/images/products/CAMERA/CAMERA-1000.png', 1, 100, 419.99, 2, NOW())");
		
		this.jdbc.execute("insert into product(product_id, sku, name, description, image_url, active, units_in_stock, "
				+ "unit_price, productCategory_id, date_created) " + 
		"values (4,'CAMERA-1001', 'instax Mini 11 Instant Camera Bundle - Lilac Purple', "
		+ "'This instax camera bundle is exclusive to Argos and wonderfully designed to include the best-selling instax mini 11 cameras.', "
		+ "'assets/images/products/CAMERA/CAMERA-1001.png', 1, 100, 99.99, 2, NOW())");
		
		
		// insert sample products data for COFEE MACHINES Category
		this.jdbc.execute("insert into product(product_id, sku, name, description, image_url, active, units_in_stock, "
				+ "unit_price, productCategory_id, date_created) " + 
		"values (5,'COFFEEMACHINE-1000', ' Tassimo by Bosch Vivy 2 Pod Coffee Machine - Black', "
		+ "' The Bosch Tassimo Vivy black Coffee Machine offers fantastic value for money.', "
		+ "'assets/images/products/COFFEEMACHINE/COFFEEMACHINE-1000.png', 1, 100, 35.00, 3, NOW())");
		
		this.jdbc.execute("insert into product(product_id, sku, name, description, image_url, active, units_in_stock, "
				+ "unit_price, productCategory_id, date_created) " + 
		"values (6,'COFFEEMACHINE-1001', 'Cookworks CM5013B-GS Espresso Coffee Machine', "
		+ "' Brighten up your mornings with this Espresso Coffee Machine.', "
		+ "'assets/images/products/COFFEEMACHINE/COFFEEMACHINE-1001.png', 1, 100, 55.00, 3, NOW())");
		
		
		// insert sample data for customers
		this.jdbc.execute("insert into customer(customer_id, first_name, last_name, email) " + 
				"values (1,'Jathusan', 'Uthayakumaran', 'jath@hotmail.com')");
		
		this.jdbc.execute("insert into customer(customer_id, first_name, last_name, email) " + 
				"values (2,'Charlie', 'Steven', 'Charlie@hotmail.com')");
		
		
		// insert sample data for Country
		this.jdbc.execute("insert into country(country_id, code, name) values (1, 'UK', 'United Kingdom')");
		this.jdbc.execute("insert into country(country_id, code, name) values (2, 'BR', 'Brazil')");
		this.jdbc.execute("insert into country(country_id, code, name) values (3, 'CA', 'Canada')");
	
		
		// insert sample data for State -f or countryID 1 UNITED KINGDOM
		this.jdbc.execute("insert into state(state_id, name, country_id) values (1, 'London', 1)");
		this.jdbc.execute("insert into state(state_id, name, country_id) values (2, 'Kent', 1)");
		
		// insert sample data for State - for countryID 2 Brazil
		this.jdbc.execute("insert into state(state_id, name, country_id) values (3, 'Acre', 2)");
		this.jdbc.execute("insert into state(state_id, name, country_id) values (4, 'Bahia', 2)");
		
		// insert sample data for State - for countryID 3 Canada
		this.jdbc.execute("insert into state(state_id, name, country_id) values (5, 'Manitoba', 3)");
		this.jdbc.execute("insert into state(state_id, name, country_id) values (6, 'New Brunwick', 3)");
		
//		 insert sample data for Address - UK
		this.jdbc.execute("insert into address(address_id, city, country, state, street, post_code)"
				+ " values (1, 'Woolwich', 'United Kingdom', 'London', 'SAS', 'SE32 3SD')");
		
		this.jdbc.execute("insert into address(address_id, city, country, state, street, post_code)"
				+ " values (2, 'Oxford Circus', 'United Kingdom', 'Kent', 'DSA', 'SW75 5KL')");
		
		// DOUBLE CHECK THE ORDER AND ORDER ITEMS DATA SQL
		
		 // insert sample data for orders 
		this.jdbc.execute("insert into orders(orders_id, order_tracking_number, total_price, total_quantity, billing_address_id, customer_id, shipping_address_id, status, date_created, last_updated)"
				+ " values (1, 'JUECOMMERCE275002c7-d033-4956-9c1f-5d3198bb07e3', 159.99, 1, 1, 2, 2, NULL, NOW(), NOW())");
		
		 // insert sample data for order items
		this.jdbc.execute("insert into order_item(orderItem_id, image_url, quantity, unit_price, order_id, product_id)"
				+ " values (1, 'assets/images/products/SMARTWATCH/SMARTWATCH-1001.png', 1, 159.99, 1, 2)");
//		
	}
	

	
	@Test
	public void testForNumOfTotalProducts() {
		
       ArrayList<Product> productList = (ArrayList<Product>) this.productDao.findAll();
       
      
       // testing productList is not Null
       assertNotNull(productList,"Product List shouldn't be empty");
       
       // we have 6 product added total to the embedded H2 database
       
       assertEquals(6, productList.size(),"SHOULD HAVE 6 PRODUCTS");
		
	}
	
	
	
	@Test
	public void testForNumOfTotalCategories() {
		
       ArrayList<ProductCategory> productCaregoryList = (ArrayList<ProductCategory>) this.producCategorytDao.findAll();
       
  
       
       // testing productCaregoryList is not Null
       assertNotNull(productCaregoryList,"Product Category shouldn't be empty");
       
       // we have 3 product category added total to the embedded H2 database
       
       assertEquals(3, productCaregoryList.size(),"SHOULD HAVE 3 PRODUCT CATEGORIES");
		
	}
	
	@Test
	public void getProductQuantity() {
		
		Optional<Product> product = this.productDao.findById((long) 1);
		
		assertTrue(product.isPresent());
		
	
		
		List<Product> productQuantity =this.productService.getProductQuantity(product.get().getProductId());
		

		
		assertEquals(100,productQuantity.get(0).getUnitsInStock());
		
	}
	
	@Test
	public void UpdateProductQuantity() {
		
		int update = 0;
		long productId =1;
		Optional<Product> product = this.productDao.findById(productId);
		
		assertTrue(product.isPresent());
		
		// we should have 100 unit in stock for 1;
		assertEquals(100,product.get().getUnitsInStock());
		
		// in this example we're purchasing of 25 quantity for this item so we should deduct from the actual unit stoc
		
		update = (product.get().getUnitsInStock())- 25;
		
		//System.out.println("update : " + update )
		
		this.productDao.updateProductQuantity(update,productId);
		
		List<Product> productQuantity =this.productDao.getProductQuantity(productId);
		
		//	System.out.println("product Quantity " + productQuantity.get(0).getUnitsInStock());
			// it should be 75 now since deducted by -25
		assertEquals(75,productQuantity.get(0).getUnitsInStock());
		
	}
	
	
	
	@Test
	public void addNewProductTest() {
		
		// retrieve product category - goinf to add new smart watch to smart Watch category
		ProductCategory productCategory = this.producCategorytDao.getReferenceById((long)1);
		
		// adding new product to smart watch Category
		Product product = (Product) this.applicationContext.getBean("product");
		product.setProductId((long)7);
		product.setSku("SMARTWATCH-1004");
		product.setDescription("Meet Fitbit Versa 2—a water resistant smartwatch that elevates every moment.");
		product.setImageURL("assets/images/products/SMARTWATCH/SMARTWATCH-1004.png");
		product.setUnitsInStock(100);
		product.setName("Fitbit Versa 2 Smart Watch - Carbon Alu / Black Band");
		product.setActive(true);
		product.setProdcutCateogryID(productCategory);
		product.setUnitPrice(new BigDecimal("99.00"));
		
		
		
		

		
		// now save to the h2 database
		this.productDao.save(product);
		
		
		product.getProductId();
		product.getSku();
		product.getDescription();
		product.getImageURL();
		product.getUnitsInStock();
		product.getName();
		product.isActive();
		product.getProdcutCateogryID();
		product.getUnitPrice();
		product.getDateCreated();
		product.getLastUpdated();
	
		
		// test to see if saved
		assertNotNull(this.productDao.getReferenceById((long)7));
	}
	
	@Test
	public void addNewCategoryTest() {
		
		
		
		ProductCategory productCategory = (ProductCategory) this.applicationContext.getBean("productCategory");
		productCategory.setProductCategoryId((long) 4);
		productCategory.setCategoryName("SOFAS");
		this.producCategorytDao.save(productCategory);
		
		// now check if sofa category added successfully
		
		Optional<ProductCategory> retrieveProductCategory = this.producCategorytDao.findById((long) 4);
		
		assertTrue(retrieveProductCategory.isPresent());
		
		retrieveProductCategory.get().getCategoryName();
		retrieveProductCategory.get().getProductCategoryId();
		retrieveProductCategory.get().getProducts();
		
		System.out.println("Product Category new id" + retrieveProductCategory.get().getProductCategoryId());
		retrieveProductCategory.get().getCategoryName();
		
	}
	
	@Test
	public void InvalidCategoryIdTest() {
		
		// testing invalid category - we've don't have catagory id of '100'
		Optional<ProductCategory> retrieveProductCategory = this.producCategorytDao.findById((long) 100);
		
		assertFalse(retrieveProductCategory.isPresent(),"Category ID 100 doesnt exist");
		
	}
	
	@Test
	public void InvalidProductIdTest() {
		
		// testing invalid Product Id - we've don't have Product id of '50'
		Optional<Product> retrieveProduct = this.productDao.findById((long) 50);
		
		assertFalse(retrieveProduct.isPresent(),"Product ID 50 doesnt exist");
		
	}
	
	@Test
	public void testForNumOfTotalCustomers() {
		
       ArrayList<Customer> customerList = (ArrayList<Customer>) this.customerDao.findAll();
       
      
       // testing customerList is not Null
       assertNotNull(customerList,"Customer List shouldn't be empty");
       
       // we have 2 Customers added total to the embedded H2 database
       
       assertEquals(2, customerList.size(),"SHOULD HAVE 2 Customers");
		
	}
	
	@Test
	public void testForInvalidTotalCustomers() {
		
       ArrayList<Customer> customerList = (ArrayList<Customer>) this.customerDao.findAll();
       
       
       // we have 2 Customers added total to the embedded H2 database
       
       assertNotEquals(5, customerList.size(),"SHOULD HAVE BE 2 Customers");
		
	}
	
	
	@Test
	public void addNewCustomer() { // NEED TO DO MORE WORK
		
		
		
		// adding new customer
		Customer customer = (Customer) this.applicationContext.getBean("customer");
		customer.setCustomerId((long) 3);
		customer.setFirstName("Johhny");
		customer.setLastName("Bravo");
		customer.setEmail("OhRight!@hotmail.com");
		
		// now save to the h2 database
				this.customerDao.save(customer);
				
				customer.getCustomerId();
				customer.getFirstName();
				customer.getLastName();
				customer.getEmail();
				
				// test to see if saved
				assertNotNull(this.customerDao.getReferenceById((long)3));
					
	}
	
	@Test
	public void testForNumOfTotalCountries() {
		
       ArrayList<Country> countryList = (ArrayList<Country>) this.countryDao.findAll();
       
      
       // testing countryList is not Null
       assertNotNull(countryList,"Country List shouldn't be empty");
       
       // we have 3 countries added total to the embedded H2 database
       
       assertEquals(3, countryList.size(),"SHOULD HAVE 3 COUNTRIES");
		
	}
	
	@Test
	public void addNewCountryTest() { // need to do more work
		
		// retrieve product category
		
		// we don't have country id with 4
		Optional<Country> country = this.countryDao.findById(4);
		
		
		assertFalse(country.isPresent());
		
		// adding new Country to smart watch Category
		Country newCountry = (Country) this.applicationContext.getBean("country");
		newCountry.setCountryId(4);
		newCountry.setCode("DE");
		newCountry.setName("Germany");
		

		// now save to the h2 database
		this.countryDao.save(newCountry);
		
		newCountry.getCountryId();
		newCountry.getCode();
		newCountry.getName();
		newCountry.getStates();
		
		
		// test to see if saved
		
		country = this.countryDao.findById(4);
		assertTrue(country.isPresent());
	}
	
	@Test
	public void testForNumOfTotalStates() {
		
       ArrayList<State> stateList = (ArrayList<State>) this.stateDao.findAll();
       
      
       // testing stateList is not Null
       assertNotNull(stateList,"State List shouldn't be empty");
       
       // we have 6 staes added total to the embedded H2 database
       
       assertEquals(6, stateList.size(),"SHOULD HAVE 6 STATES");
		
	}
	
	@Test
	public void addNewStateTest() {
		
		// retrieve Country we're going to add a new state to UK
		Country country = this.countryDao.getReferenceById(1);
		
		// adding new state to the uk
		State newState = (State) this.applicationContext.getBean("state");
		
		
		newState.setStateId(7);
		newState.setCountry(country);
		newState.setName("Luton");
		

		// now save to the h2 database
		this.stateDao.save(newState);
		
		
		newState.getStateId();
		newState.getName();
		
		newState.getCountry();
		
		
		// test to see if saved
		assertNotNull(this.stateDao.getReferenceById(7));
		
		// now there should be 7 states
		assertEquals(7,this.stateDao.findAll().size());
	}
	

	
	// https://github.com/json-path/JsonPath
	
	// testing endpoints for Main
	
	@Test
	 void getJUEcommerceMainRestAPI() throws Exception {
						
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType));
							
	}
	
	// testing endpoints for Product
	
	@Test
	 void getProductEndpoint() throws Exception {
		
	    ArrayList<Product> productList = (ArrayList<Product>) this.productDao.findAll();
	       
				
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/products"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$._embedded.products.length()", is(productList.size())));

							
	}
	
	@Test
	 void getProductsByAProductCategoryId() throws Exception {
		
		long categoryId =3;		
		
				Optional<ProductCategory> productCategory = this.producCategorytDao.findById(categoryId);
				
				assertTrue(productCategory.isPresent(),"ProductCategory ID 10 does exist");
				
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/products/search/findByProdcutCateogryIDProductCategoryId?id={categoryId}", categoryId))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$._embedded.products.length()", is(productCategory.get().getProducts().size())));

							
	}
	
	
	
	@Test
	 void searchByProductNameOrByKeywordEndpoint() throws Exception {
				
		String key = "coff";
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/products/search/findByNameContaining?name={key}", key))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType));
					
							
	}
	
	
	@Test
	 void addProductEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/JUEcommerce-api/products"))
					.andExpect(status().is4xxClientError());
				
							
	}
	
	@Test
	 void searchAProductByAnIdEndpoint() throws Exception {
		
		long productId =4;		
		
		Optional<Product> product = this.productDao.findById(productId);
		
		
		assertTrue(product.isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/products/{id}",productId))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$.productId", is(product.get().getProductId().intValue())))
					.andExpect(jsonPath("$.sku", is(product.get().getSku())))
					.andExpect(jsonPath("$.name", is(product.get().getName())));

	}
	
	@Test
	 void searchAProductByAnInvalidIdEndpoint() throws Exception {
		
		long productId =100;		
		
		Optional<Product> product = this.productDao.findById(productId);
		
		assertFalse(product.isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/products/{id}",productId))
					.andExpect(status().is4xxClientError());
					
							
	}
	
	@Test
	 void updateAProductEndpoint() throws Exception {
		
		long productId =2;		
		
		Optional<Product> product = this.productDao.findById(productId);
		
		assertTrue(product.isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/JUEcommerce-api/products/{id}",productId))
					.andExpect(status().is4xxClientError());
					
							
	}
	
	@Test
	 void deleteAProductEndpoint() throws Exception {
		
		long productId =3;		
		
		Optional<Product> product = this.productDao.findById(productId);
		
		assertTrue(product.isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/JUEcommerce-api/products/{id}",productId))
					.andExpect(status().is4xxClientError());
					
							
	}

	// testing endpoints for Product Category
	
	@Test
	 void getProductCategoryEndpoint() throws Exception {
		
		  ArrayList<ProductCategory> productCaregoryList = (ArrayList<ProductCategory>) this.producCategorytDao.findAll();
				
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/product-category"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$._embedded.ProductCategory.length()", is(productCaregoryList.size())));
							
	}
	
	@Test
	 void getProductCategorByValidIdEndpoint() throws Exception {
		
		long categoryId = 2;
		
		Optional <ProductCategory> productCategory = this.producCategorytDao.findById(categoryId);
				
		// test if the id exist
		
		assertTrue(productCategory.isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/product-category/{id}",categoryId))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$.productCategoryId", is((productCategory.get().getProductCategoryId()).intValue())));
				
							
	}
	
	@Test
	 void getProductCategorByInvalidIdEndpoint() throws Exception {
		
		long categoryId = 55;
		
		Optional <ProductCategory> productCategory = this.producCategorytDao.findById(categoryId);
				
		// test if the id exist
		
		assertFalse(productCategory.isPresent());
				
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/product-category/{id}",categoryId))
						.andExpect(status().is4xxClientError());
							
	}
	
	@Test
	 void addProductCategoryEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/JUEcommerce-api/product-category"))
					.andExpect(status().is4xxClientError());
							
	}
	
	@Test
	 void updateProductCategoryEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/JUEcommerce-api/product-category"))
					.andExpect(status().is4xxClientError());
					//.andExpect(jsonPath("$", aMapWithSize(6)));
							
	}
	
	@Test
	 void deleteProductCategoryEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/JUEcommerce-api/product-category"))
					.andExpect(status().is4xxClientError());
					//.andExpect(jsonPath("$", aMapWithSize(6)));
							
	}
	
	// country endpoint
	
	@Test
	 void getCountryEndpoint() throws Exception {
				
		   ArrayList<Country> countryList = (ArrayList<Country>) this.countryDao.findAll();
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/countries"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$._embedded.countries.length()", is(countryList.size())));
					//.andExpect(jsonPath("$", aMapWithSize(6)));
							
	}
	
	
	@Test
	 void getCountrybyValidIdEndpoint() throws Exception {
		
		Optional<Country> country = this.countryDao.findById(3);
				
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/countries/{id}",country.get().getCountryId()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$.countryId", is(country.get().getCountryId())))
					.andExpect(jsonPath("$.code", is(country.get().getCode())))
					.andExpect(jsonPath("$.name", is(country.get().getName())));
							
	}
	
	@Test
	 void getCountrybyInValidIdEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/countries/{id}",67))
					.andExpect(status().is4xxClientError());
							
	}
	
	@Test
	 void addCountryEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/JUEcommerce-api/countries"))
					.andExpect(status().is4xxClientError());
					//.andExpect(jsonPath("$", aMapWithSize(6)));
							
	}
	
	@Test
	 void updateCountryEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/JUEcommerce-api/countries"))
					.andExpect(status().is4xxClientError());
					//.andExpect(jsonPath("$", aMapWithSize(6)));
							
	}


	@Test
	 void deleteCountryEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/JUEcommerce-api/countries"))
					.andExpect(status().is4xxClientError());
					//.andExpect(jsonPath("$", aMapWithSize(6)));
							
	}
	
	// State endpoint
	
	@Test
	 void getStateEndpoint() throws Exception {
		
		ArrayList<State> stateList = (ArrayList<State>) this.stateDao.findAll();
				
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/states"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$._embedded.states.length()", is(stateList.size())));
			
							
	}
	
	
	@Test
	 void getStatebyValidIdEndpoint() throws Exception {
				
		int stateId =2;		
		
		Optional<State> state = this.stateDao.findById(2);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/states/{id}",stateId))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$.stateId", is(state.get().getStateId())))
					.andExpect(jsonPath("$.name", is(state.get().getName())));
					
	}
	
	@Test
	 void getStatebyInValidIdEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/states/{id}",23))
					.andExpect(status().is4xxClientError());
							
	}
	
	@Test
	 void addStateEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/JUEcommerce-api/states"))
					.andExpect(status().is4xxClientError());
					//.andExpect(jsonPath("$", aMapWithSize(6)));
							
	}
	
	@Test
	 void updateStateEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/JUEcommerce-api/states"))
					.andExpect(status().is4xxClientError());
					//.andExpect(jsonPath("$", aMapWithSize(6)));
							
	}


	@Test
	 void deleteStateEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/JUEcommerce-api/states"))
					.andExpect(status().is4xxClientError());
					//.andExpect(jsonPath("$", aMapWithSize(6)));
							
	}
	
	
	@Test
	 void findStatebyCountryCodeEndpoint() throws Exception {
		
		String countryCode = "UK";
		
		Optional<Country> country =this.countryDao.findById(1);
		
		List<State> state =this.countryDao.findById(1).get().getStates();
		
		// check if it exist
		assertNotNull(country.isPresent());
		
		// test to see if it eqauls to our country code
		assertEquals(countryCode, country.get().getCode());
		// check country code exist
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/JUEcommerce-api/states/search/findByCountryCode?code={code}",countryCode))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andExpect(jsonPath("$._embedded.states.length()", is(state.size())));
				
							
	}
	
	@Test
	 void getOrderEndpoint() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/JUEcommerce-api/orders"))
					.andExpect(status().is4xxClientError());
				
							
	}
	
	@Test
	 void testPurchaseEndpoint() throws Exception {
		
		// lets test with an existing gucstomer
		
		Optional<Customer> customer = this.customerDao.findById((long) 1);
		
		assertTrue(customer.isPresent());
		
		
		// we only 1 order so now in our he databse
		
				assertEquals(1, this.orderDao.findAll().size());

		// define sample data for billing Address
		Address billingAddress = (Address) this.applicationContext.getBean("address");
		billingAddress.setStreet("Woolwich");
		billingAddress.setCity("London");
		billingAddress.setState("England");
		billingAddress.setCountry("United Kingdom");
		billingAddress.setCountry("SE29 3FG");
		
		billingAddress.getStreet();
		billingAddress.getCity();
		billingAddress.getState();
		billingAddress.getCountry();
		billingAddress.getCountry();
		
		
		// define sample data for shipping  Address
		Address shippingAddress = (Address) this.applicationContext.getBean("address");
		shippingAddress.setStreet("Woolwich");
		shippingAddress.setCity("London");
		shippingAddress.setState("England");
		shippingAddress.setCountry("United Kingdom");
		shippingAddress.setCountry("SE29 3FG");
		
		// add some orders in arraylist
		ArrayList<Product>  orderList = new ArrayList<Product>();
		
		orderList.add(this.productDao.getReferenceById((long) 1));
		orderList.add(this.productDao.getReferenceById((long) 2));
		
		double totalPrice = ((orderList.get(0).getUnitPrice().doubleValue()) +( orderList.get(1).getUnitPrice().doubleValue())) ;
		

		
		
		Order order = (Order) this.applicationContext.getBean("order");
		order.setTotalPrice(BigDecimal.valueOf(totalPrice));
		order.setTotalQuantity(orderList.size());
		
		
		// setting array ORDERItem
		
		OrderItem [] orderItems = new OrderItem[orderList.size()];
		
		OrderItem orderItem = (OrderItem) this.applicationContext.getBean("orderItem");
		orderItem.setImageUrl(orderList.get(0).getImageURL());
		orderItem.setQuantity(1);
		orderItem.setUnitPrice(orderList.get(0).getUnitPrice());
		orderItem.setProductId(orderList.get(0).getProductId());
		
		orderItems[0] = orderItem;
		
		orderItem = (OrderItem) this.applicationContext.getBean("orderItem");
		orderItem.setImageUrl(orderList.get(1).getImageURL());
		orderItem.setQuantity(1);
		orderItem.setUnitPrice(orderList.get(1).getUnitPrice());
		orderItem.setProductId(orderList.get(1).getProductId());
		
		orderItems[1] = orderItem;
		
		
		Purchase purchase = (Purchase) this.applicationContext.getBean("purchase");
		purchase.setCustomer(customer.get());
		purchase.setShippingAddress(shippingAddress);
		purchase.setBillingAddress(billingAddress);
		purchase.setOrder(order);
		
		Set<OrderItem> OrderItemSets = new HashSet<>(Arrays.asList(orderItems));
		
		purchase.setOrderItems(OrderItemSets);
		
		this.mockMvc.perform(post("http://localhost:8080/JUEcommerce-api/checkout/purchase")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objecMapper.writeValueAsString(purchase)))
				.andExpect(status().isOk());
		
		
		orderItem.getImageUrl();
		orderItem.getQuantity();
		orderItem.getUnitPrice();
		orderItem.getProductId();
		orderItem.getOrder();
		orderItem.getOrderItemId();
		
		purchase.getBillingAddress();
		purchase.getCustomer();
		purchase.getOrder();
		purchase.getOrderItems();	
		purchase.getShippingAddress();
				
		
		order.getTotalPrice();
		order.getTotalQuantity();
		
		
		// we have already 1 order so now let check if there 2 order as we added new order 1 
		
		assertEquals(2, this.orderDao.findAll().size());
							
	}
	

	
/*
 * 
 *  END Point To work on
 * 
 * 			http://localhost:8080/JUEcommerce-api/checkout/purchase
 * 
 */
	
	@AfterEach
	public void setupAfterTransaction() { 
		// delete the sample data after each test so clean uo
		jdbc.execute("DELETE FROM order_item");
		jdbc.execute("DELETE FROM orders");

		jdbc.execute("DELETE FROM product");
		jdbc.execute("DELETE FROM Product_Category");
		
		jdbc.execute("DELETE FROM customer");
		jdbc.execute("DELETE FROM state");
		jdbc.execute("DELETE FROM country");
		jdbc.execute("DELETE FROM address");
		
		
	}

}
