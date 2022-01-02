package com.camp.controller;

import java.io.IOException;

import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.camp.model.CampService;
import com.camp.model.CampVO;
import com.campTagDetail.model.CampTagDetailService;
import com.campTagDetail.model.CampTagDetailVO;
import com.company.model.*;


@MultipartConfig
public class CampServlet extends HttpServlet {
	private CampService campService;

	private CampTagDetailService campTagDetailService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8459587984407557206L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		campService = new CampService();
		campTagDetailService = new CampTagDetailService();
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		HttpSession session = req.getSession();
		String action = req.getParameter("action");

		// 新增營地資料
		if ("INSERT".equals(action)) {

			CampVO campVO = new CampVO();// 1.先宣告CampVO,預存傳入參數

			List<String> errorMsgs = new ArrayList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String campanyId = req.getParameter("campany_id");
				campVO.setCompanyId(Integer.valueOf(campanyId));
				Timestamp ts = new Timestamp(new Date().getTime());
				campVO.setCampLaunchedTime(ts);
				campVO.setCampAppliedLaunchTime(ts);

				String campStatus = req.getParameter("camp_status");
				campVO.setCampStatus(Integer.valueOf(campStatus));
				String campName = req.getParameter("camp_name");// 2.宣告campName變數,存camp_name參數
				if (campName == null || (campName.trim()).length() == 0) {
					errorMsgs.add("營地名稱:請勿空白");// 3.判斷傳入參數是否為空值
				} else {
					campVO.setCampName(campName);// 4.將campName參數存入campVO
				}

				String campPhone = req.getParameter("camp_phone");
				if (campPhone == null || (campPhone.trim()).length() == 0) {
					errorMsgs.add("營地電話:請勿空白");
				} else {
					campVO.setCampPhone(campPhone);
				}

				String longitude = req.getParameter("longitude");
				BigDecimal bigDecimalLongitude = new BigDecimal(longitude);
				if (longitude == null || (longitude.trim()).length() == 0) {
					errorMsgs.add("緯度:請勿空白");
				} else {
					campVO.setLongitude(bigDecimalLongitude);
				}

				String lattitude = req.getParameter("lattitude");
				BigDecimal bigDecimalLattitude = new BigDecimal(lattitude);
				if (lattitude == null || (lattitude.trim()).length() == 0) {
					errorMsgs.add("經度:請勿空白");
				} else {
					campVO.setLattitude(bigDecimalLattitude);
				}

				String campAddress = req.getParameter("campAddress");
				if (campAddress == null || (campAddress.trim()).length() == 0) {
					errorMsgs.add("地址:請勿空白");
				} else {
					campVO.setCampAddress(campAddress);
				}

				String campDiscription = req.getParameter("campDiscription");
				if (campDiscription == null || (campDiscription.trim()).length() == 0) {
					errorMsgs.add("營地敘述:請勿空白");
				} else {
					campVO.setCampDiscription(campDiscription);
				}

				String campRule = req.getParameter("campRule");
				if (campRule == null || (campRule.trim()).length() == 0) {
					errorMsgs.add("營地租借規則:請勿空白");
				} else {
					campVO.setCampRule(campRule);
				}
				
				byte[] campPic1 = null;
				byte[] campPic2 = null;
				byte[] campPic3 = null;
				byte[] campPic4 = null;
				byte[] campPic5 = null;
				Part parts1 = req.getPart("camp_pic1");
				Part parts2 = req.getPart("camp_pic2");
				Part parts3 = req.getPart("camp_pic3");
				Part parts4 = req.getPart("camp_pic4");
				Part parts5 = req.getPart("camp_pic5");

				if (parts1.getInputStream().available() != 0) {
					campPic1 = getBytesFromPart(parts1);
					campVO.setCampPic1(campPic1);
				}

				if (parts2.getInputStream().available() != 0) {
					campPic2 = getBytesFromPart(parts2);
					campVO.setCampPic2(campPic2);
				}
				if (parts3.getInputStream().available() != 0) {
					campPic3 = getBytesFromPart(parts3);
					campVO.setCampPic3(campPic3);
				}
				
				if (parts4.getInputStream().available() != 0) {
					campPic4 = getBytesFromPart(parts4);
					campVO.setCampPic4(campPic4);
				}

				if (parts5.getInputStream().available() != 0) {
					campPic5 = getBytesFromPart(parts5);
					campVO.setCampPic5(campPic5);
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/camp/insertCampShelves.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				// 新增營地後,執行查詢
				CampService campSvc = new CampService();
				campSvc.insertCamp(campVO);
				req.setAttribute("list", campSvc.getAll());
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				String url = "/back_end/camp/selectCamp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交campShelves.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.getStackTrace();
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/camp/insertCampShelves.jsp");
				failureView.forward(req, res);
			}
		}

