package com.example.ecom.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.product.exception.DuplicateEntryException;
import com.example.ecom.product.exception.ProductNotfoundException;
import com.example.ecom.product.model.ProductDTO;
import com.example.ecom.product.service.ProductService;

@RestController
public class ProductController {
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public ResponseEntity<List<ProductDTO>> getProducts(){
		List<ProductDTO> products = productService.getProducts();
		if(products==null) {
			logger.info("No products exist in the system.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
		ProductDTO product = productService.getProduct(id);
		if(product==null) {
			String message = String.format("Product doesn't exist with id :%s",id); 
			logger.error(message);
			throw new ProductNotfoundException(message);
		}
		return new ResponseEntity<>(product,HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product){
		ProductDTO existingProduct= productService.getByNameAndUnit(product.getName(),product.getUnit());
		if(existingProduct!=null) {
			logger.error("Product already exist!");
			throw new DuplicateEntryException("Product already exist!");
		}
		ProductDTO createdProduct = productService.createProduct(product);
		if(createdProduct==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(createdProduct,HttpStatus.OK);
	} 
	
	@PutMapping("/")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO product){
		ProductDTO udatedProduct = productService.updateProduct(product);
		if(udatedProduct==null || !udatedProduct.equals(product)) {
			logger.error("Product updating failed!");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(udatedProduct,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id){
		Boolean isDeleted = productService.deleteProduct(id);
		return new ResponseEntity<>(isDeleted,HttpStatus.OK);
	}
	
	@GetMapping("/category/{name}")
	public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String name){
		List<ProductDTO> products = productService.getByCategoryName(name);
		if(products==null) {
			String message = String.format("Product doesn't exist for product :%s",name);
			throw new ProductNotfoundException(message);
		}
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	@GetMapping("/name/{name}")
	public ResponseEntity<List<ProductDTO>> getByName(@PathVariable String name){
		List<ProductDTO> products = productService.getByName(name);
		if(products==null) {
			logger.info("No products exist in the system.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(products,HttpStatus.OK);
	}

}
