package com.example.ecom.product.model;

import java.util.Objects;

public class ProductDTO {
	private Long id;
	private String name;
	private String unit;
	private Double price;
	private Long categoryId;
	public ProductDTO() {
	}
	
	public ProductDTO(Long id, String name, String unit, Double price, Long categoryId) {
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.price = price;
		this.categoryId = categoryId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnit() {
		return unit;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPrice() {
		return price;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO product = (ProductDTO) o;
        return id == product.id  && name.equals(product.name)&& unit.equals(product.unit)&&price == product.price
        		&& categoryId == product.categoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name,unit,price, categoryId);
    }
}
