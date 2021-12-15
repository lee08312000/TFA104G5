package com.company.model;

import java.util.List;

import com.company.model.CompanyVO;

public interface CompanyDAO {
	void add(CompanyVO companyVO);
	void update(CompanyVO companyVO);
	void delete(Integer companyId);
	CompanyVO findByPK(Integer companyId);
	List<CompanyVO> getAll();
	
	// ginny新增
	 CompanyVO findByAccountPassword(String companyAccount,String companyPassword);
	 CompanyVO findByAccount(String companyAccount);
}
