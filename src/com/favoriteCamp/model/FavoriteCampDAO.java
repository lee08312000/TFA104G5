package com.favoriteCamp.model;

import java.util.List;

public interface FavoriteCampDAO {

	void add(FavoriteCampVO favoriteCampVO);

	void update(FavoriteCampVO favoriteCampVO);

	void delete(Integer favoriteCampId);
	
	void deleteByMemberId(Integer  memberId );

	FavoriteCampVO findByPrimaryKey(Integer favoriteCampId);

	List<FavoriteCampVO> getAllByMemberId(Integer memberId);

}
