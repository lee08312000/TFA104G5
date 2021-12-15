package com.campArea.model;

import java.io.Serializable;
import java.util.Arrays;

// 營位(CampArea)
public class CampAreaVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer campAreaId;// 營位流水號
	private Integer campId;// 營地流水號
	private String campAreaName;// 營位名稱
	private Integer campAreaMax;// 帳數上限
	private Integer weekdayPrice;// 平日(單價)
	private Integer holidayPrice;// 假日(單價)
	private Integer capitationMax;// 每帳加購人頭上限
	private Integer perCapitationFee;// 加購人頭價格
	private byte[] campAreaPic;// 營位美照
	
	public CampAreaVO() {
	}
	public CampAreaVO(Integer campAreaId, Integer campId, String campAreaName, Integer campAreaMax, Integer weekdayPrice,
			Integer holidayPrice, Integer capitationMax, Integer perCapitationFee, byte[] campAreaPic) {
			
		this.campAreaId = campAreaId;
		this.campId = campId;
		this.campAreaName = campAreaName;
		this.campAreaMax = campAreaMax;
		this.weekdayPrice = weekdayPrice;
		this.holidayPrice = holidayPrice;
		this.capitationMax = capitationMax;
		this.perCapitationFee = perCapitationFee;
		this.campAreaPic = campAreaPic;
	}
	public Integer getCampAreaId() {
		return campAreaId;
	}
	public void setCampAreaId(Integer campAreaId) {
		this.campAreaId = campAreaId;
	}
	public Integer getCampId() {
		return campId;
	}
	public void setCampId(Integer campId) {
		this.campId = campId;
	}
	public String getCampAreaName() {
		return campAreaName;
	}
	public void setCampAreaName(String campAreaName) {
		this.campAreaName = campAreaName;
	}
	public Integer getCampAreaMax() {
		return campAreaMax;
	}
	public void setCampAreaMax(Integer campAreaMax) {
		this.campAreaMax = campAreaMax;
	}
	public Integer getWeekdayPrice() {
		return weekdayPrice;
	}
	public void setWeekdayPrice(Integer weekdayPrice) {
		this.weekdayPrice = weekdayPrice;
	}
	public Integer getHolidayPrice() {
		return holidayPrice;
	}
	public void setHolidayPrice(Integer holidayPrice) {
		this.holidayPrice = holidayPrice;
	}
	public Integer getCapitationMax() {
		return capitationMax;
	}
	public void setCapitationMax(Integer capitationMax) {
		this.capitationMax = capitationMax;
	}
	public Integer getPerCapitationFee() {
		return perCapitationFee;
	}
	public void setPerCapitationFee(Integer perCapitationFee) {
		this.perCapitationFee = perCapitationFee;
	}
	public byte[] getCampAreaPic() {
		return campAreaPic;
	}
	public void setCampAreaPic(byte[] campAreaPic) {
		this.campAreaPic = campAreaPic;
	}
	@Override
	public String toString() {
		return "CampAreaVO [campAreaId=" + campAreaId + ", campId=" + campId + ", campAreaName=" + campAreaName
				+ ", campAreaMax=" + campAreaMax + ", weekdayPrice=" + weekdayPrice + ", holidayPrice=" + holidayPrice
				+ ", capitationMax=" + capitationMax + ", perCapitationFee=" + perCapitationFee + ", campAreaPic="
				+ Arrays.toString(campAreaPic) + "]";
	}


}

