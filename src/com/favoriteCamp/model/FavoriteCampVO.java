package com.favoriteCamp.model;

import java.io.Serializable;

//我的最愛營地(favorite_camp)
public class FavoriteCampVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer favoriteCampId;// 最愛營地流水號
	private Integer memberId;// 會員流水號
	private Integer campId;// 營地流水號
	
	public FavoriteCampVO() {
	}
	public FavoriteCampVO(Integer favoriteCampId, Integer memberId, Integer campId) {
		this.favoriteCampId = favoriteCampId;
		this.memberId = memberId;
		this.campId = campId;
	}
	public Integer getFavoriteCampId() {
		return favoriteCampId;
	}
	public void setFavoriteCampId(Integer favoriteCampId) {
		this.favoriteCampId = favoriteCampId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getCampId() {
		return campId;
	}
	public void setCampId(Integer campId) {
		this.campId = campId;
	}
	@Override
	public String toString() {
		return "FavoriteCampVO [favoriteCampId=" + favoriteCampId + ", memberId=" + memberId + ", campId=" + campId
				+ "]";
	}

	
}