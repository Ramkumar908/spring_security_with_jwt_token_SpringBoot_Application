package com.smart.web.product.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HomeProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	private String productName;
	private String productPriece;
	private String productImage;
	private String productCategory;
	
	
	public HomeProduct() {
		
	}
	
	public HomeProduct(long productId, String productName, String productPriece, String productImage,
			String productCategory) {
		this.productId = productId;
		this.productName = productName;
		this.productPriece = productPriece;
		this.productImage = productImage;
		this.productCategory = productCategory;
	}

	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductPriece() {
		return productPriece;
	}
	public void setProductPriece(String productPriece) {
		this.productPriece = productPriece;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Override
	public String toString() {
		return "HomeProduct [productId=" + productId + ", productName=" + productName + ", productPriece="
				+ productPriece + ", productImage=" + productImage + ", productCategory=" + productCategory + "]";
	}
	
}
