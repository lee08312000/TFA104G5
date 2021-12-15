package com.campEquipDetail.model;

import java.io.Serializable;
//營地設備明細(camp_equip_detail)
public class CampEquipDetailVO implements Serializable{
	private Integer campEquipId ;   //營地設備流水號
	private Integer campId;        //營地流水號
	public CampEquipDetailVO() {

	}
	public CampEquipDetailVO(Integer campEquipId, Integer campId) {
		this.campEquipId = campEquipId;
		this.campId = campId;
	}
	public Integer getCampEquipId() {
		return campEquipId;
	}
	public void setCampEquipId(Integer campEquipId) {
		this.campEquipId = campEquipId;
	}
	public Integer getCampId() {
		return campId;
	}
	public void setCampId(Integer campId) {
		this.campId = campId;
	}
	
	

}