		/*************************** 新增營地跳轉畫面 *************************************/
		if (action.equals("INSERTCAMP")) {

			List<CampTagDetailVO> campTagDetailList = campTagDetailService.getAll();
			req.setAttribute("campTagDetailList", campTagDetailList);

			String url = "/back_end/camp/insertCampShelves.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);

		}

		/*************************** 更新營地 *************************************/
		if ("UPDATE".equals(action)) {

			CampVO campVO = new CampVO();// 1.先宣告CampVO,預存傳入參數

			List<String> errorMsgs = new ArrayList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				String campLaunchedTime = req.getParameter("campLaunchedTime");

				campVO.setCampLaunchedTime(Timestamp.valueOf(campLaunchedTime + " 00:00:00.0"));

				String campanyId = req.getParameter("campany_id");
				campVO.setCompanyId(Integer.valueOf(campanyId));

				String campAppliedLaunchTime = req.getParameter("campAppliedLaunchTime");

				campVO.setCampAppliedLaunchTime(Timestamp.valueOf(campAppliedLaunchTime + " 00:00:00.0"));

				String[] campTag = req.getParameterValues("campTag");
				List<String> updateCampList = Arrays.asList(campTag);
				String campId = req.getParameter("camp_id");
				campTagDetailService.update(updateCampList, Integer.valueOf(campId));
				campVO.setCampId(Integer.valueOf(campId));

				String campStatus = req.getParameter("camp_status");
				campVO.setCampStatus(Integer.valueOf(campStatus));
				String campName = req.getParameter("camp_name");// 2.宣告campName變數,存camp_name參數
				if (campName == null || (campName.trim()).length() == 0) {
					errorMsgs.add("營地名稱:請勿空白");// 3.判斷傳入參數是否為空值
				} else {
					campVO.setCampName(campName);// 4.將campName參數存入campVO
				}

				String campPhone = req.getParameter("camp_phone");
				if (campPhone == null || (campPhone.trim()).length() == 0) {
					errorMsgs.add("營地電話:請勿空白");
				} else {
					campVO.setCampPhone(campPhone);
				}

				String longitude = req.getParameter("longitude");
				BigDecimal bigDecimalLongitude = new BigDecimal(longitude);
				if (longitude == null || (longitude.trim()).length() == 0) {
					errorMsgs.add("緯度:請勿空白");
				} else {
					campVO.setLongitude(bigDecimalLongitude);
				}

				String lattitude = req.getParameter("lattitude");
				BigDecimal bigDecimalLattitude = new BigDecimal(lattitude);
				if (lattitude == null || (lattitude.trim()).length() == 0) {
					errorMsgs.add("經度:請勿空白");
				} else {
					campVO.setLattitude(bigDecimalLattitude);
				}

				String campAddress = req.getParameter("campAddress");
				if (campAddress == null || (campAddress.trim()).length() == 0) {
					errorMsgs.add("地址:請勿空白");
				} else {
					campVO.setCampAddress(campAddress);
				}

				String campDiscription = req.getParameter("campDiscription");
				if (campDiscription == null || (campDiscription.trim()).length() == 0) {
					errorMsgs.add("營地敘述:請勿空白");
				} else {
					campVO.setCampDiscription(campDiscription);
				}

				String campRule = req.getParameter("campRule");
				if (campRule == null || (campRule.trim()).length() == 0) {
					errorMsgs.add("營地租借規則:請勿空白");
				} else {
					campVO.setCampRule(campRule);
				}

				byte[] campPic1 = null;
				byte[] campPic2 = null;
				byte[] campPic3 = null;
				byte[] campPic4 = null;
				byte[] campPic5 = null;
				Part parts1 = req.getPart("camp_pic1");
				Part parts2 = req.getPart("camp_pic2");
				Part parts3 = req.getPart("camp_pic3");
				Part parts4 = req.getPart("camp_pic4");
				Part parts5 = req.getPart("camp_pic5");

				if (parts1.getInputStream().available() != 0) {
					campPic1 = getBytesFromPart(parts1);
					campVO.setCampPic1(campPic1);
				}

				if (parts2.getInputStream().available() != 0) {
					campPic2 = getBytesFromPart(parts2);
					campVO.setCampPic2(campPic2);
				}
				if (parts3.getInputStream().available() != 0) {
					campPic3 = getBytesFromPart(parts3);
					campVO.setCampPic3(campPic3);
				}
				
				if (parts4.getInputStream().available() != 0) {
					campPic4 = getBytesFromPart(parts4);
					campVO.setCampPic4(campPic4);
				}

				if (parts5.getInputStream().available() != 0) {
					campPic5 = getBytesFromPart(parts5);
					campVO.setCampPic5(campPic5);
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/camp/insertCampShelves.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				// 新增營地後,執行查詢
				CampService campSvc = new CampService();
				campSvc.updateCamp(campVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				String url = "/back_end/camp/selectCamp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交campShelves.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/camp/insertCampShelves.jsp");
				failureView.forward(req, res);
			}
		}

