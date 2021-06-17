package com.example.ecom.product.model;

import java.util.Objects;

public class CategoryDTO {
	private Long id;
	private String name;
	public CategoryDTO() {
	}
	
	public CategoryDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO category = (CategoryDTO) o;
        return id == category.id  && name == category.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
