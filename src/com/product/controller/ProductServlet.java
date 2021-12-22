package com.product.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.product.model.ProductService;
import com.product.model.ProductVO;

@WebServlet("/Product/ProductServlet")
@MultipartConfig
public class ProductServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("insert".equals(action)) { // 來自addProduct.jsp的請求  
					
					List<String> errorMsgs = new LinkedList<String>();					
					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);
		
					try {
						/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
						String productName = req.getParameter("productName");
						String stringReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$";
						if (productName == null || productName.trim().length() == 0) {
							errorMsgs.add("商品名稱: 請勿空白");
						} else if(!productName.trim().matches(stringReg)) { //以下練習正則(規)表示式(regular-expression)
							errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到30之間");
			            }
						
						Integer productTypeId = new Integer(req.getParameter("productTypeId").trim());
						
						Integer productPrice = null;
						try {
							productPrice = new Integer(req.getParameter("productPrice").trim());
						} catch (Exception e) {
							productPrice = 0;
							errorMsgs.add("商品單價請填數字.");
						}
						
						String productBrand = req.getParameter("productBrand");						
						if (productBrand == null || productBrand.trim().length() == 0) {
							errorMsgs.add("商品品牌: 請勿空白");
						} else if(!productBrand.trim().matches(stringReg)) { //以下練習正則(規)表示式(regular-expression)
							errorMsgs.add("商品品牌: 只能是中、英文字母、數字和_ , 且長度必需在2到30之間");
			            }
						
						Integer productInventory = null;
						try {
							productInventory = Integer.parseInt(req.getParameter("productInventory").trim());
						} catch (Exception e) {
							productInventory = 0;
							errorMsgs.add("庫存請填數字.");
						}
						
						String productDescription = req.getParameter("productDescription").trim();
						if (productDescription == null || productDescription.trim().length() == 0) {
							errorMsgs.add("商品敘述請勿空白");
						}
						
						String shoppingInformation = req.getParameter("shoppingInformation").trim();
						if (shoppingInformation == null || shoppingInformation.trim().length() == 0) {
							errorMsgs.add("商品購買須知請勿空白");
						}
						
						Integer productStatus = null;
						
						byte[] productPic1 = null;
						byte[] productPic2 = null;
						byte[] productPic3 = null;
						Part parts1 = req.getPart("productPic1");
						Part parts2 = req.getPart("productPic2");
						Part parts3 = req.getPart("productPic3");
						
						if(parts1.getInputStream().available() != 0) {
							productPic1 = getBytesFromPart(parts1);
						}
						
						if(parts2.getInputStream().available() != 0) {
							productPic2 = getBytesFromPart(parts2);
						}
						if(parts3.getInputStream().available() != 0) {
							productPic3 = getBytesFromPart(parts3);
						}
						
						ProductVO productVO = new ProductVO();
						Integer companyId = 1;
						productVO.setCompanyId(companyId); //.....暫時寫死廠商編號
						productVO.setProductName(productName);
						productVO.setProductTypeId(productTypeId);
						productVO.setProductPrice(productPrice);
						productVO.setProductBrand(productBrand);
						productVO.setProductInventory(productInventory);
						productVO.setProductDescription(productDescription);
						productVO.setShoppingInformation(shoppingInformation);
						productVO.setProductPic1(productPic1);
						productVO.setProductPic2(productPic2);
						productVO.setProductPic3(productPic3);
						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的empVO物件,也存入req
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back_end/companyProduct/jsp/addProduct.jsp");
							failureView.forward(req, res);
							return;
						}
						
						/***************************2.開始新增資料***************************************/
						ProductService productSvc = new ProductService();
						productVO = productSvc.addProduct( companyId, productTypeId, productStatus,
								 productName, productPrice, productBrand, productInventory,
								 productDescription, shoppingInformation, productPic1, productPic2,
								 productPic3);
						
						/***************************3.新增完成,準備轉交(Send the Success view)***********/
						String url = "/back_end/companyProduct/jsp/productlist.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
						successView.forward(req, res);				
						
						/***************************其他可能的錯誤處理**********************************/
						} catch (Exception e) {
							e.printStackTrace();
//							errorMsgs.add(e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back_end/companyProduct/jsp/addProduct.jsp");
							failureView.forward(req, res);
						}
			}
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer productId = new Integer(req.getParameter("productId"));
				
				/***************************2.開始查詢資料****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(productId);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("productVO", productVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back_end/companyProduct/jsp/updateProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/companyProduct/jsp/addProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer productId = new Integer(req.getParameter("productId").trim());
				
				String productName = req.getParameter("productName");
				String stringReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$";
				if (productName == null || productName.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				} else if(!productName.trim().matches(stringReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到30之間");
	            }
				
				Integer productTypeId = new Integer(req.getParameter("productTypeId").trim());
				
				Integer productPrice = null;
				try {
					productPrice = new Integer(req.getParameter("productPrice").trim());
				} catch (Exception e) {
					productPrice = 0;
					errorMsgs.add("商品單價請填數字.");
				}
				
				String productBrand = req.getParameter("productBrand");						
				if (productBrand == null || productBrand.trim().length() == 0) {
					errorMsgs.add("商品品牌: 請勿空白");
				} else if(!productBrand.trim().matches(stringReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品品牌: 只能是中、英文字母、數字和_ , 且長度必需在2到30之間");
	            }
				
				Integer productInventory = null;
				try {
					productInventory = Integer.parseInt(req.getParameter("productInventory").trim());
				} catch (Exception e) {
					productInventory = 0;
					errorMsgs.add("庫存請填數字.");
				}
				
				String productDescription = req.getParameter("productDescription").trim();
				if (productDescription == null || productDescription.trim().length() == 0) {
					errorMsgs.add("商品敘述請勿空白");
				}
				
				String shoppingInformation = req.getParameter("shoppingInformation").trim();
				if (shoppingInformation == null || shoppingInformation.trim().length() == 0) {
					errorMsgs.add("商品購買須知請勿空白");
				}
				
				Integer productStatus = new Integer(req.getParameter("productStatus").trim());
				
				byte[] productPic1 = null;
				byte[] productPic2 = null;
				byte[] productPic3 = null;
				Part parts1 = req.getPart("productPic1");
				Part parts2 = req.getPart("productPic2");
				Part parts3 = req.getPart("productPic3");
				
				if(parts1.getInputStream().available() != 0) {
					productPic1 = getBytesFromPart(parts1);
				}
				
				if(parts2.getInputStream().available() != 0) {
					productPic2 = getBytesFromPart(parts2);
				}
				if(parts3.getInputStream().available() != 0) {
					productPic3 = getBytesFromPart(parts3);
				}
				
				ProductVO productVO = new ProductVO();
				productVO.setProductId(productId); 
				Integer companyId = 1;
				productVO.setCompanyId(companyId); //.....暫時寫死廠商編號
				productVO.setProductName(productName);
				productVO.setProductStatus(productStatus);
				productVO.setProductTypeId(productTypeId);
				productVO.setProductPrice(productPrice);
				productVO.setProductBrand(productBrand);
				productVO.setProductInventory(productInventory);
				productVO.setProductDescription(productDescription);
				productVO.setShoppingInformation(shoppingInformation);
				productVO.setProductPic1(productPic1);
				productVO.setProductPic2(productPic2);
				productVO.setProductPic3(productPic3);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/companyProduct/jsp/updateProduct.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.updateProductInformation(productId,companyId, productTypeId, productStatus,
						 productName, productPrice, productBrand, productInventory,
						 productDescription, shoppingInformation, productPic1, productPic2,
						 productPic3);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back_end/companyProduct/jsp/productlist.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/companyProduct/jsp/updateProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
		
	
	
	
	
	//處理圖片讀取
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
