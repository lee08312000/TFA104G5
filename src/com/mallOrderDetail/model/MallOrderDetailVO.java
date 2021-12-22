package com.mallOrderDetail.model;
import java.io.Serializable;
import java.sql.Timestamp;

// 商城訂單明細(mall_order_detail)
public class MallOrderDetailVO implements Serializable {

	private Integer mallOrderDetailId; // 商城訂單明細流水號
	private Integer mallOrderId; // 商城訂單流水號
	private Integer productId; // 商品流水號
	private Integer productPurchaseQuantity; // 商品購買數量
	private Integer productPurchasePrice; // 商品單價
	private Integer productCommentStar; // 商品評價
	private String productComment; // 商品評論
	private Timestamp productCommentTime; // 商品評論時間
	
	
	
	public MallOrderDetailVO() {
		super();
	}

	public MallOrderDetailVO(Integer mallOrderDetailId, Integer mallOrderId, Integer productId,
			Integer productPurchaseQuantity, Integer productPurchasePrice, Integer productCommentStar,
			String productComment, Timestamp productCommentTime) {
		super();
		this.mallOrderDetailId = mallOrderDetailId;
		this.mallOrderId = mallOrderId;
		this.productId = productId;
		this.productPurchaseQuantity = productPurchaseQuantity;
		this.productPurchasePrice = productPurchasePrice;
		this.productCommentStar = productCommentStar;
		this.productComment = productComment;
		this.productCommentTime = productCommentTime;
	}
	
	public Integer getMallOrderDetailId() {
		return mallOrderDetailId;
	}
	public void setMallOrderDetailId(Integer mallOrderDetailId) {
		this.mallOrderDetailId = mallOrderDetailId;
	}
	public Integer getMallOrderId() {
		return mallOrderId;
	}
	public void setMallOrderId(Integer mallOrderId) {
		this.mallOrderId = mallOrderId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getProductPurchaseQuantity() {
		return productPurchaseQuantity;
	}
	public void setProductPurchaseQuantity(Integer productPurchaseQuantity) {
		this.productPurchaseQuantity = productPurchaseQuantity;
	}
	public Integer getProductPurchasePrice() {
		return productPurchasePrice;
	}
	public void setProductPurchasePrice(Integer productPurchasePrice) {
		this.productPurchasePrice = productPurchasePrice;
	}
	public Integer getProductCommentStar() {
		return productCommentStar;
	}
	public void setProductCommentStar(Integer productCommentStar) {
		this.productCommentStar = productCommentStar;
	}
	public String getProductComment() {
		return productComment;
	}
	public void setProductComment(String productComment) {
		this.productComment = productComment;
	}
	public Timestamp getProductCommentTime() {
		return productCommentTime;
	}
	public void setProductCommentTime(Timestamp productCommentTime) {
		this.productCommentTime = productCommentTime;
	}
	
}
