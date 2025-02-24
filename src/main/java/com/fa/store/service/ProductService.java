package com.fa.store.service;

import java.util.List;

import com.fa.store.entity.Product;

public interface ProductService {
	Product addProduct(Product product);
	List<Product> listAllProducts();
	Product getProductById(Long productId);
	List<Product> getProductByName(String prouctName);
	Product updateProduct(Product product, Long productId);	
	void deleteProduct(Long productId);
}
