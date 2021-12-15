package com.campBooking.model;

import java.io.Serializable;
import java.sql.Date;

//營地預約(CampBooking)
public class CampBookingVO implements Serializable{ 
	private Integer campBookingId;// 營地預約流水號
	private Integer campId;// 營地流水號
	private Integer campAreaId;// 營位流水號
	private Date date;// 日期
	private Integer bookingCampAreaMax;// 預約帳數上限
	private Integer bookedCampAreaNum;// 已預約帳數
	private Boolean closedStatus;// 公休狀態
	
	public CampBookingVO() {
	}
	public CampBookingVO(Integer campBookingId, Integer campId, Integer campAreaId, Date date,
			Integer bookingCampAreaMax, Integer bookedCampAreaNum, Boolean closedStatus) {
		
		this.campBookingId = campBookingId;
		this.campId = campId;
		this.campAreaId = campAreaId;
		this.date = date;
		this.bookingCampAreaMax = bookingCampAreaMax;
		this.bookedCampAreaNum = bookedCampAreaNum;
		this.closedStatus = closedStatus;
	}

	public Integer getCampBookingId() {
		return campBookingId;
	}

	public void setCampBookingId(Integer campBookingId) {
		this.campBookingId = campBookingId;
	}

	public Integer getCampId() {
		return campId;
	}

	public void setCampId(Integer campId) {
		this.campId = campId;
	}

	public Integer getCampAreaId() {
		return campAreaId;
	}

	public void setCampAreaId(Integer campAreaId) {
		this.campAreaId = campAreaId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getBookingCampAreaMax() {
		return bookingCampAreaMax;
	}

	public void setBookingCampAreaMax(Integer bookingCampAreaMax) {
		this.bookingCampAreaMax = bookingCampAreaMax;
	}

	public Integer getBookedCampAreaNum() {
		return bookedCampAreaNum;
	}

	public void setBookedCampAreaNum(Integer bookedCampAreaNum) {
		this.bookedCampAreaNum = bookedCampAreaNum;
	}

	public Boolean getClosedStatus() {
		return closedStatus;
	}

	public void setClosedStatus(Boolean closedStatus) {
		this.closedStatus = closedStatus;
	}

}
