package com.campAreaOrderDetail.model;

import java.io.Serializable;

//營位訂單明細(camp_area_order_detail)
public class CampAreaOrderDetailVO implements Serializable {

	private Integer campAreaOrderDetailId;// 營位訂單明細流水號
	private Integer campAreaId;// 營位流水號
	private Integer campOrderId;// 營地訂單流水號
	private Integer bookingQuantity;// 訂帳數量
	private Integer campAreaWeekdayPrice;// 營位平日單價
	private Integer campAreaHolidayPrice;// 營位假日單價
	private Integer capitationQuantity;// 加購人頭數量
	private Integer perCapitationFee;// 加購人頭單價
	private Integer bookingWeekdays;// 訂位平日天數
	private Integer bookingHolidays;// 訂位假日天數

	public CampAreaOrderDetailVO() {

	}

	public CampAreaOrderDetailVO(Integer campAreaOrderDetailId, Integer campAreaId, Integer campOrderId,
			Integer bookingQuantity, Integer campAreaWeekdayPrice, Integer campAreaHolidayPrice,
			Integer capitationQuantity, Integer perCapitationFee, Integer bookingWeekdays, Integer bookingHolidays) {
		this.campAreaOrderDetailId = campAreaOrderDetailId;
		this.campAreaId = campAreaId;
		this.campOrderId = campOrderId;
		this.bookingQuantity = bookingQuantity;
		this.campAreaWeekdayPrice = campAreaWeekdayPrice;
		this.campAreaHolidayPrice = campAreaHolidayPrice;
		this.capitationQuantity = capitationQuantity;
		this.perCapitationFee = perCapitationFee;
		this.bookingWeekdays = bookingWeekdays;
		this.bookingHolidays = bookingHolidays;
	}

	public Integer getCampAreaOrderDetailId() {
		return campAreaOrderDetailId;
	}

	public void setCampAreaOrderDetailId(Integer campAreaOrderDetailId) {
		this.campAreaOrderDetailId = campAreaOrderDetailId;
	}

	public Integer getCampAreaId() {
		return campAreaId;
	}

	public void setCampAreaId(Integer campAreaId) {
		this.campAreaId = campAreaId;
	}

	public Integer getCampOrderId() {
		return campOrderId;
	}

	public void setCampOrderId(Integer campOrderId) {
		this.campOrderId = campOrderId;
	}

	public Integer getBookingQuantity() {
		return bookingQuantity;
	}

	public void setBookingQuantity(Integer bookingQuantity) {
		this.bookingQuantity = bookingQuantity;
	}

	public Integer getCampAreaWeekdayPrice() {
		return campAreaWeekdayPrice;
	}

	public void setCampAreaWeekdayPrice(Integer campAreaWeekdayPrice) {
		this.campAreaWeekdayPrice = campAreaWeekdayPrice;
	}

	public Integer getCampAreaHolidayPrice() {
		return campAreaHolidayPrice;
	}

	public void setCampAreaHolidayPrice(Integer campAreaHolidayPrice) {
		this.campAreaHolidayPrice = campAreaHolidayPrice;
	}

	public Integer getCapitationQuantity() {
		return capitationQuantity;
	}

	public void setCapitationQuantity(Integer capitationQuantity) {
		this.capitationQuantity = capitationQuantity;
	}

	public Integer getPerCapitationFee() {
		return perCapitationFee;
	}

	public void setPerCapitationFee(Integer perCapitationFee) {
		this.perCapitationFee = perCapitationFee;
	}

	public Integer getBookingWeekdays() {
		return bookingWeekdays;
	}

	public void setBookingWeekdays(Integer bookingWeekdays) {
		this.bookingWeekdays = bookingWeekdays;
	}

	public Integer getBookingHolidays() {
		return bookingHolidays;
	}

	public void setBookingHolidays(Integer bookingHolidays) {
		this.bookingHolidays = bookingHolidays;
	}

	@Override
	public String toString() {
		return "CampAreaOrderDetailVO [campAreaOrderDetailId=" + campAreaOrderDetailId + ", campAreaId=" + campAreaId
				+ ", campOrderId=" + campOrderId + ", bookingQuantity=" + bookingQuantity + ", campAreaWeekdayPrice="
				+ campAreaWeekdayPrice + ", campAreaHolidayPrice=" + campAreaHolidayPrice + ", capitationQuantity="
				+ capitationQuantity + ", perCapitationFee=" + perCapitationFee + ", bookingWeekdays=" + bookingWeekdays
				+ ", bookingHolidays=" + bookingHolidays + "]";
	}

}