package com.mallOrder.model;
import java.io.Serializable;
import java.sql.Timestamp;

// 商城訂單(mall_order)
public class MallOrderVO implements Serializable {
	
	private Integer mallOrderId; // 商城訂單流水號
	private Integer companyId; // 廠商流水號
	private Integer memberId; // 會員流水號
	private Integer mallOrderStatus; // 商城訂單狀態
	private Integer mallOrderDeliveryStatus; // 商城訂單物流狀態
	private Integer mailOrderTotalAmount; // 商城訂單總金額
	private Timestamp mallOrderConfirmedTime; // 商城訂單成立時間
	private String creditCardNum; // 信用卡號
	private String receiverName; // 收件人姓名
	private String receiverPhone; // 收件人電話
	private String receiverAddress; // 收件人地址
	private Timestamp mallOrderCompletedTime; // 商城訂單完成時間
	
	
	public MallOrderVO() {
		super();
	}

	public MallOrderVO(Integer mallOrderId, Integer companyId, Integer memberId, Integer mallOrderStatus,
			Integer mallOrderDeliveryStatus, Integer mailOrderTotalAmount, Timestamp mallOrderConfirmedTime,
			String creditCardNum, String receiverName, String receiverPhone, String receiverAddress,
			Timestamp mallOrderCompletedTime) {
		super();
		this.mallOrderId = mallOrderId;
		this.companyId = companyId;
		this.memberId = memberId;
		this.mallOrderStatus = mallOrderStatus;
		this.mallOrderDeliveryStatus = mallOrderDeliveryStatus;
		this.mailOrderTotalAmount = mailOrderTotalAmount;
		this.mallOrderConfirmedTime = mallOrderConfirmedTime;
		this.creditCardNum = creditCardNum;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.receiverAddress = receiverAddress;
		this.mallOrderCompletedTime = mallOrderCompletedTime;
	}
	
	public Integer getMallOrderId() {
		return mallOrderId;
	}
	public void setMallOrderId(Integer mallOrderId) {
		this.mallOrderId = mallOrderId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getMallOrderStatus() {
		return mallOrderStatus;
	}
	public void setMallOrderStatus(Integer mallOrderStatus) {
		this.mallOrderStatus = mallOrderStatus;
	}
	public Integer getMallOrderDeliveryStatus() {
		return mallOrderDeliveryStatus;
	}
	public void setMallOrderDeliveryStatus(Integer mallOrderDeliveryStatus) {
		this.mallOrderDeliveryStatus = mallOrderDeliveryStatus;
	}
	public Integer getMailOrderTotalAmount() {
		return mailOrderTotalAmount;
	}
	public void setMailOrderTotalAmount(Integer mailOrderTotalAmount) {
		this.mailOrderTotalAmount = mailOrderTotalAmount;
	}
	public Timestamp getMallOrderConfirmedTime() {
		return mallOrderConfirmedTime;
	}
	public void setMallOrderConfirmedTime(Timestamp mallOrderConfirmedTime) {
		this.mallOrderConfirmedTime = mallOrderConfirmedTime;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public Timestamp getMallOrderCompletedTime() {
		return mallOrderCompletedTime;
	}
	public void setMallOrderCompletedTime(Timestamp mallOrderCompletedTime) {
		this.mallOrderCompletedTime = mallOrderCompletedTime;
	}
	
}
