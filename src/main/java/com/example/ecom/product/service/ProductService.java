package com.example.ecom.product.service;

import java.util.List;

import com.example.ecom.product.model.ProductDTO;

public interface ProductService {
	public ProductDTO updateProduct(ProductDTO product);
	public ProductDTO getById(Long userId);
	public List<ProductDTO> getProducts();
	public ProductDTO createProduct(ProductDTO product);
	public Boolean deleteProduct(Long id);
	public ProductDTO getProduct(Long id);
	public List<ProductDTO> getByCategoryId(Long categoryId);
	public List<ProductDTO> getByCategoryName(String categoryName);
	public ProductDTO getByNameAndUnit(String name, String unit);
	public List<ProductDTO> getByName(String name);
}
