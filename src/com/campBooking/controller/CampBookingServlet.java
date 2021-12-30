package com.campBooking.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import com.camp.model.CampVO;
import com.campArea.model.CampAreaService;
import com.campArea.model.CampAreaVO;
import com.campAreaOrderDetail.model.CampAreaOrderDetailVO;
import com.campBooking.model.CampBookingService;
import com.campOrder.model.CampOrderService;
import com.campOrder.model.CampOrderVO;
import com.member.model.MemberVO;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import util.UUIDGenerator;

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

		if ("getcampdata".equals(action)) {
			String campid = req.getParameter("campid");
			CampService campSvc = new CampService();
			CampVO campVO = campSvc.findCampByCampId(Integer.parseInt(campid));

			JSONObject jobj = new JSONObject(campVO);
			out.print(jobj);

		}

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
			System.out.println(dateToStr);

			List list = campbookingSvc.CalendarUse(Integer.parseInt(campId), dateToStr, 3);
			JSONArray jsArray = new JSONArray(list);

			out.print(jsArray.toString());
			return;
		}

////////////////////////////////////抓取前端使用者的選擇日期已及天數///////////////////////////////

		if ("chooseday".equals(action)) {
			String errorCode = null;

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
					errorCode = "1";
					
				}
				// 檢查選擇的日期區間是否有是公休日或客滿
				java.util.Date start = new java.util.Date(java.sql.Date.valueOf(datechoose).getTime());
				java.util.Date end = new java.util.Date(java.sql.Date.valueOf(datechoose).getTime()
						+ 24 * 60 * 60 * 1000 * (Integer.parseInt(dayschoose) - 1));
				List<java.util.Date> datelist = util.DiffDays.getDates(start, end);
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (java.util.Date date : datelist) {
					Map<String, Integer> map = campbookingSvc.findByAllArea(Integer.parseInt(campId), sdf.format(date));
					
					if (map.get(sdf.format(date)) == 0) {
						System.out.println("不好意思，選擇日期區間已滿");
						errorCode = "2";
					}
					if (map.get(sdf.format(date)) == -1) {
						System.out.println("不好意思，選擇日期區間不開放營業");
						errorCode = "3";
					}

				}

				// Send the use back to the form, if there were errors
				if (errorCode != null) {
					res.sendRedirect(req.getContextPath() + "/front_end/camp/camp_calendar.html?campid=" + campId
							+ "&errorcode=" + errorCode);

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

			Object memberVO = session.getAttribute("memberVO");

			session.setAttribute("action", "confirmseat");
			session.setAttribute("campId", req.getParameter("campId"));
			session.setAttribute("chooseDate", req.getParameter("chooseDate"));
			session.setAttribute("chooseDay", req.getParameter("chooseDay"));
			session.setAttribute("campAreaId", req.getParameterValues("campAreaId"));
			Map<String, String[]> paymap = req.getParameterMap();
			int size = req.getParameterValues("campAreaId").length;

			Set keyset = paymap.keySet();
			
			Iterator it;
			Map<String, String> ordermap;
			List<Map> orderlist = new ArrayList();
			for (int i = 0; i < size; i++) {
				ordermap = new HashMap<String, String>();
				it = keyset.iterator();
				while (it.hasNext()) {
					String name = (String) it.next();

					if (name.equals("action") || name.equals("campId") || name.equals("chooseDate")
							|| name.equals("chooseDay")) {
						continue;
					}

					String[] values = (String[]) paymap.get(name);

					String val = values[i];

					ordermap.put(name, val);
				}

				orderlist.add(ordermap);

			}

			session.setAttribute("seatlist", orderlist);

			//////////////////////////// 參數驗證//////////////////////////////////////

			String campId = null, chooseDate = null, chooseDay = null;
			Map<String, String[]> map = null;

			campId = req.getParameter("campId");
			chooseDate = req.getParameter("chooseDate");
			chooseDay = req.getParameter("chooseDay");

			if (campId == null) {
				campId = (String) session.getAttribute("campId");
			}
			if (chooseDate == null) {
				chooseDate = (String) session.getAttribute("chooseDate");
			}
			if (chooseDay == null) {
				chooseDay = (String) session.getAttribute("chooseDay");
			}

			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String begin = sdf.format(java.sql.Date.valueOf("2" + chooseDate).getTime());
			String end = sdf.format(java.sql.Date.valueOf("2" + chooseDate).getTime()
					+ 24 * 60 * 60 * 1000 * (Integer.parseInt(chooseDay)));

			session.setAttribute("beginDate", begin);
			session.setAttribute("endDate", end);
			//////////////////////////// >>營地資料捕獲<</////////////////////////////////////
			CampService campSvc = new CampService();
			session.setAttribute("campVO", campSvc.findCampByCampId(Integer.parseInt(campId)));

			String url = "/front_end/camp/campBooking02.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;

		}

		if ("oneorder".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			Integer campId = Integer.parseInt(req.getParameter("campId"));
			String sex = req.getParameter("sex");
			String payertel = req.getParameter("payertel");
			String email = req.getParameter("email");
			String payername = req.getParameter("payername");
			String creditnumber = req.getParameter("creditnumber").replaceAll("\\s+", "");
			String campCheckInDate = req.getParameter("campCheckInDate");
			String campCheckOutDate = req.getParameter("campCheckOutDate");

			if (sex == null) {
				errorMsgs.add("請輸入性別");

			}
			if (payertel == null || !payertel.matches("[0-9]{10}")) {
				errorMsgs.add("請輸入正確手機號碼");

			}
			if (email == null || !email.matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$")) {
				errorMsgs.add("請輸入正確email");

			}
			if (payername == null || payername.trim().length() == 0) {
				errorMsgs.add("請輸入付款人姓名");

			}
			if (payername.trim().length() > 10) {
				errorMsgs.add("付款人姓名長度不能>10");

			}
			if (creditnumber == null || !creditnumber.matches("[0-9]{9,16}")) {
				errorMsgs.add("請輸入信用卡號");

			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher(req.getRequestURI());
				failureView.forward(req, res);
				return;// 程式中斷
			}

			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

			ArrayList<Map> orderInfo = (ArrayList<Map>) session.getAttribute("seatlist");
			System.out.println("=============>>綠界訂單明細商品名稱<<==============");
			Integer capitationNumforepay = 0; // 總金額
			List<String> listforepay = new ArrayList<String>(); //綠界
			
			List<Map> maillist =new ArrayList<Map>();//email

			for (Map item : orderInfo) {
				String campAreaName = (String) item.get("campAreaName");
				String weekdayNum = (String) item.get("weekdayNum");
				String holidayNum = (String) item.get("holidayNum");
				Integer areaNumforepay = (Integer.parseInt(weekdayNum) + Integer.parseInt(holidayNum));

				capitationNumforepay += (Integer.parseInt((String) item.get("perCapitationNum")));
				String combine = campAreaName + areaNumforepay + "帳";
				listforepay.add(combine);
				
				//包裝email資訊
				
				Map<String, String> eMap=new HashMap<String, String>();
				eMap.put("areaName", campAreaName);
				eMap.put("orderSeat", areaNumforepay+"帳");
				eMap.put("personNum", (String)(item.get("perCapitationNum"))+"人");
				eMap.put("subtotal", (String)(item.get("subtotal")));
				maillist.add(eMap);
			}

			listforepay.add("加購人頭" + capitationNumforepay + "人");

			System.out.println("綠界商品名稱包裝" + Arrays.toString(listforepay.toArray()));

			System.out.println("=============>>列印訂單明細<<==============");
			
			Integer campOrderTotalAmount = 0; // 訂單總金額

			List<CampAreaOrderDetailVO> orderdetailList = new ArrayList<CampAreaOrderDetailVO>();
			CampAreaOrderDetailVO campareaorderdetailVO = null;

			for (Map map : orderInfo) {
				Integer bookingQuantity = 0;// 訂帳數量
				campareaorderdetailVO = new CampAreaOrderDetailVO();
				Set<String> keyset = map.keySet();

				///////////////////////////////////////////
//				for (String i : keyset) {
//
//					String value = (String) map.get(i);
//					System.out.println(i + ":" + value);
//
//				}
				///////////////////////////////////////////
//				System.out.println("=================================");

				for (String name : keyset) {
					if ("subtotal".equals(name)) {// 計算總金額
						String subtotal = (String) map.get(name);
						campOrderTotalAmount += Integer.parseInt(subtotal.substring(1));
					}
					// 訂帳數量OK
					if ("weekdayNum".equals(name) || "holidayNum".equals(name)) {
						String seattotal = (String) map.get(name);
						bookingQuantity += Integer.parseInt(seattotal);
						campareaorderdetailVO.setBookingQuantity(bookingQuantity);

					}
					// 平日單價OK
					if ("weekdayPrice".equals(name)) {
						String weekdayprice = (String) map.get(name);
						campareaorderdetailVO.setCampAreaWeekdayPrice(Integer.parseInt(weekdayprice));
					}

					// 假日單價OK
					if ("holidayPrice".equals(name)) {
						String holidayprice = (String) map.get(name);
						campareaorderdetailVO.setCampAreaHolidayPrice(Integer.parseInt(holidayprice));
					}
					// 加購人頭數量OK
					if ("perCapitationNum".equals(name)) {
						String percapitationnum = (String) map.get(name);
						campareaorderdetailVO.setCapitationQuantity(Integer.parseInt(percapitationnum));
					}
					// 加購人頭單價OK
					if ("perCapitationFee".equals(name)) {
						String percapitationfee = (String) map.get(name);
						campareaorderdetailVO.setPerCapitationFee(Integer.parseInt(percapitationfee));
					}

					// 營位流水號OK
					if ("campAreaId".equals(name)) {
						String campareaid = (String) map.get(name);
						campareaorderdetailVO.setCampAreaId(Integer.parseInt(campareaid));
					}

				}
//				// 假日天數&平日天數
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date checkinDate = null;
				java.util.Date checkoutDate = null;
				try {
					checkinDate = sdf.parse(campCheckInDate);
					checkoutDate = sdf.parse(campCheckOutDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				List<java.util.Date> daysList = util.DiffDays.getDates(checkinDate, checkoutDate);
//				for (Date date : daysList) {
//				
//					System.out.println(sdf.format(date));
//
//				}
				Integer bookingWeekdays = 0;// 訂位平日天數
				Integer bookingHolidays = 0;// 訂位假日天數
				Calendar cal = Calendar.getInstance();
				for (int i = 0; i < daysList.size() - 1; i++) {

					cal.setTime(daysList.get(i));
					// [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ];
					int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
					
					if (w <= 0 || w == 5 || w == 6) {
						bookingHolidays++;
					} else {
						bookingWeekdays++;
					}
				}

				campareaorderdetailVO.setBookingHolidays(bookingHolidays);
				campareaorderdetailVO.setBookingWeekdays(bookingWeekdays);

				orderdetailList.add(campareaorderdetailVO);

			}

/////////////////////////////////包裝CampOrderVO//////////////////////////////
			CampOrderService camporderSvc = new CampOrderService();
			CampOrderVO camporderVO = new CampOrderVO();

			camporderVO.setCampId(campId);
			camporderVO.setMemberId(memberVO.getMemberId());
			camporderVO.setCampOrderTotalAmount(campOrderTotalAmount);
			camporderVO.setCampCheckOutDate(java.sql.Date.valueOf(campCheckOutDate));
			camporderVO.setCampCheckInDate(java.sql.Date.valueOf(campCheckInDate));
			camporderVO.setCreditCardNum(creditnumber);
			camporderVO.setPayerName(payername);
			camporderVO.setPayerPhone(payertel);
/////////////////////////////1.產生訂單成功2.轉交綠界支付/////////////////////////////////////
			AllInOne all;
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			int b = camporderSvc.addOneOrder(camporderVO, orderdetailList); // 產生一筆訂單，回傳方法
		
			if (b != 0) {
				//step1.寄email訂單成立
				MailService mailService=new MailService();
				HttpSession sess = req.getSession();
				String campid2 = (String) session.getAttribute("campId");
				CampService campSvc = new CampService();
				String campName = campSvc.findCampByCampId(Integer.parseInt(campid2)).getCampName();
				mailService.sendMail(email,"CampingParadise 訂單成立通知",maillist,memberVO.getMemberName(),String.valueOf(campOrderTotalAmount),campName,campCheckInDate+"~"+campCheckOutDate);

				all = new AllInOne("");
				AioCheckOutALL obj = new AioCheckOutALL();
				obj.setMerchantTradeNo(String.valueOf(b) + "cp" + UUIDGenerator.getUUID()); // 訂單id+cp+亂碼16位
				obj.setMerchantTradeDate(sd.format(new Date())); // 交易時間
				obj.setTotalAmount(String.valueOf(campOrderTotalAmount)); // 訂單總金額
				obj.setTradeDesc("test Description"); // 訂單描述
				obj.setItemName(String.join("#", listforepay)); // 商品項目
				obj.setReturnURL("https://ea62-220-138-21-49.ngrok.io/TFA104G5/EcpayReturn");
				obj.setClientBackURL("http://localhost:8081/TFA104G5/front_end/camp/redirect.jsp?orderid="
						+ String.valueOf(b) + "&tradetime=" + sd.format(new Date())); // 回傳URL
				obj.setNeedExtraPaidInfo("N");

				System.out.println(obj);
				String form = all.aioCheckOut(obj, null);
				out.print(form);

			} else {

				errorMsgs.add("系統繁忙中，請重新確認");
				RequestDispatcher failureView = req.getRequestDispatcher(req.getRequestURI());
				
				failureView.forward(req, res);
				return;// 程式中斷
			}

		}
	}
}
