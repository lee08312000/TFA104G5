package com.campTag.model;

import java.util.List;

public interface CampTagDAO {

	public void add(CampTagVO campTagVO);

	public CampTagVO findByPrimaryKey(Integer campTagId);

	public void delete(Integer campTagId);

	public void update(CampTagVO campTagVO);

	public List<CampTagVO> getAll();



}