		        /******************************** 查詢營地******************************************/
		// 依據營位狀態,時間區間,輸入關鍵字查詢營地

		if (action.equals("SEARCHALL")) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<CampVO> cavList = new ArrayList<CampVO>();
			String str = req.getParameter("campstatus");
			String startime = req.getParameter("startDate");

			if (startime == null || (startime.trim()).length() == 0) {
				Calendar startimeCalendar = Calendar.getInstance();
				startimeCalendar.add(Calendar.DATE, -90);
				startime = sdf.format(startimeCalendar.getTime());
			}

			String endtime = req.getParameter("endDate");

			if (endtime == null || (endtime.trim()).length() == 0) {
				Calendar endtimeCalendar = Calendar.getInstance();
				// 假如今天選12/19 00:00:00 自動加一天減一秒 變成12/20
				endtimeCalendar.add(Calendar.DATE, +1);
				endtime = sdf.format(endtimeCalendar.getTime());
			}

			String campName = req.getParameter("campName");
			Date stardate = null;
			Date enddate = null;
			try {
				stardate = new SimpleDateFormat("yyyy-MM-dd").parse(startime);
				enddate = new SimpleDateFormat("yyyy-MM-dd").parse(endtime);
				// 假如今天選12/19 00:00:00 自動加一天減一秒 變成12/20
				Calendar endtimeCalendar = Calendar.getInstance();
				endtimeCalendar.setTime(enddate);
				endtimeCalendar.add(Calendar.DATE, +1);
				enddate = endtimeCalendar.getTime();
			} catch (ParseException e) {

				e.printStackTrace();
			}

