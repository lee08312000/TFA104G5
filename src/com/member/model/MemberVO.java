package com.member.model;

import java.util.Arrays;

// 會員VO
public class MemberVO implements java.io.Serializable{
	@Override
	public String toString() {
		return "MemberVO [memberId=" + memberId + ", memberAccountStatus=" + memberAccountStatus + ", memberName="
				+ memberName + ", memberAccount=" + memberAccount + ", memberPassword=" + memberPassword
				+ ", memberEmail=" + memberEmail + ", memberAddress=" + memberAddress + ", memberPhone=" + memberPhone
				+ ", memberPic=" + Arrays.toString(memberPic) + "]";
	}
	private Integer memberId;				// 會員流水號
	private Integer memberAccountStatus;	// 會員帳號狀態
	private String memberName;				// 會員姓名
	private String memberAccount;			// 會員帳號
	private String memberPassword;			// 會員密碼
	private String memberEmail;				// 會員email
	private String memberAddress;			// 會員地址
	private String memberPhone;				// 會員電話
	private byte[] memberPic;				// 會員照片
	
	public MemberVO() {
		
	}
	
	// 建構子
	public MemberVO(Integer memberId, Integer memberAccountStatus, String memberName, String memberAccount,
			String memberPassword, String memberEmail, String memberAddress, String memberPhone, byte[] memberPic) {
		super();
		this.memberId = memberId;							// 會員流水號
		this.memberAccountStatus = memberAccountStatus;		// 會員帳號狀態
		this.memberName = memberName;						// 會員姓名
		this.memberAccount = memberAccount;					// 會員帳號
		this.memberPassword = memberPassword;				// 會員密碼
		this.memberEmail = memberEmail;						// 會員email
		this.memberAddress = memberAddress;					// 會員地址
		this.memberPhone = memberPhone;						// 會員電話
		this.memberPic = memberPic;							// 會員照片
	}
	
	// 會員流水號
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}	
	// 會員帳號狀態
	public Integer getMemberAccountStatus() {
		return memberAccountStatus;
	}
	public void setMemberAccountStatus(Integer memberAccountStatus) {
		this.memberAccountStatus = memberAccountStatus;
	}
	// 會員姓名
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	// 會員帳號
	public String getMemberAccount() {
		return memberAccount;
	}
	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}
	// 會員密碼
	public String getMemberPassword() {
		return memberPassword;
	}
	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}
	// 會員email
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	// 會員地址
	public String getMemberAddress() {
		return memberAddress;
	}
	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}
	// 會員電話
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	// 會員照片
	public byte[] getMemberPic() {
		return memberPic;
	}
	public void setMemberPic(byte[] memberPic) {
		this.memberPic = memberPic;
	}
	
}