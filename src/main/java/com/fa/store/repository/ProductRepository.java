package com.fa.store.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.store.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	 @Query(value = "SELECT * FROM Product WHERE product_name = ?1", nativeQuery = true)
	  List<Product> getByProductName(String productName);
	
}
