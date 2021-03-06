package com.camp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camp.model.CampService;
import com.camp.model.CampVO;
import com.campArea.model.CampAreaService;
import com.campArea.model.CampAreaVO;
import com.campOrder.model.CampOrderService;
import com.campOrder.model.CampOrderVO;
import com.campTag.model.CampTagService;
import com.campTagDetail.model.CampTagDetailService;
import com.campTagDetail.model.CampTagDetailVO;
import com.favoriteCamp.model.FavoriteCampService;
import com.favoriteCamp.model.FavoriteCampVO;
import com.member.model.MemberVO;

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

		if ("islogin".equals(action)) {

			HttpSession session = req.getSession();
			Map loginMap=new HashMap();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

			if (memberVO != null) {
				Integer memberid = memberVO.getMemberId();
				
				FavoriteCampService favoritecampSvc=new FavoriteCampService();
				List<FavoriteCampVO> favorlist=favoritecampSvc.findBymemberId(memberid);
			loginMap.put("memberid",memberid);
			loginMap.put("favorlist",favorlist);
			JSONObject jObject=new JSONObject(loginMap);
				
				out.print(jObject);
				
				
				
				return;
			}else {
				loginMap.put("not-login","not-login");
				JSONObject jObject=new JSONObject(loginMap);			
				out.print(jObject);
				return;
				
			}
		}

////////////////////////////////////////////??????????????????????????????/////////////////////////////////////////////////////////////////////////
		if ("hotcamp".equals(action)) {

			/*************************** ?????????????????? ****************************************/
//step1 ???????????????????????????id
			CampOrderService camporderSvc = new CampOrderService();
			CampTagService camptagSvc = new CampTagService();
			CampTagDetailService camptagdetailSvc = new CampTagDetailService();
			CampService campSvc = new CampService();

			List<Integer> hotlist = camporderSvc.findhotcamp();
			List<CampWindow> addlist = new ArrayList<CampWindow>();
			CampWindow window = null;
//step2 ??????????????????id???????????????????????????
			for (Object campId : hotlist) {
				window = new CampWindow();
				Integer Id = (Integer) campId;
				CampVO hotcampData = campSvc.findCampByCampId(Id);
				window.setCampId(Id);
				window.setAddress(hotcampData.getCampAddress());
				String Base64Str = Base64.getEncoder().encodeToString(hotcampData.getCampPic1());
				window.setImgBase64(Base64Str);
				window.setName(hotcampData.getCampName());
//step3 ?????????????????????????????????????????????
				List<Integer> camptags = camptagdetailSvc.findCampTagsByCampId(Id);
				List<String> camptagsName = new ArrayList<String>();
				for (Integer i : camptags) {
					camptagsName.add(camptagSvc.getOneTag(i).getCampTagName());
				}

				window.setTags(camptagsName);

				// step4:????????????josn
				addlist.add(window);

			}

			JSONArray jsArray = new JSONArray(addlist);

			res.getWriter().print(jsArray);
			return;
		}

////////////////////////////////////////////????????????????????????????????????/////////////////////////////////////////////////////////////////////////

		if ("selectedcamp".equals(action)) {

			String[] section = req.getParameterValues("section");
			String[] feature = req.getParameterValues("feature");
			String orderby = req.getParameter("orderby");

			/*************************** ?????????????????? ****************************************/
			CampTagDetailService camptagdetailSvc = new CampTagDetailService();
			CampTagService camptagSvc = new CampTagService();
			CampService campSvc = new CampService();

			/*************************** ???????????????????????? ****************************************/

//step1 ??????????????????????????????
			List<CampVO> list = campSvc.getAllCamp(Integer.parseInt(orderby));
//step2 ??????????????????1.?????? 2.???????????? ????????????
			Set<Integer> tagset = new HashSet<Integer>();
			// ????????????????????????????????????
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
//step3 ???????????????????????????
			if (result != null) {

//step4 ??????????????????????????????????????????bean??????????????????????????????
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
			} else {

				out.print("????????????");
			}
		}