			int campstatus = Integer.valueOf(str);
			CampService campSvc = new CampService();
			cavList = campSvc.camplist(campstatus, stardate, enddate, campName);

			
			Calendar endtimeCalendar = Calendar.getInstance();
			endtimeCalendar.setTime(enddate);
			endtimeCalendar.add(Calendar.DATE, -1);
			req.setAttribute("list", cavList);
			req.setAttribute("startime", stardate);
			req.setAttribute("endtime", endtimeCalendar.getTime());
			req.setAttribute("campstatus", campstatus);
			req.setAttribute("campName", campName);
			String url = "/back_end/camp/selectCamp.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}

		/*************************** 跳轉到更新營地頁面(帶了舊的資料) ****************************/

		if (action.equals("UPDATEFINDBYKEY")) {
			String campStr = req.getParameter("campId");
			Integer campId = Integer.valueOf(campStr);

			// 取得已選取的tag標籤
			List<CampTagDetailVO> checkedList = campTagDetailService.findByCampId(campId);
			List<Integer> checkedIntList = new ArrayList<Integer>();

			// 裝入已取的tagID 放到jsp用contain方法判斷是否有勾選
			for (CampTagDetailVO ctd : checkedList) {
				checkedIntList.add(ctd.getCampTagId());
			}

			CampVO cv = campService.getOneCamp(campId);

			List<CampTagDetailVO> campTagDetailList = campTagDetailService.getAll();
			req.setAttribute("campTagDetailList", campTagDetailList);
			req.setAttribute("checkedIntList", checkedIntList);

			req.setAttribute("campVO", cv);
			String url = "/back_end/camp/updateCamp.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);

		}

		/*************************** 營地上架審核跳轉畫面 *************************************/
		if (action.equals("certificatepage")) {
			String campStr = req.getParameter("campId");
			Integer campId = Integer.valueOf(campStr);
			CampService campSvce = new CampService();
			CampVO cv = new CampVO();
			cv = campSvce.selectCampCheck(campId);
			req.setAttribute("campVO", cv);

			String url = "/back_end/camp/updateCampCertificatenum.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}

		/******************** * 營地上架審核 ************************************************/
		// 更新營地上架審核

		if ("updateCertificate".equals(action)) {

			CampVO campVO = new CampVO();

			CompanyVO companyVO = new CompanyVO();

			List<String> errorMsgs = new ArrayList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				String campId = req.getParameter("camp_id");
				campVO.setCampId(Integer.valueOf(campId));

				String certificateNum = req.getParameter("certificate_num");
				if (certificateNum == null || (certificateNum.trim()).length() == 0) {
					errorMsgs.add("認證字號:請勿空白");
				} else {
					campVO.setCertificateNum(certificateNum);
				}
				
				byte[] campPic1 = null;
				Part parts1 = req.getPart("camp_pic1");

				if (parts1.getInputStream().available() != 0) {
					campPic1 = getBytesFromPart(parts1);
					campVO.setCertificatePic(campPic1);
				}

				/*************************** 2.開始查詢資料 *****************************************/
				// 新增營地後,執行查詢

				CampService campSerive = new CampService();
				campSerive.updateCampCertificatenum(campVO, companyVO);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				String url = "/back_end/camp/selectCampCertificatenum.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				String url = "/back_end/camp/updateCampCertificatenum.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			}
		}

		/***************************************************************************/
		// 查詢營地上架審核

		if (action.equals("SEARCHCAMPANY")) {

			String companyName = req.getParameter("companyName");

			CampService campSvc = new CampService();
			List<CampVO> campVolist = new ArrayList<CampVO>();
			campVolist = campSvc.selectAllCampCheck(companyName);

			req.setAttribute("list", campVolist);
			String url = "/back_end/camp/selectCampCertificatenum.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}

	}

	// 處理圖片讀取
	private byte[] getBytesFromPart(Part part) {
		byte[] buf = null;
		InputStream in;
		try {
			in = part.getInputStream();
			buf = new byte[in.available()];
			in.read(buf);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buf;
	}
}
