package com.example.ecom.product.exception;

public class ProductNotfoundException extends RuntimeException {
	   private static final long serialVersionUID = 1L;
	   public ProductNotfoundException(String message) {
			super(message);
	   }
	   
}