////////////////////////////////////////////Search Bar??????/////////////////////////////////////////////////////////////////////////

		if ("searchbar".equals(action)) {
			CampTagDetailService camptagdetailSvc = new CampTagDetailService();
			CampTagService camptagSvc = new CampTagService();
			CampService campSvc = new CampService();
//step1 ????????????????????????????????????????????????
			String searchtext = req.getParameter("searchtext");
			System.out.println(searchtext);
			if (searchtext != null && (searchtext.trim()).length() != 0) {
				List<CampVO> list = campSvc.findCampByKeyWord(searchtext);
				CampWindow window = null;
				List<CampWindow> searchlist = new ArrayList<CampWindow>();
//step2 ??????CampVO???????????????????????????
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
				System.out.println("?????????????????????");

			}

		}

		if ("detailcamp".equals(action)) {
			CampTagDetailService camptagdetailSvc = new CampTagDetailService();
			CampTagService camptagSvc = new CampTagService();
			CampService campSvc = new CampService();
			CampAreaService campareaSvc = new CampAreaService();
			CampOrderService camporderSvc = new CampOrderService();
			List detaillist = new ArrayList();
//step1 ???????????????campid????????????????????????
			String campId = req.getParameter("campid");
			System.out.println(campId);
//step2 ??????????????????
			CampVO camp = null;
			camp = campSvc.findCampByCampId(Integer.parseInt(campId));
			try {
//step3 ??????????????????????????????????????????
				List<Integer> taglist = camptagdetailSvc.findCampTagsByCampId(camp.getCampId());
//step4 ????????????????????????????????????
				List<String> tagnameslist = new ArrayList<String>();
				for (Integer tagnumber : taglist) {
					String tagname = camptagSvc.getOneTag(tagnumber).getCampTagName();
					tagnameslist.add(tagname);
				}

//step5 ??????????????????
				List<CampAreaVO> arealist = campareaSvc.findCampAreaByCampId(camp.getCampId());

//step6 ????????????????????????????????????
				List<CampOrderVO> orderlist = camporderSvc.OrderByCommented(camp.getCampId());

//step7 ?????????????????????????????????

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

		if ("getallcamp".equals(action)) {
			Integer rows = Integer.parseInt(req.getParameter("rows")); // ???????????????????????????
			Integer callpage = Integer.parseInt(req.getParameter("callpage")); // ????????????
			System.out.println(rows);
			System.out.println(callpage);

			CampService campSvc = new CampService();
			Map pagemap = null;
			CampWindow window = null;
			CampTagDetailService camptagdetailSvc = new CampTagDetailService();
			CampTagService camptagSvc = new CampTagService();
			Map outdata = new HashMap();
			pagemap = campSvc.showPage(rows, 1, callpage);
			List<CampWindow> pageout = new ArrayList<CampWindow>();
			if (pagemap != null || pagemap.size() < rows) {
				List<CampVO> pagelist = (List<CampVO>) pagemap.get("pagedata");
				Integer allpage = (Integer) pagemap.get("allpage");

				for (CampVO obj : pagelist) {
					window = new CampWindow();
					window.setCampId(obj.getCampId());
					window.setName(obj.getCampName());
					window.setAddress(obj.getCampAddress());
//					String Base64Str = Base64.getEncoder().encodeToString(obj.getCampPic1());
					window.setImgBase64("noPic");
					List<Integer> camptags = camptagdetailSvc.findCampTagsByCampId(obj.getCampId());
					List<String> camptagsName = new ArrayList<String>();
					for (Integer i : camptags) {
						camptagsName.add(camptagSvc.getOneTag(i).getCampTagName());
					}
					window.setTags(camptagsName);
					pageout.add(window);
					System.out.println("window??????" + window);
				}
				outdata.put("camplist", pageout);
				outdata.put("allpage", allpage);
				JSONObject jsonobj = new JSONObject(outdata);
				out.print(jsonobj);
			} else {
				out.print("endpage");

			}

		}

		if ("recommend".equals(action)) {

			CampService campSvc = new CampService();

			List<CampVO> list = campSvc.recommendCamp(3);
			System.out.println("???????????????" + list.size());
			JSONArray jsArray = new JSONArray(list);
			out.print(jsArray);

		}

	}

}
