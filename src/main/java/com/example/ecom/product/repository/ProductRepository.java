package com.example.ecom.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecom.product.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	public List<Product> findByCategoryId(Long categoryId);
	public Product findByNameAndUnit(String name, String unit);
	public List<Product> findByName(String name);
}
