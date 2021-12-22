package com.camp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.camp.model.CampService;
import com.camp.model.CampVO;
import com.campArea.model.CampAreaService;
import com.campArea.model.CampAreaVO;
import com.campOrder.model.CampOrderService;
import com.campOrder.model.CampOrderVO;
import com.campTag.model.CampTagService;
import com.campTagDetail.model.CampTagDetailService;
import com.campTagDetail.model.CampTagDetailVO;

@WebServlet("/CampServlet2")

public class CampServlet2 extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		System.out.println(action);

		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "300");
		res.setHeader("Access-Control-Allow-Headers", "content-type, x-requested-with");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = res.getWriter();


////////////////////////////////////////////首頁載入熱門營地八大/////////////////////////////////////////////////////////////////////////
		if ("hotcamp".equals(action)) {

			/*************************** 開始查詢資料 ****************************************/
//step1 從訂單找熱門營地的id
			CampOrderService camporderSvc = new CampOrderService();
			CampTagService camptagSvc = new CampTagService();
			CampTagDetailService camptagdetailSvc = new CampTagDetailService();
			CampService campSvc = new CampService();

			List<Integer> hotlist = camporderSvc.findhotcamp();
			List<CampWindow> addlist = new ArrayList<CampWindow>();
			CampWindow window = null;
//step2 從上面找到的id去找營地的基本資料
			for (Object campId : hotlist) {
				window = new CampWindow();
				Integer Id = (Integer) campId;
				CampVO hotcampData = campSvc.findCampByCampId(Id);
				window.setCampId(Id);
				window.setAddress(hotcampData.getCampAddress());
				String Base64Str = Base64.getEncoder().encodeToString(hotcampData.getCampPic1());
				window.setImgBase64(Base64Str);
				window.setName(hotcampData.getCampName());
//step3 查詢這個營地的標籤轉成標籤名稱
				List<Integer> camptags = camptagdetailSvc.findCampTagsByCampId(Id);
				List<String> camptagsName = new ArrayList<String>();
				for (Integer i : camptags) {
					camptagsName.add(camptagSvc.getOneTag(i).getCampTagName());
				}

				window.setTags(camptagsName);

				// step4:物件轉換josn
				addlist.add(window);

			}

			JSONArray jsArray = new JSONArray(addlist);

			res.getWriter().print(jsArray);
			return;
		}

////////////////////////////////////////////第二頁載入使用者篩選結果/////////////////////////////////////////////////////////////////////////

		if ("selectedcamp".equals(action)) {

			String[] section = req.getParameterValues("section");
			String[] feature = req.getParameterValues("feature");
			String orderby = req.getParameter("orderby");

			/*************************** 開始查詢資料 ****************************************/
			CampTagDetailService camptagdetailSvc = new CampTagDetailService();
			CampTagService camptagSvc = new CampTagService();
			CampService campSvc = new CampService();
			
			
			/*************************** 分業顯示全部營地 ****************************************/
		
//step1 根據排序查詢全部營地
			List<CampVO> list = campSvc.getAllCamp(Integer.parseInt(orderby));
//step2 根據搜尋條件1.地區 2.特色標籤 開始篩選
			Set<Integer> tagset = new HashSet<Integer>();
			// 把標籤全部放進一個集合內
			if (section != null && section.length != 0) {
				for (String item : section) {
					tagset.add(Integer.parseInt(item) + 1);
				}
			}
			if (feature != null && feature.length != 0) {
				for (String item : feature) {
					tagset.add(Integer.parseInt(item) + 5);
				}
			}

			Set<CampTagDetailVO> result = camptagdetailSvc.findByMultireq(tagset);
//step3 檢查返回是否有資料
			if (result != null) {

//step4 把篩選的條件加入，目標營地的bean抽出，開始包裝資料喽
				List<CampWindow> selelist = new ArrayList<CampWindow>();
				CampWindow window = null;
				for (CampVO obj : list) {
					for (CampTagDetailVO tags : result) {
						if (obj.getCampId() == tags.getCampId()) {
							window = new CampWindow();
							window.setCampId(obj.getCampId());
							window.setName(obj.getCampName());
							window.setAddress(obj.getCampAddress());
							String Base64Str = Base64.getEncoder().encodeToString(obj.getCampPic1());
							window.setImgBase64(Base64Str);
							List<Integer> camptags = camptagdetailSvc.findCampTagsByCampId(obj.getCampId());
							List<String> camptagsName = new ArrayList<String>();
							for (Integer i : camptags) {
								camptagsName.add(camptagSvc.getOneTag(i).getCampTagName());
							}
							window.setTags(camptagsName);
							selelist.add(window);
						}
					}
				}
				JSONArray jsArray = new JSONArray(selelist);

				out.print(jsArray);
				return;
			}else {

			out.print("查無資料");
			}
		}

