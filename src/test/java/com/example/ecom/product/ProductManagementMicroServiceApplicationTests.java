package com.example.ecom.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecom.product.controller.ProductController;

@SpringBootTest
class ProductManagementMicroServiceApplicationTests {
	@Autowired
	private ProductController productController;
	
	
	@Test
	void contextLoads() {
		assertThat(productController).isNotNull();
	}

}
