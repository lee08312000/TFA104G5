package com.cart.model;

import java.io.Serializable;

public class CartVO implements Serializable {
	
	private Integer companyId;
	private Integer productId;
	private Integer productTypeId;
	private String productName;
	private Integer productPrice;
	private Integer productPurchaseQuantity;
	
	
	public CartVO() {
		super();
	}
	
	public CartVO(Integer companyId, Integer productId, Integer productTypeId, String productName, Integer productPrice,
			Integer productPurchaseQuantity) {
		super();
		this.companyId = companyId;
		this.productId = productId;
		this.productTypeId = productTypeId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productPurchaseQuantity = productPurchaseQuantity;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}
	public Integer getProductPurchaseQuantity() {
		return productPurchaseQuantity;
	}
	public void setProductPurchaseQuantity(Integer productPurchaseQuantity) {
		this.productPurchaseQuantity = productPurchaseQuantity;
	}
	
	
}
