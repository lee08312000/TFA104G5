package com.campTagDetail.model;

import java.util.List;

import java.util.Set;

import com.campEquipDetail.model.CampEquipDetailVO;


public interface CampTagDetailDAO {

	public void insert(CampTagDetailVO campTagDetailVO);

	public void update(CampTagDetailVO campTagDetailVO);


	public CampTagDetailVO findByPrimaryKey(Integer campTagId, Integer campId);


	
	public void delete(Integer campTagId, Integer campId);


	public Set<CampTagDetailVO> findByMultireq(Set<Integer> camptagSet);//查詢符合條件的營地


	public List<Integer> findByCampId2(Integer campId);
	
	 public List<CampTagDetailVO> findByCampId(Integer campId);
	
	
	public List<CampTagDetailVO> getAll();


	public void deleteByKey(Integer campId);

	List<CampTagDetailVO> getAll2();



}
