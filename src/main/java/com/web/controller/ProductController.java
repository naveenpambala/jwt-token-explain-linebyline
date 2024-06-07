package com.web.controller;

 

 

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.entity.ImageUpload;
import com.web.entity.Product;
import com.web.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@PostMapping(value={"/addNewProduct"}, consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Product addNewProduct(@RequestPart("product") Product product,
								@RequestPart("imageFile") MultipartFile [] file) {		
 		try {
		Set<ImageUpload> images=imageUpload(file);
		product.setProductImages(images);
	return productService.addNewProduct(product);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
			
		}
		
	}
	
	public Set<ImageUpload> imageUpload(MultipartFile[] multipartFiles) throws IOException{
		
		Set<ImageUpload> imageUploads= new HashSet<>();
		
		for(MultipartFile file : multipartFiles) {
			
			ImageUpload imageUpload = new ImageUpload(
					null, file.getOriginalFilename(),
					file.getName(),
					file.getBytes()
					
					);
			
			imageUploads.add(imageUpload);
		}
		return imageUploads;
		
	}
	
}