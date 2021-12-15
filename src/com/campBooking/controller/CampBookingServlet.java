package com.campBooking.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.json.JSONArray;
import org.json.JSONObject;

import com.camp.model.CampService;
import com.campArea.model.CampAreaService;
import com.campArea.model.CampAreaVO;
import com.campBooking.model.CampBookingService;

@WebServlet("/CampBookingServlet")
public class CampBookingServlet extends HttpServlet {

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
		res.setContentType("text/html;charset=UTF-8");
		PrintWriter out = res.getWriter();

////////////////////////////////////日曆載入空位資訊///////////////////////////////
		if ("calendar".equals(action)) {
			String today = req.getParameter("today");
			String campId = req.getParameter("campid");
			CampBookingService campbookingSvc = new CampBookingService();
			CampService campSvc = new CampService();

//step1 拿到當日日期，並且抓出半年期間的所有日期，
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = new java.util.Date();
			String dateToStr = dateFormat.format(date);
//			System.out.println(dateToStr);

			List list = campbookingSvc.CalendarUse(Integer.parseInt(campId), dateToStr, 3);
			JSONArray jsArray = new JSONArray(list);

			out.print(jsArray.toString());
			return;
		}

////////////////////////////////////抓取前端使用者的選擇日期已及天數///////////////////////////////

		if ("chooseday".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//step1 判斷使用者有無輸入資料，沒有就送提示
			String campId = req.getParameter("campid");
			String datechoose = req.getParameter("datechoose");
			String dayschoose = req.getParameter("dayschoose");

//step2 查詢營地的每個營位資料
			CampAreaService campareaSvc = new CampAreaService();
			CampService campSvc = new CampService();
			CampBookingService campbookingSvc = new CampBookingService();
			// 檢查有無此營位
			if (campId != null && datechoose != null && dayschoose != null) {
				List<CampAreaVO> list = campareaSvc.findCampAreaByCampId(Integer.parseInt(campId));
				if (list == null) {
					errorMsgs.add("查無資料");
					return;
				}
				// 檢查選擇的日期區間是否有是公休日或客滿
				java.util.Date start = new java.util.Date(java.sql.Date.valueOf(datechoose).getTime());
				java.util.Date end = new java.util.Date(java.sql.Date.valueOf(datechoose).getTime()
						+ 24 * 60 * 60 * 1000 * Integer.parseInt(dayschoose));
				List<java.util.Date> datelist = util.DiffDays.getDates(start, end);
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (java.util.Date date : datelist) {
					Map<String, Integer> map = campbookingSvc.findByAllArea(Integer.parseInt(campId), sdf.format(date));
					if (map.get(sdf.format(date)) <= 0) {
						errorMsgs.add("不好意思，選擇日期已滿");
					}

				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(req.getRequestURI());
					failureView.forward(req, res);
					return;// 程式中斷
				}

				req.setAttribute("campVO", campSvc.findCampByCampId(Integer.parseInt(campId)));
				req.setAttribute("list", list);
				req.setAttribute("date", datechoose);
				String days = "";
				switch (dayschoose) {
				case "1":
					days = "2天1夜";
					break;
				case "2":
					days = "3天2夜";
					break;
				case "3":
					days = "4天3夜";
					break;
				case "4":
					days = "5天4夜";

				}
				req.setAttribute("days", days);
				// Send the Success view
				String url = "/front_end/camp/campBooking01.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;
			}

		}

		if ("confirmseat".equals(action)) {
			/////////////////////// 驗證有無登入///////////////////////////////
			HttpSession session = req.getSession();
			Object account = session.getAttribute("account");
			if (account == null) {
				session.setAttribute("location", req.getRequestURI());
//				res.sendRedirect(req.getContextPath()+"/login.html");
//				return;
			}

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			//////////////////////////// 開始抓前端輸入的參數//////////////////////////////////////
			Map<String, String[]> map = req.getParameterMap();
			if (map == null) {
				errorMsgs.add("查無資料");
				return;
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher(req.getRequestURI());
				failureView.forward(req, res);
				return;// 程式中斷
			}

			String campId = req.getParameter("campId");
			String chooseDate = req.getParameter("chooseDate");
			String chooseDay = req.getParameter("chooseDay");
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String begin = sdf.format(java.sql.Date.valueOf("2" + chooseDate).getTime());
			String end = sdf.format(java.sql.Date.valueOf("2" + chooseDate).getTime()
					+ 24 * 60 * 60 * 1000 * (Integer.parseInt(chooseDay) + 1));
			req.setAttribute("beginDate", begin);
			req.setAttribute("endDate", end);

			CampService campSvc = new CampService();
			req.setAttribute("campVO", campSvc.findCampByCampId(Integer.parseInt(campId)));
			int size = req.getParameterValues("campAreaId").length;
//			System.out.println("size="+size);
			Set keyset = map.keySet();
			Iterator it;
			Map<String, String> ordermap;
			List<Map> orderlist = new ArrayList();
			for (int i = 0; i <size; i++) {
				ordermap = new HashMap<String, String>();
				it = keyset.iterator();
				while (it.hasNext()) {
					String name = (String) it.next();
					if (name.equals("action") || name.equals("campId") || name.equals("chooseDate")
							|| name.equals("chooseDay")) {
						continue;
					}

					String[] values =(String[])map.get(name);

					String val=values[i];
					ordermap.put(name, val);
				}
				
				orderlist.add(ordermap);

			}
//System.out.println(Arrays.toString(orderlist.toArray()));
			session.setAttribute("seatlist", orderlist);

			String url = "/front_end/camp/campBooking02.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;

		}
		    
		if("memberInfo".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			//取得帳號名稱
			String account=req.getParameter("account");
			System.out.println(account);
			
			if(account==null) {
				errorMsgs.add("請登入會員，謝謝");
			}
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher(req.getRequestURI());
				failureView.forward(req, res);
				return;// 程式中斷
			}
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
