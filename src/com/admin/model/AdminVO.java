package com.admin.model;

// 管理員VO
public class AdminVO implements java.io.Serializable{
	@Override
	public String toString() {
		return "AdminVO [adminId=" + adminId + ", adminAccountStatus=" + adminAccountStatus + ", adminAccount="
				+ adminAccount + ", adminPassword=" + adminPassword + "]";
	}
	
	private Integer adminId;				// 管理員流水號
	private Integer adminAccountStatus;			// 管理員帳號狀態
	private String adminAccount;				// 管理員帳號
	private String adminPassword;				// 管理員密碼
	
	public AdminVO() {
		
	}
	
	// 建構子
	public AdminVO(Integer adminId, Integer adminAccountStatus, String adminAccount, String adminPassword) {
		super();
		this.adminId = adminId;					// 管理員流水號	
		this.adminAccountStatus = adminAccountStatus;		// 管理員帳號狀態
		this.adminAccount = adminAccount;			// 管理員帳號
		this.adminPassword = adminPassword;			// 管理員密碼
	}
	
	// 管理員流水號
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	// 管理員帳號狀態
	public Integer getAdminAccountStatus() {
		return adminAccountStatus;
	}
	public void setAdminAccountStatus(Integer adminAccountStatus) {
		this.adminAccountStatus = adminAccountStatus;
	}
	// 管理員帳號
	public String getAdminAccount() {
		return adminAccount;
	}
	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}
	// 管理員密碼
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

}