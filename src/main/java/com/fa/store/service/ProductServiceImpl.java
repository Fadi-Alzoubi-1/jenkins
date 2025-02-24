package com.fa.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.store.entity.Product;
import com.fa.store.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	ProductRepository productRepo;

	ProductServiceImpl(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	@Override
	public Product getProductById(Long productId) {
		return productRepo.getById(productId);
	}

	@Override
	public List<Product> getProductByName(String prouctName) {
		return productRepo.getByProductName(prouctName);
	}

	@Override
	public Product addProduct(Product product) {
		return productRepo.save(product);
	}

	@Override
	public List<Product> listAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public void deleteProduct(Long productId) {
		productRepo.deleteById(productId);
	}

	@Override
	public Product updateProduct(Product product, Long productId) {

		Product productToUpdate = productRepo.getById(productId);
		if (!product.equals(productToUpdate)) {
			if (!productToUpdate.getProductName().equals(product.getProductName()))
				productToUpdate.setProductName(product.getProductName());
			if (!productToUpdate.getProductDescription().equals(product.getProductDescription()))
				productToUpdate.setProductDescription(product.getProductDescription());
			if (productToUpdate.getProductPrice() != product.getProductPrice())
				productToUpdate.setProductPrice(product.getProductPrice());
			if (productToUpdate.getProductDiscount() != product.getProductDiscount())
				productToUpdate.setProductDiscount(product.getProductDiscount());

			return productRepo.save(productToUpdate);
		}
		return null;
	}

}
