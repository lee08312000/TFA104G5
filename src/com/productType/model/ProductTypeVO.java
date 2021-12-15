package com.productType.model;

public class ProductTypeVO implements java.io.Serializable{
	private Integer productTypeId;   //�ӫ~�����y����
	private String productTypeName;  //�ӫ~�����W��
	
	
	
	public ProductTypeVO() {
		super();
	}

	public ProductTypeVO(Integer productTypeId, String productTypeName) {
		super();
		this.productTypeId = productTypeId;
		this.productTypeName = productTypeName;
	}
	
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	
	
}
