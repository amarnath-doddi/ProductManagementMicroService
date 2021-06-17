package com.example.ecom.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecom.product.entity.Category;
import com.example.ecom.product.entity.Product;
import com.example.ecom.product.model.CategoryDTO;
import com.example.ecom.product.model.ProductDTO;
import com.example.ecom.product.repository.CategoryRepository;
import com.example.ecom.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public ProductDTO updateProduct(ProductDTO product) {
		return geProductDTO(productRepository.saveAndFlush(getProduct(product)));
	}
	@Override
	public ProductDTO getById(Long id) {
		return geProductDTO(productRepository.findById(id).orElse(new Product()));
	}
	@Override
	public List<ProductDTO> getProducts() {
		return productRepository.findAll().stream().map(bf -> geProductDTO(bf)).collect(Collectors.toList());
	}
	@Override
	public Boolean deleteProduct(Long id) {
		productRepository.deleteById(id);
		 return true;
	}
	@Override
	public ProductDTO getProduct(Long id) {
		return geProductDTO(productRepository.findById(id).orElse(new Product()));
	}
	@Override
	public List<ProductDTO> getByCategoryId(Long categoryId) {
		return productRepository.findByCategoryId(categoryId).stream().map(bf -> geProductDTO(bf)).collect(Collectors.toList());
	}
	@Override
	public ProductDTO createProduct(ProductDTO product) {
		return geProductDTO(productRepository.saveAndFlush(getProduct(product)));
	}
	@Override
	public ProductDTO getByNameAndUnit(String name, String unit) {
		return geProductDTO(productRepository.findByNameAndUnit(name,unit));
	}
	@Override
	public List<ProductDTO> getByCategoryName(String categoryName) {
		CategoryDTO category = getCategoryDTO(categoryRepository.findByName(categoryName));
		return getByCategoryId(category.getId());
	}
	
	CategoryDTO getCategoryDTO(Category category) {
		category = Optional.ofNullable(category).orElse(new Category());
		return new CategoryDTO(category.getId(),category.getName());
	}

	private ProductDTO geProductDTO(Product product) {
		product = Optional.ofNullable(product).orElse(new Product());
		return new ProductDTO(product.getId(), product.getName(), product.getUnit(), product.getPrice(), product.getCategoryId());
	}
	
	private Product getProduct(ProductDTO productDto) {
		Product product = new Product();
		product.setId(productDto.getId());
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		product.setUnit(productDto.getUnit());
		product.setCategoryId(productDto.getCategoryId());
		return product;
	}
	@Override
	public List<ProductDTO> getByName(String name) {
		return productRepository.findByName(name).stream().map(bf -> geProductDTO(bf)).collect(Collectors.toList());
	}
}
