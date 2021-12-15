package com.camp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

//營地(Camp)
public class CampVO implements Serializable {
	/**
	 * 
	 */
	private Integer campId;// 營地流水號
	private Integer companyId;// 廠商流水號
	private Integer campStatus;// 營地狀態
	private String campDiscription;
	private String campName;// 營地名稱
	private String campRule;// 營地租借規則
	private byte[] campPic1;// 營地美照1
	private byte[] campPic2;// 營地美照2
	private byte[] campPic3;// 營地美照3
	private byte[] campPic4;// 營地美照4
	private byte[] campPic5;// 營地美照5
	private String campAddress;// 營地地址
	private String campPhone;// 營地電話
	private String certificateNum;// 認證字號
	private byte[] certificatePic;// 證書圖片
	private Timestamp campLaunchedTime;// 營地上架時間
	private Timestamp campAppliedLaunchTime;// 營地申請上架時間
	private BigDecimal longitude;// 經度
	private BigDecimal lattitude;// 緯度
	
	public CampVO() {
		
	}
	
	
	public CampVO(Integer campId, Integer companyId, Integer campStatus, String campDiscription, String campName,
			String campRule, byte[] campPic1, byte[] campPic2, byte[] campPic3, byte[] campPic4, byte[] campPic5,
			String campAddress, String campPhone, String certificateNum, byte[] certificatePic,
			Timestamp campLaunchedTime, Timestamp campAppliedLaunchTime, BigDecimal longitude, BigDecimal lattitude) {
		super();
		this.campId = campId;
		this.companyId = companyId;
		this.campStatus = campStatus;
		this.campDiscription = campDiscription;
		this.campName = campName;
		this.campRule = campRule;
		this.campPic1 = campPic1;
		this.campPic2 = campPic2;
		this.campPic3 = campPic3;
		this.campPic4 = campPic4;
		this.campPic5 = campPic5;
		this.campAddress = campAddress;
		this.campPhone = campPhone;
		this.certificateNum = certificateNum;
		this.certificatePic = certificatePic;
		this.campLaunchedTime = campLaunchedTime;
		this.campAppliedLaunchTime = campAppliedLaunchTime;
		this.longitude = longitude;
		this.lattitude = lattitude;
	}


	public Integer getCampId() {
		return campId;
	}


	public void setCampId(Integer campId) {
		this.campId = campId;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public Integer getCampStatus() {
		return campStatus;
	}


	public void setCampStatus(Integer campStatus) {
		this.campStatus = campStatus;
	}


	public String getCampDiscription() {
		return campDiscription;
	}


	public void setCampDiscription(String campDiscription) {
		this.campDiscription = campDiscription;
	}


	public String getCampName() {
		return campName;
	}


	public void setCampName(String campName) {
		this.campName = campName;
	}


	public String getCampRule() {
		return campRule;
	}


	public void setCampRule(String campRule) {
		this.campRule = campRule;
	}


	public byte[] getCampPic1() {
		return campPic1;
	}


	public void setCampPic1(byte[] campPic1) {
		this.campPic1 = campPic1;
	}


	public byte[] getCampPic2() {
		return campPic2;
	}


	public void setCampPic2(byte[] campPic2) {
		this.campPic2 = campPic2;
	}


	public byte[] getCampPic3() {
		return campPic3;
	}


	public void setCampPic3(byte[] campPic3) {
		this.campPic3 = campPic3;
	}


	public byte[] getCampPic4() {
		return campPic4;
	}


	public void setCampPic4(byte[] campPic4) {
		this.campPic4 = campPic4;
	}


	public byte[] getCampPic5() {
		return campPic5;
	}


	public void setCampPic5(byte[] campPic5) {
		this.campPic5 = campPic5;
	}


	public String getCampAddress() {
		return campAddress;
	}


	public void setCampAddress(String campAddress) {
		this.campAddress = campAddress;
	}


	public String getCampPhone() {
		return campPhone;
	}


	public void setCampPhone(String campPhone) {
		this.campPhone = campPhone;
	}


	public String getCertificateNum() {
		return certificateNum;
	}


	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}


	public byte[] getCertificatePic() {
		return certificatePic;
	}


	public void setCertificatePic(byte[] certificatePic) {
		this.certificatePic = certificatePic;
	}


	public Timestamp getCampLaunchedTime() {
		return campLaunchedTime;
	}


	public void setCampLaunchedTime(Timestamp campLaunchedTime) {
		this.campLaunchedTime = campLaunchedTime;
	}


	public Timestamp getCampAppliedLaunchTime() {
		return campAppliedLaunchTime;
	}


	public void setCampAppliedLaunchTime(Timestamp campAppliedLaunchTime) {
		this.campAppliedLaunchTime = campAppliedLaunchTime;
	}


	public BigDecimal getLongitude() {
		return longitude;
	}


	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}


	public BigDecimal getLattitude() {
		return lattitude;
	}


	public void setLattitude(BigDecimal lattitude) {
		this.lattitude = lattitude;
	}


	@Override
	public String toString() {
		return "CampVO [campId=" + campId + ", companyId=" + companyId + ", campStatus=" + campStatus
				+ ", campDiscription=" + campDiscription + ", campName=" + campName + ", campRule=" + campRule
				+ ", campPic1=" + Arrays.toString(campPic1) + ", campPic2=" + Arrays.toString(campPic2) + ", campPic3="
				+ Arrays.toString(campPic3) + ", campPic4=" + Arrays.toString(campPic4) + ", campPic5="
				+ Arrays.toString(campPic5) + ", campAddress=" + campAddress + ", campPhone=" + campPhone
				+ ", certificateNum=" + certificateNum + ", certificatePic=" + Arrays.toString(certificatePic)
				+ ", campLaunchedTime=" + campLaunchedTime + ", campAppliedLaunchTime=" + campAppliedLaunchTime
				+ ", longitude=" + longitude + ", lattitude=" + lattitude + "]";
	}
	
	
	
}
	
