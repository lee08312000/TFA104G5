package com.favoriteProdoct.model;

import java.util.List;



public interface FavoriteProdoctDAO {
	
	 public void insert(FavoriteProdoctVO FavoriteProdoctVO);
     public void update(FavoriteProdoctVO FavoriteProdoctVO);
     public void delete(Integer favoriteProductId);
     public FavoriteProdoctVO findByPrimaryKey(Integer favoriteProductId);
     public List<FavoriteProdoctVO> getAll();
     public FavoriteProdoctVO findByMemberIdAndProductId(Integer memberId, Integer productId);
	
}
