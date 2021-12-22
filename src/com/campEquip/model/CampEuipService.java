package com.campEquip.model;

import java.util.List;

public class CampEuipService {
	private CampEquipDAO euipdao;

	CampEuipService() {
		euipdao = new CampEquipDAOImpl();

	}

//新增營地標籤
	public void addCampEquiptag(String campEquipName) {
		if (campEquipName != null) {
			CampEquipVO campEquipVO = new CampEquipVO();
			campEquipVO.setCampEquipName(campEquipName);
			euipdao.add(campEquipVO);
		}
	}

//更新營地標籤	
	public void updateCampEquiptag(Integer campEquipId, String campEquipName) {
		if (campEquipId != null && campEquipName != null) {

			CampEquipVO campEquipVO = new CampEquipVO();
			campEquipVO.setCampEquipId(campEquipId);
			campEquipVO.setCampEquipName(campEquipName);
			euipdao.update(campEquipVO);
		}

	}

//刪除營地設備標籤，同時刪除所有關於這個標籤的營地設備明細
	public void deleteCampEquiptag(Integer campEquipId) {
		if (campEquipId != null) {
			euipdao.delete(campEquipId);
		}

	}

//查詢某個營地標籤		
	public CampEquipVO findByCampEquipId(Integer campEquipId) {
		if (campEquipId != null) {
			return euipdao.findByPK(campEquipId);
		} else {
			return null;
		}

	}

//查詢全部的營地設備標籤
	public List<CampEquipVO> getAllCampEquiptag(Integer order) {

		return euipdao.getAll();
	}
	

	
	

}
