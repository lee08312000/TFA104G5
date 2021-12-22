package com.campArea.model;

import java.util.List;

public interface CampAreaDAO {
	public void add(CampAreaVO campAreaVO);
	

	public void update(CampAreaVO campAreaVO);

	public void delete(Integer campAreaId);

	public CampAreaVO findByPrimaryKey(Integer campAreaId);

	public List<CampAreaVO> getAll();
	
	List<CampAreaVO> camparealist(Integer campId);


	public void insert(CampAreaVO campAreaVO);
	
}
