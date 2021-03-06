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

////////////////////////////////////????????????????????????///////////////////////////////
		if ("calendar".equals(action)) {
			String today = req.getParameter("today");
			String campId = req.getParameter("campid");
			CampBookingService campbookingSvc = new CampBookingService();
			CampService campSvc = new CampService();

//step1 ???????????????????????????????????????????????????????????????
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = new java.util.Date();
			String dateToStr = dateFormat.format(date);
			System.out.println(dateToStr);

			List list = campbookingSvc.CalendarUse(Integer.parseInt(campId), dateToStr, 3);
			JSONArray jsArray = new JSONArray(list);

			out.print(jsArray.toString());
			return;
		}

////////////////////////////////////????????????????????????????????????????????????///////////////////////////////

		if ("chooseday".equals(action)) {
			String errorCode = null;

//step1 ??????????????????????????????????????????????????????
			String campId = req.getParameter("campid");
			String datechoose = req.getParameter("datechoose");
			String dayschoose = req.getParameter("dayschoose");

//step2 ?????????????????????????????????
			CampAreaService campareaSvc = new CampAreaService();
			CampService campSvc = new CampService();
			CampBookingService campbookingSvc = new CampBookingService();
			// ?????????????????????
			if (campId != null && datechoose != null && dayschoose != null) {
				List<CampAreaVO> list = campareaSvc.findCampAreaByCampId(Integer.parseInt(campId));
				if (list == null) {
					errorCode = "1";
					
				}
				// ?????????????????????????????????????????????????????????
				java.util.Date start = new java.util.Date(java.sql.Date.valueOf(datechoose).getTime());
				java.util.Date end = new java.util.Date(java.sql.Date.valueOf(datechoose).getTime()
						+ 24 * 60 * 60 * 1000 * (Integer.parseInt(dayschoose) - 1));
				List<java.util.Date> datelist = util.DiffDays.getDates(start, end);
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (java.util.Date date : datelist) {
					Map<String, Integer> map = campbookingSvc.findByAllArea(Integer.parseInt(campId), sdf.format(date));
					
					if (map.get(sdf.format(date)) == 0) {
						System.out.println("???????????????????????????????????????");
						errorCode = "2";
					}
					if (map.get(sdf.format(date)) == -1) {
						System.out.println("????????????????????????????????????????????????");
						errorCode = "3";
					}

				}

				// Send the use back to the form, if there were errors
				if (errorCode != null) {
					res.sendRedirect(req.getContextPath() + "/front_end/camp/camp_calendar.html?campid=" + campId
							+ "&errorcode=" + errorCode);

					return;// ????????????
				}

				req.setAttribute("campVO", campSvc.findCampByCampId(Integer.parseInt(campId)));
				req.setAttribute("list", list);
				req.setAttribute("date", datechoose);
				String days = "";
				switch (dayschoose) {
				case "1":
					days = "2???1???";
					break;
				case "2":
					days = "3???2???";
					break;
				case "3":
					days = "4???3???";
					break;
				case "4":
					days = "5???4???";

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
			/////////////////////// ??????????????????///////////////////////////////

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

			//////////////////////////// ????????????//////////////////////////////////////

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
			//////////////////////////// >>??????????????????<</////////////////////////////////////
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
				errorMsgs.add("???????????????");

			}
			if (payertel == null || !payertel.matches("[0-9]{10}")) {
				errorMsgs.add("???????????????????????????");

			}
			if (email == null || !email.matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$")) {
				errorMsgs.add("???????????????email");

			}
			if (payername == null || payername.trim().length() == 0) {
				errorMsgs.add("????????????????????????");

			}
			if (payername.trim().length() > 10) {
				errorMsgs.add("???????????????????????????>10");

			}
			if (creditnumber == null || !creditnumber.matches("[0-9]{9,16}")) {
				errorMsgs.add("?????????????????????");

			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher(req.getRequestURI());
				failureView.forward(req, res);
				return;// ????????????
			}

			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

			ArrayList<Map> orderInfo = (ArrayList<Map>) session.getAttribute("seatlist");
			System.out.println("=============>>??????????????????????????????<<==============");
			Integer capitationNumforepay = 0; // ?????????
			List<String> listforepay = new ArrayList<String>(); //??????
			
			List<Map> maillist =new ArrayList<Map>();//email

			for (Map item : orderInfo) {
				String campAreaName = (String) item.get("campAreaName");
				String weekdayNum = (String) item.get("weekdayNum");
				String holidayNum = (String) item.get("holidayNum");
				Integer areaNumforepay = (Integer.parseInt(weekdayNum) + Integer.parseInt(holidayNum));

				capitationNumforepay += (Integer.parseInt((String) item.get("perCapitationNum")));
				String combine = campAreaName + areaNumforepay + "???";
				listforepay.add(combine);
				
				//??????email??????
				
				Map<String, String> eMap=new HashMap<String, String>();
				eMap.put("areaName", campAreaName);
				eMap.put("orderSeat", areaNumforepay+"???");
				eMap.put("personNum", (String)(item.get("perCapitationNum"))+"???");
				eMap.put("subtotal", (String)(item.get("subtotal")));
				maillist.add(eMap);
			}

			listforepay.add("????????????" + capitationNumforepay + "???");

			System.out.println("????????????????????????" + Arrays.toString(listforepay.toArray()));

			System.out.println("=============>>??????????????????<<==============");
			
			Integer campOrderTotalAmount = 0; // ???????????????

			List<CampAreaOrderDetailVO> orderdetailList = new ArrayList<CampAreaOrderDetailVO>();
			CampAreaOrderDetailVO campareaorderdetailVO = null;

			for (Map map : orderInfo) {
				Integer bookingQuantity = 0;// ????????????
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
					if ("subtotal".equals(name)) {// ???????????????
						String subtotal = (String) map.get(name);
						campOrderTotalAmount += Integer.parseInt(subtotal.substring(1));
					}
					// ????????????OK
					if ("weekdayNum".equals(name) || "holidayNum".equals(name)) {
						String seattotal = (String) map.get(name);
						bookingQuantity += Integer.parseInt(seattotal);
						campareaorderdetailVO.setBookingQuantity(bookingQuantity);

					}
					// ????????????OK
					if ("weekdayPrice".equals(name)) {
						String weekdayprice = (String) map.get(name);
						campareaorderdetailVO.setCampAreaWeekdayPrice(Integer.parseInt(weekdayprice));
					}

					// ????????????OK
					if ("holidayPrice".equals(name)) {
						String holidayprice = (String) map.get(name);
						campareaorderdetailVO.setCampAreaHolidayPrice(Integer.parseInt(holidayprice));
					}
					// ??????????????????OK
					if ("perCapitationNum".equals(name)) {
						String percapitationnum = (String) map.get(name);
						campareaorderdetailVO.setCapitationQuantity(Integer.parseInt(percapitationnum));
					}
					// ??????????????????OK
					if ("perCapitationFee".equals(name)) {
						String percapitationfee = (String) map.get(name);
						campareaorderdetailVO.setPerCapitationFee(Integer.parseInt(percapitationfee));
					}

					// ???????????????OK
					if ("campAreaId".equals(name)) {
						String campareaid = (String) map.get(name);
						campareaorderdetailVO.setCampAreaId(Integer.parseInt(campareaid));
					}

				}
//				// ????????????&????????????
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
				Integer bookingWeekdays = 0;// ??????????????????
				Integer bookingHolidays = 0;// ??????????????????
				Calendar cal = Calendar.getInstance();
				for (int i = 0; i < daysList.size() - 1; i++) {

					cal.setTime(daysList.get(i));
					// [ "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????" ];
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

/////////////////////////////////??????CampOrderVO//////////////////////////////
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
/////////////////////////////1.??????????????????2.??????????????????/////////////////////////////////////
			AllInOne all;
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			int b = camporderSvc.addOneOrder(camporderVO, orderdetailList); // ?????????????????????????????????
		
			if (b != 0) {
				//step1.???email????????????
				MailService mailService=new MailService();
				HttpSession sess = req.getSession();
				String campid2 = (String) session.getAttribute("campId");
				CampService campSvc = new CampService();
				String campName = campSvc.findCampByCampId(Integer.parseInt(campid2)).getCampName();
				mailService.sendMail(email,"CampingParadise ??????????????????",maillist,memberVO.getMemberName(),String.valueOf(campOrderTotalAmount),campName,campCheckInDate+"~"+campCheckOutDate);

				all = new AllInOne("");
				AioCheckOutALL obj = new AioCheckOutALL();
				obj.setMerchantTradeNo(String.valueOf(b) + "cp" + UUIDGenerator.getUUID()); // ??????id+cp+??????16???
				obj.setMerchantTradeDate(sd.format(new Date())); // ????????????
				obj.setTotalAmount(String.valueOf(campOrderTotalAmount)); // ???????????????
				obj.setTradeDesc("test Description"); // ????????????
				obj.setItemName(String.join("#", listforepay)); // ????????????
				obj.setReturnURL("https://ea62-220-138-21-49.ngrok.io/TFA104G5/EcpayReturn");
				obj.setClientBackURL("http://localhost:8081/TFA104G5/front_end/camp/redirect.jsp?orderid="
						+ String.valueOf(b) + "&tradetime=" + sd.format(new Date())); // ??????URL
				obj.setNeedExtraPaidInfo("N");

				System.out.println(obj);
				String form = all.aioCheckOut(obj, null);
				out.print(form);

			} else {

				errorMsgs.add("?????????????????????????????????");
				RequestDispatcher failureView = req.getRequestDispatcher(req.getRequestURI());
				
				failureView.forward(req, res);
				return;// ????????????
			}

		}
	}
}
