package com.example.ecom.product.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.example.ecom.product.exception.ProductNotfoundException;
import com.example.ecom.product.model.CategoryDTO;
import com.example.ecom.product.model.ProductDTO;
import com.example.ecom.product.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Mock
	private ProductServiceImpl productServiceImpl;
	@InjectMocks
	private ProductController productController;
	
	private static ProductDTO product;
	private static ProductDTO product1;
	private static ProductDTO product2;
	
	private static CategoryDTO category;
	private static CategoryDTO category1;
	
	private static List<ProductDTO> products;
	
	
	@BeforeAll
	public static void setUp() {
		product = new ProductDTO();
		product.setId(2001L);
		product.setName("Apple");
		product.setPrice(100.00);
		product.setUnit("1Kg");
		product.setCategoryId(1000L);
		
		product1 = new ProductDTO();
		product1.setId(2002L);
		product1.setName("Banana");
		product1.setPrice(30.00);
		product1.setUnit("1Kg");
		product1.setCategoryId(1000L);
		
		product2 = new ProductDTO();
		product2.setId(2003L);
		product2.setName("Rice");
		product2.setPrice(55.00);
		product2.setUnit("1Kg");
		product2.setCategoryId(1001L);
		
		category = new CategoryDTO();
		category.setId(1000L);
		category.setName("Fruits");
		
		category1 = new CategoryDTO();
		category1.setId(1001L);
		category1.setName("Groceries");
		
		products = new ArrayList<>();
		products.add(product);
		products.add(product1);
		products.add(product2);
	}
	
	@Test
	@DisplayName("Get all Products test")
	void testGetAllProducts() {
		when(productServiceImpl.getProducts()).thenAnswer(i -> {
			if(products==null)
				return null;
			return products;
		});
		
		List<ProductDTO> persistedProducts = productController.getProducts().getBody();
		if(persistedProducts==null) {
			logger.info("No Products exist in the system.");
		}
		verify(productServiceImpl).getProducts();
		assertNotNull(persistedProducts);
		assertEquals(products, persistedProducts);
	}
	
	@Test
	@DisplayName("Get no Products test")
	void testGetNoProducts() {
		when(productServiceImpl.getProducts()).thenReturn(null);
		
		List<ProductDTO> persistedProducts = productController.getProducts().getBody();
		assertEquals(null,persistedProducts);
	}
	
	@Test
	@DisplayName("Get Product by id test")
	void testGetProductById() {
		when(productServiceImpl.getProduct(any(Long.class))).thenAnswer(i -> {
			Long productId = i.getArgument(0);
			product.setId(productId);
			return product;
		});
		ProductDTO persistedProduct = productController.getProduct(2001L).getBody();
		assertNotNull(persistedProduct);
		assertEquals(product, persistedProduct);
		assertThat(persistedProduct).isNotNull();
	}
	@Test
	@DisplayName("Get no Product by id test")
	void testGetNoOrderById() {
		when(productServiceImpl.getProduct(any(Long.class))).thenAnswer(i -> {
			return null;
		});
		assertThrows(ProductNotfoundException.class, ()->productController.getProduct(2001L));
	}
	@Test
	@DisplayName("Get User by id: Negative Scenario")
	void testGetUserByIdNotFound() {
		//context
		when(productServiceImpl.getProduct(5L)).thenThrow(ProductNotfoundException.class);
		
		//event
		//outcome
		assertThrows(ProductNotfoundException.class, ()->productServiceImpl.getProduct(5L));
	}
	
	@Test
	@DisplayName("Update product test")
	void testUpdateProduct() {
		when(productServiceImpl.updateProduct(any(ProductDTO.class))).thenAnswer(i -> {
			ProductDTO product = i.getArgument(0);
			product.setId(3001L);
			product.setName("Test");
			return product;
		});
		
		ProductDTO persistedProduct = productController.updateProduct(product).getBody();
		
		assertEquals("Test", persistedProduct.getName());
	}
	@Test
	@DisplayName("Update no product test")
	void testUpdateNoProduct() {
		when(productServiceImpl.updateProduct(any(ProductDTO.class))).thenAnswer(i -> {
			return null;
		});
		
		assertEquals(HttpStatus.NO_CONTENT,productController.updateProduct(product).getStatusCode());
	} 
	
	
	@Test
	@DisplayName("Save Product test")
	void testSaveUser() {
		when(productServiceImpl.createProduct(any(ProductDTO.class))).thenAnswer(i -> {
			ProductDTO product = i.getArgument(0);
			product.setId(30011L);
			return product;
		});
		
		ProductDTO persistedProduct = productController.createProduct(product).getBody();
		
		assertEquals(product, persistedProduct);
	}
	@Test
	@DisplayName("Product not saved test")
	void testSaveNoProductr() {
		when(productServiceImpl.createProduct(any(ProductDTO.class))).thenAnswer(i -> {
			return null;
		});
		
		assertEquals(HttpStatus.NO_CONTENT,productController.createProduct(product).getStatusCode());
	}
	@Test
	@DisplayName("delete Product by id test")
	void testdeleteUser() {
		when(productServiceImpl.deleteProduct(any(Long.class))).thenReturn(true);
		
		boolean isDeleted = productController.deleteProduct(2001L).getBody();
		assertTrue(isDeleted);
	}
	@Test
	@DisplayName("Get Product by category test")
	void testgetProductsByCategory() {
		List<ProductDTO> products = new ArrayList<>();
		products.add(product);
		products.add(product1);
		when(productServiceImpl.getByCategoryName(any(String.class))).thenAnswer(i -> {
			String categoryName = i.getArgument(0);
			
			return products;
		});
		
		List<ProductDTO> persistedProduct = productController.getProductsByCategory("Fruits").getBody();
		assertNotNull(persistedProduct);
		assertEquals(products, persistedProduct);
	}
	@Test
	@DisplayName("Get no Product by category test")
	void testGetNoOrderByCategory() {
		when(productServiceImpl.getByCategoryName(any(String.class))).thenAnswer(i -> {
			return null;
		});
		assertThrows(ProductNotfoundException.class, ()->productController.getProductsByCategory("Fruits"));
	}
}
