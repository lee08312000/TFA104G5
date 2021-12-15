package com.campEquipDetail.model;

import java.util.List;
import java.util.Set;

public class CampEquipDetailService {

	private CampEquipDetailDAO equipdetaildao;

	CampEquipDetailService() {
		equipdetaildao = new CampEquipDetailDAOImpl();
	}

//新增營地設備明細
	public void addCampEquipTagDetail(Set<CampEquipDetailVO> equipSet) {
		if (!equipSet.isEmpty()) {
			equipdetaildao.add(equipSet);

		}

	}

//刪除營地設備明細
	public void delete(CampEquipDetailVO campEquipDetailVO) {
		if (campEquipDetailVO != null) {
			equipdetaildao.delete(campEquipDetailVO);

		}

	}


//查詢這個營地有哪些設備標籤
	public Set<CampEquipDetailVO> findEquipTagByCampId(Integer CampId) {
		if(CampId!=null) {
			return equipdetaildao.findByCampId(CampId);
		}else {
			return null;
		}

	}

	
//查詢這個設備標籤有哪些營地
	public Set<CampEquipDetailVO> findCampIdByEquipId(Integer campEquipId) {
		if(campEquipId!=null) {
			return equipdetaildao.findByCampEquipId(campEquipId);
		}else {
			return null;
		}

	}
		
//查詢符合條件的營地(多條件)
	public Set<CampEquipDetailVO> findByMultireq(Set<Integer> campEquipIdSet) {
		if(!campEquipIdSet.isEmpty()) {
			return equipdetaildao.findByMultireq(campEquipIdSet);
		}else {
			return null;
		}

	}
		
		
		
	
//取得全部資料
	public List<CampEquipDetailVO> getAllCampEquipDetailVO() {
		
		return equipdetaildao.getAll();
	}

}
