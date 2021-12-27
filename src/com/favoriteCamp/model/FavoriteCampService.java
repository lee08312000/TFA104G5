package com.favoriteCamp.model;

import java.util.List;

public class FavoriteCampService {

	private FavoriteCampDAO favoritecampdao;

	public FavoriteCampService() {
		favoritecampdao = new FavoriteCampDAOlmpl();
	}

	// 新增單筆-我的最愛營地
	public void addFavoriteCamp(Integer memberId, Integer campId) {

		FavoriteCampVO favoritecampVO = new FavoriteCampVO();

		favoritecampVO.setMemberId(memberId);
		favoritecampVO.setCampId(campId);

		favoritecampdao.add(favoritecampVO);

	}

	// 新增多筆-我的最愛營地
	public void favoriteCampList(Integer memberId, List<Integer> campIdList) {
		for (Integer campid : campIdList) {
			addFavoriteCamp(memberId, campid);

		}
	}

	// 刪除我的最愛營地
	public void deleteFavoritecamp(Integer favoriteCampId) {
		favoritecampdao.delete(favoriteCampId);
	}

	// 更新我的最愛營地
	public void updateFavoritecamp(Integer memberId, List<Integer> campIdList) {

		// TODO 尚未加上 rolleback
		favoritecampdao.deleteByMemberId(memberId);
		favoriteCampList(memberId, campIdList);

	}

	// 查詢某一個我的最愛營地
	public FavoriteCampVO getFavoritecamp(Integer favoriteCampId) {
		return favoritecampdao.findByPrimaryKey(favoriteCampId);

	}

	// 查詢某會員全部的最愛營地
	public List<FavoriteCampVO> findBymemberId(Integer memberId){		
		if(memberId!=null) {			
			return favoritecampdao.getAllByMemberId(memberId);						
		}else {			
			return null;			
		}		
	}
}
