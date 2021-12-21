package com.camp.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CampDAO {

	public void insert(CampVO campVO);

	public void update(CampVO campVO);

	public void delete(Integer campId);

	public CampVO findByPrimaryKey(Integer campId);

	public List<CampVO> findByKeyWord(String words);
	
	
	public List<CampVO> camplist(Integer campId, Date startime, Date endtime, String campIdsearchs);

	public List<CampVO> getall(Integer orderby);
	

	List<CampVO> getAll();
	


	//查詢全部營地(分頁模式)  rows=顯示筆數   
	public Map getAllByPage(Integer rows,Integer status,Integer reqpage);
	
	


}
