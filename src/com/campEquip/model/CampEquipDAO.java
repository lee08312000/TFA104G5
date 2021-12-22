package com.campEquip.model;



import java.util.List;

public interface CampEquipDAO {
	
		public void add(CampEquipVO campEquipVO);
		public void update(CampEquipVO campEquipVO);//名稱改正之類的
		public void delete(Integer  campEquipId);
		public CampEquipVO findByPK(Integer  campEquipId);
		public List<CampEquipVO> getAll();
	
	
	

}
