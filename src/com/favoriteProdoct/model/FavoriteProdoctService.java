package com.favoriteProdoct.model;

import java.util.List;



public class FavoriteProdoctService {
	private FavoriteProdoctDAO dao;

	public FavoriteProdoctService() {
		dao = new FavoriteProdoctDAOImpl();
	}
	
	public FavoriteProdoctVO addFavoriteProdoct(Integer memberId, Integer productId) {

		FavoriteProdoctVO FavoriteProdoctVO = new FavoriteProdoctVO();

		FavoriteProdoctVO.setMemberId(memberId);
		FavoriteProdoctVO.setProductId(productId);
		
		dao.insert(FavoriteProdoctVO);

		return FavoriteProdoctVO;
	}

	public FavoriteProdoctVO updateFavoriteProdoct(Integer favoriteProductId, Integer memberId, Integer productId) {

		FavoriteProdoctVO FavoriteProdoctVO = new FavoriteProdoctVO();
		
		FavoriteProdoctVO.setFavoriteProductId(favoriteProductId);
		FavoriteProdoctVO.setMemberId(memberId);
		FavoriteProdoctVO.setProductId(productId);
		dao.update(FavoriteProdoctVO);
		
		return FavoriteProdoctVO;
	}

	public void deleteFavoriteProdoct(Integer favoriteProductId) {
		dao.delete(favoriteProductId);
	}

	public FavoriteProdoctVO getOneFavoriteProdoct(Integer favoriteProductId) {
		return dao.findByPrimaryKey(favoriteProductId);
	}

	public List<FavoriteProdoctVO> getAllFavoriteProdoct() {
		return dao.getAll();
	}
	
	// update by Lee
	public FavoriteProdoctVO getOneFavoriteProdoctByMemberIdAndProductId(Integer memberId, Integer productId) {
		return dao.findByMemberIdAndProductId(memberId, productId);
	}
}
