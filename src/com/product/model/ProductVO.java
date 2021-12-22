package com.product.model;

import java.sql.Timestamp;

public class ProductVO implements java.io.Serializable{

	private Integer productId;  //�ӫ~�y����
	private Integer companyId;  //�t�Ӭy����
	private Integer productTypeId;  //�ӫ~�����y���� 
	private Integer productStatus;  //�ӫ~���A
	private String productName;  //�ӫ~�W��
	private Integer productPrice;  //�ӫ~���
	private String productBrand;  //�ӫ~�~�P
	private Integer productInventory;  //�ӫ~�w�s�ƶq
	private String productDescription;  //�ӫ~�ԭz
	private String shoppingInformation;  //�ӫ��ʶR�ݪ�
	private byte[] productPic1;  //�ӫ~����1
	private byte[] productPic2;  //�ӫ~����2
	private byte[] productPic3;  //�ӫ~����3
	private Timestamp productLaunchedTime;  //�ӫ~�W�[�ɶ�
	private Integer productCommentedAllnum;  //�ӫ~�w�����`��
	private Integer productCommentAllstar;  //�ӫ~�`����
	private Integer productSellAllnum;  // �ӫ~��X�`��
	
	
	public ProductVO() {
		super();
	}

	public ProductVO(Integer productId, Integer companyId, Integer productTypeId, Integer productStatus,
			String productName, Integer productPrice, String productBrand, Integer productInventory,
			String productDescription, String shoppingInformation, byte[] productPic1, byte[] productPic2,
			byte[] productPic3, Timestamp productLaunchedTime, Integer productCommentedAllnum,
			Integer productCommentAllstar, Integer productSellAllnum) {
		super();
		this.productId = productId;
		this.companyId = companyId;
		this.productTypeId = productTypeId;
		this.productStatus = productStatus;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productBrand = productBrand;
		this.productInventory = productInventory;
		this.productDescription = productDescription;
		this.shoppingInformation = shoppingInformation;
		this.productPic1 = productPic1;
		this.productPic2 = productPic2;
		this.productPic3 = productPic3;
		this.productLaunchedTime = productLaunchedTime;
		this.productCommentedAllnum = productCommentedAllnum;
		this.productCommentAllstar = productCommentAllstar;
		this.productSellAllnum = productSellAllnum;
	}
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public Integer getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
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
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public Integer getProductInventory() {
		return productInventory;
	}
	public void setProductInventory(Integer productInventory) {
		this.productInventory = productInventory;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getShoppingInformation() {
		return shoppingInformation;
	}
	public void setShoppingInformation(String shoppingInformation) {
		this.shoppingInformation = shoppingInformation;
	}
	public byte[] getProductPic1() {
		return productPic1;
	}
	public void setProductPic1(byte[] productPic1) {
		this.productPic1 = productPic1;
	}
	public byte[] getProductPic2() {
		return productPic2;
	}
	public void setProductPic2(byte[] productPic2) {
		this.productPic2 = productPic2;
	}
	public byte[] getProductPic3() {
		return productPic3;
	}
	public void setProductPic3(byte[] productPic3) {
		this.productPic3 = productPic3;
	}
	public Timestamp getProductLaunchedTime() {
		return productLaunchedTime;
	}
	public void setProductLaunchedTime(Timestamp productLaunchedTime) {
		this.productLaunchedTime = productLaunchedTime;
	}
	public Integer getProductCommentedAllnum() {
		return productCommentedAllnum;
	}
	public void setProductCommentedAllnum(Integer productCommentedAllnum) {
		this.productCommentedAllnum = productCommentedAllnum;
	}
	public Integer getProductCommentAllstar() {
		return productCommentAllstar;
	}
	public void setProductCommentAllstar(Integer productCommentAllstar) {
		this.productCommentAllstar = productCommentAllstar;
	}
	public Integer getProductSellAllnum() {
		return productSellAllnum;
	}
	public void setProductSellAllnum(Integer productSellAllnum) {
		this.productSellAllnum = productSellAllnum;
	}
	
	
}
