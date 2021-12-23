package com.campAreaOrderDetail.model;


import java.sql.Connection;
import java.util.List;


public interface CampAreaOrderDetailDAO {
	public void add(CampAreaOrderDetailVO campAreaOrderDetailVO);
	public void update(CampAreaOrderDetailVO campAreaOrderDetailVO);
	public void delete(Integer campAreaOrderDetailId);
	public CampAreaOrderDetailVO findByPK( Integer campAreaOrderDetailId);
	public List<CampAreaOrderDetailVO> getAll();
	public CampAreaOrderDetailVO findByCampOrderId( Integer campOrderId);

}
