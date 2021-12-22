package com.campEquip.model;

import java.io.Serializable;
//營地設備(camp_equip)
public class CampEquipVO implements Serializable{
	private Integer campEquipId;   //營地設備流水號
	private String campEquipName;   //營地設備名稱
	public CampEquipVO() {

	}
	public CampEquipVO(Integer campEquipId, String campEquipName) {
		this.campEquipId = campEquipId;
		this.campEquipName = campEquipName;
	}
	public Integer getCampEquipId() {
		return campEquipId;
	}
	public void setCampEquipId(Integer campEquipId) {
		this.campEquipId = campEquipId;
	}
	public String getCampEquipName() {
		return campEquipName;
	}
	public void setCampEquipName(String campEquipName) {
		this.campEquipName = campEquipName;
	}

}
