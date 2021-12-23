package com.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mallOrderDetail.model.MallOrderDetailService;
import com.mallOrderDetail.model.MallOrderDetailVO;


@WebServlet("/Member/MemberProductServlet")
@MultipartConfig
public class MemberProductServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getMallOrderDetail".equals(action)) {
			try {
				/***************************1.接收請求參數****************************************/
				Integer mallOrderId = new Integer(req.getParameter("mallOrderId"));
				
				/***************************2.開始查詢資料****************************************/
				MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();
				List<MallOrderDetailVO> mallOrderDetailList = mallOrderDetailSvc.getBymallOrderId(mallOrderId);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("mallOrderDetailList", mallOrderDetailList);         // 資料庫取出的empVO物件,存入req
				String url = "/front_end/member/jsp/member_product_order_detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {				
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/member/jsp/member_product_order_list.jsp");
				failureView.forward(req, res);
			}
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
