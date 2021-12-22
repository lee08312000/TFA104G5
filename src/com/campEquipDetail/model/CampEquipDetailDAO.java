package com.campEquipDetail.model;



import java.util.List;
import java.util.Set;

public interface CampEquipDetailDAO {
	public void add(Set<CampEquipDetailVO> equipSet);
	public void delete(CampEquipDetailVO campEquipDetailVO);
	public CampEquipDetailVO findByPK(CampEquipDetailVO campEquipDetailVO); 
	public Set<CampEquipDetailVO> findByCampId(Integer CampId);  //查詢這個營地有哪些設備標籤
	public Set<CampEquipDetailVO> findByCampEquipId(Integer campEquipId);//查詢這個設備標籤有哪些營地
	public Set<CampEquipDetailVO> findByMultireq(Set<Integer> campEquipIdSet);//查詢符合條件的營地

	public List<CampEquipDetailVO> getAll();
	
	

}
