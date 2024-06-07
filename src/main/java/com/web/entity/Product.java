package com.web.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer productId;
	private String productName;
	private String productDescription;	 
	private double productDiscountPrize;
	private double productAcuallPrize;
 
	@ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL )
	@JoinTable(name="product_image",
	 joinColumns= {
			 @JoinColumn(name="product_id")
			 
	 },
	inverseJoinColumns= {
			@JoinColumn(name="image_id")
			
	}
	)
	private Set<ImageUpload> productImages;

}
