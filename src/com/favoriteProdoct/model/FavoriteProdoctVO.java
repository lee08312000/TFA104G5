package com.favoriteProdoct.model;


public class FavoriteProdoctVO implements java.io.Serializable{
	private Integer favoriteProductId;  //�̷R�ӫ~�y����
	private Integer memberId;  //�|���y����
	private Integer productId;  //�ӫ~�y����
	
	
	
	public FavoriteProdoctVO() {
		super();
	}

	public FavoriteProdoctVO(Integer favoriteProductId, Integer memberId, Integer productId) {
		super();
		this.favoriteProductId = favoriteProductId;
		this.memberId = memberId;
		this.productId = productId;
	}
	
	public Integer getFavoriteProductId() {
		return favoriteProductId;
	}
	public void setFavoriteProductId(Integer favoriteProductId) {
		this.favoriteProductId = favoriteProductId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	
}
