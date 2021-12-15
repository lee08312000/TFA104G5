package com.cart.model;

import java.io.Serializable;

public class ReceiverVO implements Serializable {

	private String receiverName;
	private String receiverPhone;
	private String receiverAddress;
	private String creditCardNum;
	private String securityCode;
	private String effectiveDate;
	
	public ReceiverVO() {
		super();
	}
	
	
	
	public ReceiverVO(String receiverName, String receiverPhone, String receiverAddress, String creditCardNum,
			String securityCode, String effectiveDate) {
		super();
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.receiverAddress = receiverAddress;
		this.creditCardNum = creditCardNum;
		this.securityCode = securityCode;
		this.effectiveDate = effectiveDate;
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
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	
}
