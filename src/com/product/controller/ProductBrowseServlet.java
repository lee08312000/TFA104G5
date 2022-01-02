package com.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.model.CompanyVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;

@WebServlet("/Product/ProductBrowseServlet")
public class ProductBrowseServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		// 取得商品列表by商品類別
		if ("getList_By_Type".equals(action)) {
			
			try {
				HttpSession session = req.getSession();
				CompanyVO companyVO = (CompanyVO) session.getAttribute("companyVO");
				
				Integer productTypeId = Integer.parseInt(req.getParameter("productTypeId"));	
				ProductService productSvc= new ProductService();
				List<ProductVO> producList= productSvc.getProductsByProductType(companyVO.getCompanyId(),productTypeId);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("producList", producList);
				String url = "/back_end/companyProduct/jsp/productListByType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
			
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
//				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/companyProduct/jsp/productlist.jsp");
				failureView.forward(req, res);
			}				
					
		}
		
		if ("getList_By_Type_comment".equals(action)) {
			
			try {
				HttpSession session = req.getSession();
				CompanyVO companyVO = (CompanyVO) session.getAttribute("companyVO");
				
				Integer productTypeId = Integer.parseInt(req.getParameter("productTypeId"));	
				ProductService productSvc= new ProductService();
				List<ProductVO> producList= productSvc.getProductsByProductType(companyVO.getCompanyId(),productTypeId);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("producList", producList);
				String url = "/back_end/companyProduct/jsp/vendorProductCommentByType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
			
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
//				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/companyProduct/jsp/vendorProductComment.jsp");
				failureView.forward(req, res);
			}				
					
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
