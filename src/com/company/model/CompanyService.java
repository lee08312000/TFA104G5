package com.company.model;

import java.sql.Timestamp;
import java.util.List;
import javafx.application.Application;

public class CompanyService {

	private CompanyDAO dao;

	public CompanyService() {
		dao = new CompanyDAOImpl();
	}

	public CompanyVO addCompany(Integer companyStatus, String headName, String companyName, String companyAccount,
			String companyPassword, String companyEmail, String companyTel, String companyBankAccount,
			String companyAddress, Timestamp companyRegisterTime) {

		CompanyVO CompanyVO = new CompanyVO();

		CompanyVO.setCompanyStatus(companyStatus);
		CompanyVO.setHeadName(headName);
		CompanyVO.setCompanyName(companyName);
		CompanyVO.setCompanyAccount(companyAccount);
		CompanyVO.setCompanyPassword(companyPassword);
		CompanyVO.setCompanyEmail(companyEmail);
		CompanyVO.setCompanyTel(companyTel);
		CompanyVO.setCompanyBankAccount(companyBankAccount);
		CompanyVO.setCompanyAddress(companyAddress);
		CompanyVO.setCompanyRegisterTime(companyRegisterTime);
		dao.add(CompanyVO);
		return CompanyVO;
	}

	public CompanyVO updateCompany(Integer companyId, Integer companyStatus, String headName, String companyName,
			String companyAccount, String companyPassword, String companyEmail, String companyTel,
			String companyBankAccount, String companyAddress, Timestamp companyRegisterTime) {

		CompanyVO CompanyVO = new CompanyVO();

		CompanyVO.setCompanyId(companyId);
		CompanyVO.setCompanyStatus(companyStatus);
		CompanyVO.setHeadName(headName);
		CompanyVO.setCompanyName(companyName);
		CompanyVO.setCompanyAccount(companyAccount);
		CompanyVO.setCompanyPassword(companyPassword);
		CompanyVO.setCompanyEmail(companyEmail);
		CompanyVO.setCompanyTel(companyTel);
		CompanyVO.setCompanyBankAccount(companyBankAccount);
		CompanyVO.setCompanyAddress(companyAddress);
		CompanyVO.setCompanyRegisterTime(companyRegisterTime);
		dao.update(CompanyVO);
		return CompanyVO;
	}

	public void deleteCompany(Integer companyId) {
		dao.delete(companyId);
	}

	public CompanyVO getOneCompany(Integer companyId) {
		return dao.findByPK(companyId);
	}

	public List<CompanyVO> getAllCompany() {
		return dao.getAll();
	}

	// ginny新增
	public CompanyVO getOneCompanyByAccountPassword(String companyAccount, String companyPassword) {
		return dao.findByAccountPassword(companyAccount, companyPassword);
	}

	public CompanyVO getOneCompanyByAccount(String companyAccount) {
		return dao.findByAccount(companyAccount);
	}

}
