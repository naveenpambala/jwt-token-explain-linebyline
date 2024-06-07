package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.entity.Product;
import com.web.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	
	public Product addNewProduct(Product product) {
		return productRepo.save(product);
		
		
	}

}
