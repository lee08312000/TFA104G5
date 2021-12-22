package com.campTag.model;

import java.io.Serializable;
//營地標籤(camp_tag)
public class CampTagVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer campTagId;  //營地標籤流水號
	private String campTagName;  //營地標籤名稱
	
	private Integer countCampNum; //營地標籤含有的數量
	
	public CampTagVO(){
		
	}

	public Integer getCampTagId() {
		return campTagId;
	}

	public void setCampTagId(Integer campTagId) {
		this.campTagId = campTagId;
	}

	public String getCampTagName() {
		return campTagName;
	}

	public void setCampTagName(String campTagName) {
		this.campTagName = campTagName;
	}

	public Integer getCountCampNum() {
		return countCampNum;
	}

	public void setCountCampNum(Integer countCampNum) {
		this.countCampNum = countCampNum;
	}

	public CampTagVO(Integer campTagId, String campTagName, Integer countCampNum) {
		super();
		this.campTagId = campTagId;
		this.campTagName = campTagName;
		this.countCampNum = countCampNum;
	}

	@Override
	public String toString() {
		return "CampTagVO [campTagId=" + campTagId + ", campTagName=" + campTagName + ", countCampNum=" + countCampNum
				+ "]";
	}
	
	

}