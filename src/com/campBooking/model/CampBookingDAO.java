package com.campBooking.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CampBookingDAO {
	
	public void add(CampBookingVO campBookingVO);
	
	
	//更新公休狀態 或 更新訂位數量
	public void update(CampBookingVO campBookingVO);
	
	
	public void delete(Integer campBookingId);
	
	
	public CampBookingVO findByPK(Integer campBookingId);
	
	
	//尋找某個營地"所有營位"的某一天的空位狀況(訂位用到)
	public List<CampBookingVO> findByAllArea(Integer campId,String date);
	
	
	//尋找某個"營位"的某一天的空位狀況(訂位用到)
		public CampBookingVO findByOneArea(Integer campAreaId,String date);
	
	//尋找某個營地整個空位狀況(月曆那邊用到)
	public Map<String,Integer> findByCampId(Integer campId,String date);

	
	public List<CampBookingVO> getAll();
	

}
