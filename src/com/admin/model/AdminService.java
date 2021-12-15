package com.admin.model;

import java.util.List;

public class AdminService {
	private AdminDAO dao;
	
	
	public AdminService() {
		dao = new AdminDAOImpl();
	}
	
	public AdminVO addAdmin(Integer adminAccountStatus, String adminAccount, String adminPassword) {
		AdminVO AdminVO = new AdminVO();
		
		AdminVO.setAdminAccountStatus(adminAccountStatus);
		AdminVO.setAdminAccount(adminAccount);
		AdminVO.setAdminPassword(adminPassword);
		
		dao.add(AdminVO);
		return AdminVO;
		
	}
	
	public AdminVO updateAdmin(Integer adminId, Integer adminAccountStatus, String adminAccount, 
			String adminPassword) {
		AdminVO AdminVO = new AdminVO();
		
		AdminVO.setAdminId(adminId); 
		AdminVO.setAdminAccountStatus(adminAccountStatus);
		AdminVO.setAdminAccount(adminAccount);
		AdminVO.setAdminPassword(adminPassword);
		
		dao.update(AdminVO);	
		return AdminVO;
		
	}
	
	public void deleteAdmin(Integer adminId) {
		dao.delete(adminId);
	}
	
	public AdminVO getOneAdmin(Integer adminId) {
		return dao.findByPK(adminId);
	}
	
	public List<AdminVO> getAllAdmin() {
		return dao.getAll();
	}
	
}


		
	

	

