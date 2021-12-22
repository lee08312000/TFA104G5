package com.campOrder.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

//營地訂單(CampOrder)
public class CampOrderVO implements Serializable {
	private Integer campOrderId;// 營地訂單流水號
	private Integer campId;// 營地流水號
	private Integer memberId; // 會員流水號
	private Integer campOrderStatus;// 營地訂單狀態 1(處理中), 2(已確認) ,3(已完成)
	private Integer campOrderTotalAmount;// 營地訂單總金額
	private Date campCheckOutDate;// 預計退房日期
	private Date campCheckInDate;// 預計入住日期
	private String creditCardNum;// 信用卡號
	private String payerName;// 付款人姓名
	private String payerPhone;// 付款人電話
	private Timestamp campOrderConfirmedTime;// 營地訂單成立時間
	private Timestamp campOrderCompletedTime;// 營地訂單完成時間
	private Integer campCommentStar;// 營地訂單評價
	private String campComment;// 營地訂單評論
	private Timestamp campOrderCommentTime;// 營地訂單評論時間
	
	public CampOrderVO() {
	}	
	public CampOrderVO(Integer campOrderId, Integer campId, Integer memberId, Integer campOrderStatus,
			Integer campOrderTotalAmount, Date campCheckOutDate, Date campCheckInDate, String creditCardNum,
			String payerName, String payerPhone, Timestamp campOrderConfirmedTime, Timestamp campOrderCompletedTime,
			Integer campCommentStar, String campComment, Timestamp campOrderCommentTime) {
	
		this.campOrderId = campOrderId;
		this.campId = campId;
		this.memberId = memberId;
		this.campOrderStatus = campOrderStatus;
		this.campOrderTotalAmount = campOrderTotalAmount;
		this.campCheckOutDate = campCheckOutDate;
		this.campCheckInDate = campCheckInDate;
		this.creditCardNum = creditCardNum;
		this.payerName = payerName;
		this.payerPhone = payerPhone;
		this.campOrderConfirmedTime = campOrderConfirmedTime;
		this.campOrderCompletedTime = campOrderCompletedTime;
		this.campCommentStar = campCommentStar;
		this.campComment = campComment;
		this.campOrderCommentTime = campOrderCommentTime;
	}
	public Integer getCampOrderId() {
		return campOrderId;
	}
	public void setCampOrderId(Integer campOrderId) {
		this.campOrderId = campOrderId;
	}
	public Integer getCampId() {
		return campId;
	}
	public void setCampId(Integer campId) {
		this.campId = campId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getCampOrderStatus() {
		return campOrderStatus;
	}
	public void setCampOrderStatus(Integer campOrderStatus) {
		this.campOrderStatus = campOrderStatus;
	}
	public Integer getCampOrderTotalAmount() {
		return campOrderTotalAmount;
	}
	public void setCampOrderTotalAmount(Integer campOrderTotalAmount) {
		this.campOrderTotalAmount = campOrderTotalAmount;
	}
	public Date getCampCheckOutDate() {
		return campCheckOutDate;
	}
	public void setCampCheckOutDate(Date campCheckOutDate) {
		this.campCheckOutDate = campCheckOutDate;
	}
	public Date getCampCheckInDate() {
		return campCheckInDate;
	}
	public void setCampCheckInDate(Date campCheckInDate) {
		this.campCheckInDate = campCheckInDate;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayerPhone() {
		return payerPhone;
	}
	public void setPayerPhone(String payerPhone) {
		this.payerPhone = payerPhone;
	}
	public Timestamp getCampOrderConfirmedTime() {
		return campOrderConfirmedTime;
	}
	public void setCampOrderConfirmedTime(Timestamp campOrderConfirmedTime) {
		this.campOrderConfirmedTime = campOrderConfirmedTime;
	}
	public Timestamp getCampOrderCompletedTime() {
		return campOrderCompletedTime;
	}
	public void setCampOrderCompletedTime(Timestamp campOrderCompletedTime) {
		this.campOrderCompletedTime = campOrderCompletedTime;
	}
	public Integer getCampCommentStar() {
		return campCommentStar;
	}
	public void setCampCommentStar(Integer campCommentStar) {
		this.campCommentStar = campCommentStar;
	}
	public String getCampComment() {
		return campComment;
	}
	public void setCampComment(String campComment) {
		this.campComment = campComment;
	}
	public Timestamp getCampOrderCommentTime() {
		return campOrderCommentTime;
	}
	public void setCampOrderCommentTime(Timestamp campOrderCommentTime) {
		this.campOrderCommentTime = campOrderCommentTime;
	}

	
}