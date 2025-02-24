package com.fa.store.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.bridge.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fa.store.entity.Product;
import com.fa.store.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
@CrossOrigin
@RestController
public class StoreController {
	Logger logger = LoggerFactory.getLogger(StoreController.class);

	@Autowired
	private ProductService productService;
//	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/allProducts")
	public List<Product> listAllProducts() {
		List<Product> lstProduct = productService.listAllProducts();
		logger.info("Fetch all products: " + lstProduct);
		return lstProduct;
	}
	
	@GetMapping(value="/allJsonProducts", produces="Application/JSON")
	public List<Product> listJsonAllProducts() {
		List<Product> lstProduct = productService.listAllProducts();
		logger.info("Fetch all products: " + lstProduct);
		return lstProduct;
	}

	@PostMapping("/addProduct")
	public Product addProduct(@RequestBody Product product) {
		logger.info("Add product: "+ product.toString());
		return productService.addProduct(product);
	}
	
 
	@GetMapping(value = "/getProduct/{id}", produces="Application/JSON")
	public  Map getProductById(@PathVariable Long id) {
		Product product1 = new Product();
		Product product = productService.getProductById(id);
		logger.info(this.getClass().getMethods() + "product: id: " + product);
		Map p = new HashMap();
		p.put("productId", product.getProductId());
		p.put("productName", product.getProductName());
		p.put("productPrice", product.getProductPrice());
		p.put("productDescription", product.getProductDescription());
		p.put("productDiscount", product.getProductDiscount());
		return p;
	}
	@GetMapping("/getProduct")
	public List<Product> getProductByName(@RequestParam String productName) {
		List<Product> products = productService.getProductByName(productName);
		logger.info("Fetch product name: " + products);
		return products;
	}

	@PutMapping("/updateProduct")
	public Product updateProduct(@RequestBody Product product) {
		Product updatedProduct = productService.updateProduct(product, product.getProductId());
		logger.info(this.getClass().getMethods()+ "Update product: from: " + product + "\n To product: " + updatedProduct);
		return updatedProduct;
	}
	
	@DeleteMapping("/deleteid/{id}")
	public String deleteByID(@PathVariable Long id) {
		

	productService.deleteProduct(id);
		return "Product Id: " + id + " Successfuly deleted.";

	}

}
