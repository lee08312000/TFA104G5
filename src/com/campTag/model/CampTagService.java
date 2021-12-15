package com.campTag.model;

import java.util.List;

public class CampTagService {

	private  CampTagDAO  camptagdao;
	
	public CampTagService() {
		camptagdao = new  CampTagDAOlmpl();
	}

	//新增營地標籤
	public  void  addCampTag(Integer campTagId, String campTagName) {

		 CampTagVO  campTagVO = new  CampTagVO();
		 
		 campTagVO.setCampTagId(campTagId);		 
		 campTagVO.setCampTagName(campTagName);
		  camptagdao.add(campTagVO);
	 

	}
	
	//刪除營地標籤
	public void deleteCampTag(Integer campTagId) {
		 camptagdao.delete(campTagId);
	}
	
	
	//更新營地標籤
	public void  updateCampTag(Integer campTagId, String campTagName) {
		 
		 CampTagVO  campTagVO = new  CampTagVO();
		 
		 campTagVO.setCampTagId(campTagId);		 
		 campTagVO.setCampTagName(campTagName);
	}
		 

	
	//查詢某個/營地標籤
	public CampTagVO getOneTag(Integer campTagId) {
		return camptagdao.findByPrimaryKey(campTagId);
	}                                          
	
	//查詢全部營地標籤
	public List<CampTagVO> getAll() {
		return  camptagdao.getAll();
	}
}
	
	
	
	
	

