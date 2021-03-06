
package com.camp.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.company.model.CompanyDAO;
import com.company.model.CompanyDAOImpl;
import com.company.model.CompanyVO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CampService {
	private CampDAO campdao;
	private CompanyDAO companydao;

	public CampService() {
		campdao = new CampDAOImpl();
		companydao = new CompanyDAOImpl();

	}

	// 1.新增營位
	public void insertCamp(CampVO campVO) {

		campdao.insert(campVO);

	};

	// 2.刪除營地
	public void deleteCamp(Integer camp) {

		campdao.delete(camp);
	}

	// 3.更新營地
	public void updateCamp(CampVO campVO) {
		campdao.update(campVO);

	};

	// 4.查詢某個營地
	public CampVO getOneCamp(Integer camp) {
		return campdao.findByPrimaryKey(camp);
	}

	// 5.依營狀態查詢營地
	public List<CampVO> camplist(Integer campstatus, Date startime, Date endtime, String campName) {

		return campdao.camplist(campstatus, startime, endtime, campName);

	}

	// 6.查詢全部營地
	public List<CampVO> getAll() {

		return campdao.getAll();
	}

	// 7.更新營地審核上架欄位 12/7新增
	public void updateCampCertificatenum(CampVO campVO, CompanyVO companyVO) throws Exception {

		campdao.updateCampCheck(campVO);

	}

	// 8.查詢營地上架審核欄位 12/7新增
	public CampVO selectCampCheck(Integer campId) {

		return campdao.getSelectStmt(campId);

	}

	// 9.查詢營地上架所有審核欄位 12/21新增
	public List<CampVO> selectAllCampCheck(String companyName) {

		return campdao.selectAllCampCheck(companyName);

	}

//查詢營地資料
	public CampVO findCampByCampId(Integer campId) {

		if (campId != null) {
			return campdao.findByPrimaryKey(campId);

		} else {
			return null;
		}

	}

//查詢廠商底下的營地(廠商只能看自己營地)

	public Set<CampVO> findCampByCompanyId(Integer companyId) {
		List<CampVO> list = campdao.getall(0);
		Set<CampVO> set = new HashSet<CampVO>();
		for (CampVO item : list) {
			if (item.getCompanyId() == companyId) {
				set.add(item);
			}
		}
		return set;
	}

//查詢全部營地
	public List<CampVO> getAllCamp(Integer order) {

		return campdao.getall(order);
	}

//查詢關鍵字搜尋營地
	public List<CampVO> findCampByKeyWord(String words) {

		return campdao.findByKeyWord(words);
	}

	// 更新營地資料(不能更改的有1.營地上架時間，2.營地申請上架時間)
	// 刪除營地用狀態改變方式:下架
	public void update(Integer campId, Integer companyId, Integer campStatus, String campName, String campRule,
			byte[] campPic1, byte[] campPic2, byte[] campPic3, byte[] campPic4, byte[] campPic5, String campAddress,
			String campPhone, String certificateNum, byte[] certificatePic, Timestamp campLaunchedTime,
			Timestamp campAppliedLaunchTime, BigDecimal longitude, BigDecimal lattitude) {

		CampVO campvo = campdao.findByPrimaryKey(campId);
		campvo.setCompanyId(companyId);
		campvo.setCampStatus(campStatus);
		campvo.setCampName(campName);
		campvo.setCampRule(campRule);
		campvo.setCampPic1(campPic1);
		campvo.setCampPic2(campPic2);
		campvo.setCampPic3(campPic3);
		campvo.setCampPic4(campPic4);
		campvo.setCampPic5(campPic5);
		campvo.setCampAddress(campAddress);
		campvo.setCampPhone(campPhone);
		campvo.setCertificateNum(certificateNum);
		campvo.setCertificatePic(certificatePic);
		campvo.setLongitude(longitude);
		campvo.setLattitude(lattitude);
		campdao.update(campvo);

	}


//請求分頁	每頁欲顯示比數rows   請求頁數reqpage    營地狀態status
	public Map showPage(Integer rows, Integer status, Integer reqpage) {
		Map pagemap = null;
		pagemap = campdao.getAllByPage(rows, status, reqpage);
		if (pagemap != null||pagemap.isEmpty()) {
			return pagemap;
		} else {
			return null;
		}


	}
	
	//推薦營地用(前台)
	public List<CampVO> recommendCamp(int campnumber){
		List<CampVO> list=campdao.getall(2);
		int size=list.size();
		System.out.println("拿到全部的"+size);
		Set<Integer> randomSet=new HashSet();
		//產生N個1~size的不重複隨機亂數
		while(randomSet.size()<campnumber) {
		int randomNum=(int)(Math.random()*size)+1; //產生1~size的隨機亂數
		randomSet.add(randomNum);
		System.out.println("查看亂數"+randomNum);
		}
		
		//把陣列的CampVO用上面的亂數取出
		List<CampVO> randomlist=new ArrayList<CampVO>();
		for(Integer i:randomSet) {
			
			randomlist.add(list.get(i-1));	
		}
		
		return randomlist;

	}

}
