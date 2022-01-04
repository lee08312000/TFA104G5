package com.campOrder.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camp.model.CampService;
import com.camp.model.CampVO;
import com.campOrder.model.CampOrderService;
import com.campOrder.model.CampOrderVO;


public class CampOrderServlet extends HttpServlet {

	private CampOrderService campOrderService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7466693753949857830L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		campOrderService = new CampOrderService();
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");

		// 查詢單筆訂單
		if (action.equals("GETONECAMP")) {
			String campOrderIdStr = req.getParameter("campOrderId");
			CampOrderVO cov = new CampOrderVO();
			if (campOrderIdStr != null && campOrderIdStr != "") {
				cov = campOrderService.findByCampOrderId(Integer.valueOf(campOrderIdStr));
			}
			req.setAttribute("campOrderVO", cov);
			String url = "/back_end/camp/updateCampOrder.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);

		}
		
		// 查詢單筆訂單
		if (action.equals("GETONECAMPNOUPDATE")) {
			String campOrderIdStr = req.getParameter("campOrderId");
			CampOrderVO cov = new CampOrderVO();
			if (campOrderIdStr != null && campOrderIdStr != "") {
				cov = campOrderService.findByCampOrderId(Integer.valueOf(campOrderIdStr));
			}
			req.setAttribute("campOrderVO", cov);
			String url = "/back_end/camp/showOneCampOrder.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);

		}

		// 查詢多筆訂單
		if (action.equals("SEARCHALL")) {
			int statusnum = req.getParameter("statusnum") == null ? -1 : Integer.valueOf(req.getParameter("statusnum"));
			String startDateStr = req.getParameter("startDate");
			String endDateStr = req.getParameter("endDate");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = sdf.parse(startDateStr);
				endDate = sdf.parse(endDateStr);

			} catch (ParseException e) {

				e.printStackTrace();
			}

			List<CampOrderVO> covList = campOrderService.findByParams(statusnum, startDate, endDate);
			req.setAttribute("list", covList);
			String url = "/back_end/camp/listAllCampOrder.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}

		// 修改訂單

		if (action.equals("UPDATE")) {

			CampOrderVO campOrderVO = new CampOrderVO();

			List<String> errorMsgs = new ArrayList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				String campOrderId = req.getParameter("campOrderId");
				campOrderVO.setCampOrderId(Integer.valueOf(campOrderId));


				String payerName = req.getParameter("payerName");
				if (payerName == null || (payerName.trim()).length() == 0) {
					errorMsgs.add("付款人姓名:請勿空白");
				} else {
					campOrderVO.setPayerName(payerName);
				}

				String payerPhone = req.getParameter("payerPhone");
				if (payerPhone == null || (payerPhone.trim()).length() == 0) {
					errorMsgs.add("付款人電話:請勿空白");
				} else {
					campOrderVO.setPayerPhone(payerPhone);
				}
				String campStatus = req.getParameter("campStatus");
				campOrderVO.setCampOrderStatus(Integer.valueOf(campStatus));

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/camp/updateCampOrder.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				// 新增營地後,執行查詢
				CampOrderService campSvc = new CampOrderService();
				campSvc.updateOrder(campOrderVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				String url = "/back_end/camp/listAllCampOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交campShelves.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/camp/updateCampOrder.jsp");
				failureView.forward(req, res);
			}
		}

		/*************************** 營地評價查詢 *****************************************/
		if (action.equals("SEARCHCOMMENT")) {

			String startDateStrs = req.getParameter("startDate");
     		String endDateStrs = req.getParameter("endDate");
			String campOrderIds = req.getParameter("campOrderId");

			Timestamp startDateTimestamp = Timestamp.valueOf(startDateStrs + " 00:00:00");
			Timestamp endDateTimestamp = Timestamp.valueOf(endDateStrs + " 23:59:59");
			int campOrder = -1;
			if (campOrderIds != null && campOrderIds.length() != 0) {
				campOrder = Integer.valueOf(campOrderIds);
			}

			CampOrderService campSvce = new CampOrderService();
			List<CampOrderVO> campOrderVolist = new ArrayList<CampOrderVO>();

			campOrderVolist = campSvce.selectCampComment(startDateTimestamp, endDateTimestamp, campOrder);

			req.setAttribute("list", campOrderVolist);
			String url = "/back_end/camp/campComment.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}
	}
}
