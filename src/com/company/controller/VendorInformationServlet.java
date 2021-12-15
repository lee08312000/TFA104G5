package com.company.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.model.CompanyService;
import com.company.model.CompanyVO;



@WebServlet("/Company/VendorInformationServlet")
public class VendorInformationServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				Integer productId = new Integer(req.getParameter("productId").trim());
//				
//				String productName = req.getParameter("productName");
//				String stringReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$";
//				if (productName == null || productName.trim().length() == 0) {
//					errorMsgs.add("商品名稱: 請勿空白");
//				} else if(!productName.trim().matches(stringReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到30之間");
//	            }
//				
//				Integer productTypeId = new Integer(req.getParameter("productTypeId").trim());
//				
//				Integer productPrice = null;
//				try {
//					productPrice = new Integer(req.getParameter("productPrice").trim());
//				} catch (Exception e) {
//					productPrice = 0;
//					errorMsgs.add("商品單價請填數字.");
//				}
//				
//				String productBrand = req.getParameter("productBrand");						
//				if (productBrand == null || productBrand.trim().length() == 0) {
//					errorMsgs.add("商品品牌: 請勿空白");
//				} else if(!productBrand.trim().matches(stringReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("商品品牌: 只能是中、英文字母、數字和_ , 且長度必需在2到30之間");
//	            }
//				
//				Integer productInventory = null;
//				try {
//					productInventory = Integer.parseInt(req.getParameter("productInventory").trim());
//				} catch (Exception e) {
//					productInventory = 0;
//					errorMsgs.add("庫存請填數字.");
//				}
//				
//				String productDescription = req.getParameter("productDescription").trim();
//				if (productDescription == null || productDescription.trim().length() == 0) {
//					errorMsgs.add("商品敘述請勿空白");
//				}
//				
//				String shoppingInformation = req.getParameter("shoppingInformation").trim();
//				if (shoppingInformation == null || shoppingInformation.trim().length() == 0) {
//					errorMsgs.add("商品購買須知請勿空白");
//				}
//				
//				Integer productStatus = new Integer(req.getParameter("productStatus").trim());
//				
//				ProductVO productVO = new ProductVO();
//				productVO.setProductId(productId); 
//				Integer companyId = 1;
//				productVO.setCompanyId(companyId); //.....暫時寫死廠商編號
//				productVO.setProductName(productName);
//				productVO.setProductStatus(productStatus);
//				productVO.setProductTypeId(productTypeId);
//				productVO.setProductPrice(productPrice);
//				productVO.setProductBrand(productBrand);
//				productVO.setProductInventory(productInventory);
//				productVO.setProductDescription(productDescription);
//				productVO.setShoppingInformation(shoppingInformation);
//				productVO.setProductPic1(productPic1);
//				productVO.setProductPic2(productPic2);
//				productVO.setProductPic3(productPic3);
				
				/***************************抓取資料*****************/
				HttpSession session = req.getSession();
				CompanyVO companyVO = (CompanyVO) session.getAttribute("companyVO");
				
				Integer companyId = companyVO.getCompanyId();
				Integer companyStatus = companyVO.getCompanyStatus();
				String headName = req.getParameter("headName").trim();
				String companyName = req.getParameter("companyName").trim();
				String companyAccount = companyVO.getCompanyAccount();
				String companyPassword = companyVO.getCompanyPassword();
				String companyEmail = req.getParameter("companyEmail").trim();
				String companyTel = req.getParameter("companyTel").trim();
				String companyBankAccount = req.getParameter("companyBankAccount").trim();
				String companyAddress = req.getParameter("companyAddress").trim();
				Timestamp companyRegisterTime = companyVO.getCompanyRegisterTime();
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("companyVO", companyVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/companyProduct/jsp/companyUpdate.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				CompanyService companySvc = new CompanyService();
				companyVO = companySvc.updateCompany(companyId, companyStatus, headName, 
						 companyName,  companyAccount,  companyPassword,  companyEmail, 
						 companyTel,  companyBankAccount,  companyAddress,  companyRegisterTime);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				session.setAttribute("vendorId", companyVO.getCompanyId());   //*工作1: 才在session內做已經登入過的標識
			    session.setAttribute("vendorName", companyVO.getCompanyName());
				session.setAttribute("companyVO", companyVO); // 資料庫update成功後,正確的的companyVO物件,存入session
				String url = "/back_end/companyProduct/jsp/companyImformation.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/companyProduct/jsp/companyUpdate.jsp");
				failureView.forward(req, res);
			}
		}
	
		
		
		
		
		
		
		
	}	
}
