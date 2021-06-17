package com.example.ecom.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ecom.product.entity.Category;
import com.example.ecom.product.entity.Product;
import com.example.ecom.product.model.CategoryDTO;
import com.example.ecom.product.model.ProductDTO;
import com.example.ecom.product.repository.CategoryRepository;
import com.example.ecom.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
	@Mock
	private ProductRepository productRepository;
	@Mock
	private CategoryRepository categoryRepository;
	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	
	private static ProductDTO product;
	private static ProductDTO product1;
	private static ProductDTO product2;
	
	private static CategoryDTO category;
	private static CategoryDTO category1;
	
	
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
		product2.setCategoryId(2002L);
		
		category = new CategoryDTO();
		category.setId(1000L);
		category.setName("Fruits");
		
		category1 = new CategoryDTO();
		category1.setId(1001L);
		category1.setName("Groceries");
	}
	
	@Test
	@DisplayName("Test Create Product")
	void testCreateProduct() {
		when(productRepository.saveAndFlush(any(Product.class))).thenAnswer(i -> {
			Product product = i.getArgument(0);
			product.setId(2001L);
			return product;
		});
		
		ProductDTO savedProduct = productServiceImpl.createProduct(product);
		
		assertEquals(2001L, savedProduct.getId());
	}
	
	@Test
	@DisplayName("Update Product Test")
	void testUpdateProduct() {
		when(productRepository.saveAndFlush(any(Product.class))).thenAnswer(i -> {
			Product product = i.getArgument(0);
			product.setId(2001L);
			product.setPrice(100.00);
			return product;
		});
		ProductDTO updatedProduct = productServiceImpl.updateProduct(product);
		assertEquals(100.00, updatedProduct.getPrice());
	}
	@Test
	@DisplayName("Delete Product Test")
	void testDeleteProduct() {
		
		Boolean isDeleted = productServiceImpl.deleteProduct(2001L);
		assertTrue(isDeleted);
	    verify(productRepository, times(1)).deleteById(2001L);
	}
	@Test
	@DisplayName("Test getProducts")
	void testgetProducts() {
		List<ProductDTO> products =  productServiceImpl.getProducts();
		assertNotNull(products);
	}
	@Test
	@DisplayName("Test getById")
	void testgetById() {
		ProductDTO productDTO =  productServiceImpl.getById(2001L);
		assertNotNull(productDTO);
	}
	@Test
	@DisplayName("Test getProduct")
	void testgetProduct() {
		ProductDTO productDTO =  productServiceImpl.getProduct(2001L);
		assertNotNull(productDTO);
	}
	@Test
	@DisplayName("Test getByCategoryId")
	void testGetByCategoryId() {
		List<ProductDTO> products =  productServiceImpl.getByCategoryId(1001L);
		assertNotNull(products);
	}
	
	@Test
	@DisplayName("getByNameAndUnit Test")
	void testGetByNameAndUnit() {
		assertNotNull(productServiceImpl.getByNameAndUnit("Apple","1Kg"));
	}
	@Test
	@DisplayName("getCategoryDTO Test")
	void testgetCategoryDTO() {
		assertNotNull(productServiceImpl.getCategoryDTO(new Category(category1.getId(),category1.getName())));
	}
	@Test
	@DisplayName("getByCategoryName Test")
	void testgetByCategoryName() {
		assertNotNull(productServiceImpl.getByCategoryName("Groceries"));
	}

}
