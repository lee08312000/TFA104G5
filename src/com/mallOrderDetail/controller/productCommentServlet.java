package com.mallOrderDetail.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mallOrderDetail.model.MallOrderDetailService;
import com.mallOrderDetail.model.MallOrderDetailVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;

@WebServlet("/MallOrderDetail/productCommentServlet")
public class productCommentServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");		
		
		if ("getProductComment".equals(action)) { // 來自listAllEmp.jsp的請求

				
			try {
				/***************************1.接收請求參數****************************************/
				Integer productId = new Integer(req.getParameter("productId"));
				
				/***************************2.開始查詢資料****************************************/
				MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();
//				System.out.println(productId);
				List<MallOrderDetailVO> mallOrderDetailList = mallOrderDetailSvc.getAllProductComment(productId.intValue());
//				System.out.println(mallOrderDetailList);				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("mallOrderDetailList", mallOrderDetailList);         // 資料庫取出的empVO物件,存入req
				String url = "/back_end/companyProduct/jsp/vendorProductCommentDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {				
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/companyProduct/jsp/vendorProductComment.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		
		
		
	}
}
