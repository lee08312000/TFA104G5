package com.campOrder.controller;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		// 查詢多筆訂單
		if (action.equals("SEARCHALL")) {
			int statusnum = req.getParameter("statusnum") == null ? -1 : Integer.valueOf(req.getParameter("statusnum"));
			String startDateStr = req.getParameter("startDate");
			String endDateStr = req.getParameter("endDate");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String startDate = null;
			String endDate = null;
			java.util.Date begindate=null;
			java.util.Date finaldate=null;
			startDate = sdf.format(startDateStr);
			endDate = sdf.format(endDateStr);
			begindate=new java.util.Date(java.sql.Date.valueOf(startDate).getTime());
			finaldate=new java.util.Date(java.sql.Date.valueOf(endDate).getTime());

			List<CampOrderVO> covList = campOrderService.findByParams(statusnum,begindate,finaldate);
			req.setAttribute("list", covList);
			String url = "/back_end/camp/listAllCampOrder.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);

		}
		
		// 修改訂單
		if (action.equals("UPDATE")) {
			String campOrderId = req.getParameter("campOrderId");
			String memberId = req.getParameter("memberId");
			String payerName = req.getParameter("payerName");
			String payerPhone = req.getParameter("payerPhone");
			String campStatus= req.getParameter("campStatus");
				
			
//			List<CampOrderVO> covList = campOrderService.findByParams(statusnum, startDate, endDate);
//			req.setAttribute("list", covList);
//			String url = "/back_end/camp/listAllCampOrder.jsp";
//			RequestDispatcher rd = req.getRequestDispatcher(url);			
//			req.forward(req, res);
			
			/*************************** 營地評價查詢*****************************************/
			
			
			
			

		}

	}

}
