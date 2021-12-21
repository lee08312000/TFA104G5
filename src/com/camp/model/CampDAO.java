package com.camp.model;

import java.util.Date;
import java.util.List;

public interface CampDAO {

	public void insert(CampVO campVO);

	public void update(CampVO campVO);

	public void delete(Integer campId);

	public CampVO findByPrimaryKey(Integer campId);

	public List<CampVO> findByKeyWord(String words);
	
	
	public List<CampVO> camplist(Integer campId, Date startime, Date endtime, String campIdsearchs);

	public List<CampVO> getall(Integer orderby);
	
	
	List<CampVO> getAll();

	CampVO getSelectStmt(Integer campId);//12/17新增
	


	//查詢全部營地(分頁模式) offset =略過筆數; rows=顯示筆數   
//	public List<CampVO> getAllByPage(Integer offset,Integer rows,Integer status);
	
	


}