////////////////////////////////////////////Search Bar功能/////////////////////////////////////////////////////////////////////////

		if ("searchbar".equals(action)) {
			CampTagDetailService camptagdetailSvc = new CampTagDetailService();
			CampTagService camptagSvc = new CampTagService();
			CampService campSvc = new CampService();
//step1 根據使用者輸入關鍵字查詢相關營地
			String searchtext = req.getParameter("searchtext");
			System.out.println(searchtext);
			if (searchtext != null && (searchtext.trim()).length() != 0) {
				List<CampVO> list = campSvc.findCampByKeyWord(searchtext);
				CampWindow window = null;
				List<CampWindow> searchlist = new ArrayList<CampWindow>();
//step2 取得CampVO陣列開始包裝資料喽
				for (CampVO obj : list) {
					window = new CampWindow();
					window.setCampId(obj.getCampId());
					window.setName(obj.getCampName());
					window.setAddress(obj.getCampAddress());
					String Base64Str = Base64.getEncoder().encodeToString(obj.getCampPic1());
					window.setImgBase64(Base64Str);
					List<Integer> camptags = camptagdetailSvc.findCampTagsByCampId(obj.getCampId());
					List<String> camptagsName = new ArrayList<String>();
					for (Integer i : camptags) {
						camptagsName.add(camptagSvc.getOneTag(i).getCampTagName());
					}
					window.setTags(camptagsName);
					searchlist.add(window);
				}
				JSONArray jsArray = new JSONArray(searchlist);
				out.print(jsArray);

			} else {
				System.out.println("請輸入正確格式");

			}

		}

		if ("detailcamp".equals(action)) {
			CampTagDetailService camptagdetailSvc = new CampTagDetailService();
			CampTagService camptagSvc = new CampTagService();
			CampService campSvc = new CampService();
			CampAreaService campareaSvc = new CampAreaService();
			CampOrderService camporderSvc = new CampOrderService();
			List detaillist = new ArrayList();
//step1 根據查詢的campid查詢相關營地資料
			String campId = req.getParameter("campid");
			System.out.println(campId);
//step2 查詢營地資料
			CampVO camp = null;
			camp = campSvc.findCampByCampId(Integer.parseInt(campId));
			try {
//step3 有找到營地後，開始找營地標籤
				List<Integer> taglist = camptagdetailSvc.findCampTagsByCampId(camp.getCampId());
//step4 營地標籤號碼轉換標籤名稱
				List<String> tagnameslist = new ArrayList<String>();
				for (Integer tagnumber : taglist) {
					String tagname = camptagSvc.getOneTag(tagnumber).getCampTagName();
					tagnameslist.add(tagname);
				}

//step5 查詢營位資料
				List<CampAreaVO> arealist = campareaSvc.findCampAreaByCampId(camp.getCampId());

//step6 查詢這個營地所有訂單評論
				List<CampOrderVO> orderlist = camporderSvc.OrderByCommented(camp.getCampId());

//step7 開始包裝營地營位資料喽

				detaillist.add(camp);
				detaillist.add(tagnameslist);
				detaillist.add(arealist);
				detaillist.add(orderlist);

			} catch (NullPointerException e) {
				e.printStackTrace();

			}
			JSONArray jsArray = new JSONArray(detaillist);
			res.getWriter().print(jsArray.toString());

		}

	}
}
