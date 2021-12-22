package com.company.model;

import java.sql.Timestamp;

// 廠商VO
public class CompanyVO implements java.io.Serializable{
	@Override
	public String toString() {
		return "CompanyVO [companyId=" + companyId + ", companyStatus=" + companyStatus + ", headName=" + headName
				+ ", companyName=" + companyName + ", companyAccount=" + companyAccount + ", companyPassword="
				+ companyPassword + ", companyEmail=" + companyEmail + ", companyTel=" + companyTel
				+ ", companyBankAccount=" + companyBankAccount + ", companyAddress=" + companyAddress
				+ ", companyRegisterTime=" + companyRegisterTime + "]";
	}
	
	private Integer companyId;			// 廠商流水號                
	private Integer companyStatus;			// 廠商帳號狀態				
	private String headName;			// 負責人姓名
	private String companyName;			// 廠商名稱
	private String companyAccount;			// 廠商帳號
	private String companyPassword;			// 廠商密碼
	private String companyEmail;			// 廠商email
	private String companyTel;			// 廠商電話
	private String companyBankAccount;		// 廠商銀行帳號
	private String companyAddress;			// 廠商地址
	private Timestamp companyRegisterTime;		// 廠商註冊時間
	
	public CompanyVO() {
		
	}
	
	// 建構子
	public CompanyVO(Integer companyId, Integer companyStatus, String headName, String companyName, String companyAccount,
			String companyPassword, String companyEmail, String companyTel, String companyBankAccount,
			String companyAddress, Timestamp companyRegisterTime) {
		super();
		this.companyId = companyId;				// 廠商流水號  																				
		this.companyStatus = companyStatus;			// 廠商帳號狀態
		this.headName = headName;				// 負責人姓名
		this.companyName = companyName;				// 廠商名稱
		this.companyAccount = companyAccount;			// 廠商帳號
		this.companyPassword = companyPassword;			// 廠商密碼
		this.companyEmail = companyEmail;			// 廠商email
		this.companyTel = companyTel;				// 廠商電話
		this.companyBankAccount = companyBankAccount;		// 廠商銀行帳號	
		this.companyAddress = companyAddress;			// 廠商地址
		this.companyRegisterTime = companyRegisterTime;		// 廠商註冊時間
	}
	
	// 廠商流水號 
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	// 廠商帳號狀態
	public Integer getCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(Integer companyStatus) {
		this.companyStatus = companyStatus;
	}
	// 負責人姓名
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	// 廠商名稱
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	// 廠商帳號
	public String getCompanyAccount() {
		return companyAccount;
	}
	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	// 廠商密碼
	public String getCompanyPassword() {
		return companyPassword;
	}
	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}	
	// 廠商email
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}	
	// 廠商電話
	public String getCompanyTel() {
		return companyTel;
	}
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}	
	// 廠商銀行帳號
	public String getCompanyBankAccount() {
		return companyBankAccount;
	}
	public void setCompanyBankAccount(String companyBankAccount) {
		this.companyBankAccount = companyBankAccount;
	}	
	// 廠商地址
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}	
	// 廠商註冊時間
	public Timestamp getCompanyRegisterTime() {
		return companyRegisterTime;
	}
	public void setCompanyRegisterTime(Timestamp companyRegisterTime) {
		this.companyRegisterTime = companyRegisterTime;
	}
	
}