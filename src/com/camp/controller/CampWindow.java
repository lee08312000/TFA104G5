package com.camp.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class CampWindow implements Serializable{
	private Integer campId;
	private String name;
	private String address;
	private String imgBase64;
	private List<String> tags;

	
	CampWindow(){
		
	}
	

	public CampWindow(Integer campId, String name, String address, String imgBase64, List<String> tags) {
		super();
		this.campId = campId;
		this.name = name;
		this.address = address;
		this.imgBase64 = imgBase64;
		this.tags = tags;
	}




	public Integer getCampId() {
		return campId;
	}


	public void setCampId(Integer campId) {
		this.campId = campId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getImgBase64() {
		return imgBase64;
	}


	public void setImgBase64(String imgBase64) {
		this.imgBase64 = imgBase64;
	}


	public List<String> getTags() {
		return tags;
	}


	public void setTags(List<String> tags) {
		this.tags = tags;
	}


	@Override
	public String toString() {
		return "CampWindow [campId=" + campId + ", name=" + name + ", address=" + address + ", imgBase64=" + imgBase64
				+ ", tags=" + tags + "]";
	}

	
}
	