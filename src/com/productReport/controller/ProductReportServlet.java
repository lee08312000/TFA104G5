package com.productReport.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.company.model.CompanyService;
import com.member.model.MemberVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.productReport.model.ProductReportService;
import com.productReport.model.ProductReportVO;

import util.MailService;

@WebServlet("/ProductReport/ProductReportServlet")
public class ProductReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductReportServlet() {
    	
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
    	res.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = res.getWriter();
    	
    	HttpSession session = req.getSession();
    	MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
    	String action = req.getParameter("action");
    	ProductReportService productReportSvc = new ProductReportService();
    	
    	// 更換排序
    	if ("orderBy".equals(action)) {
    		Integer productReportOrder = Integer.parseInt(req.getParameter("order"));
    		session.setAttribute("productReportOrder", productReportOrder);
    		/*************************************準備轉交****************************************/
    		String url = "/back_end/admin/productReport.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    		return;
    	}
    	
    	// 加入檢舉
    	if ("addReport".equals(action)) {
    		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
    		Integer productId = Integer.parseInt(req.getParameter("productId"));
    		String reportReason = req.getParameter("reportReason").trim();
    		/*************************** 2.開始新增資料 *****************************************/
    		
    		if (memberVO == null) {
    			try {
    				JSONObject obj = new JSONObject();
					obj.put("msg", "noLogin");
					out.println(obj.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
    			return;
    		}
    		
    		
    		for(ProductReportVO productReportVO : productReportSvc.getAll()) {
    			if (memberVO.getMemberId().intValue() == productReportVO.getMemberId().intValue() && productId.intValue() == productReportVO.getProductId().intValue() && productReportVO.getReportStatus().intValue() == 0) {
    				try {
        				JSONObject obj = new JSONObject();
    					obj.put("msg", "hasReported");
    					out.println(obj.toString());
    				} catch (JSONException e) {
    					e.printStackTrace();
    				}
        			return;
    			}
    		}
    		
    		productReportSvc.addProductReport(memberVO.getMemberId(), productId, reportReason);
			try {
				JSONObject obj = new JSONObject();
				obj.put("msg", "success");
				out.println(obj.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	}
    	
    	// 管理員忽略檢舉
    	if ("ignore".equals(action)) {
    		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
    		Integer productReportId = Integer.parseInt(req.getParameter("productReportId"));
    		/*************************** 2.開始更改資料 *****************************************/
    		ProductReportVO productReportVO = productReportSvc.getOneProductReport(productReportId);
    		productReportSvc.updateProductReport(productReportVO.getProductReportId(), productReportVO.getMemberId(), productReportVO.getProductId(), productReportVO.getReportReason(), 1);
    		/*************************** 3.準備轉交 *****************************************/
    		String url = "/back_end/admin/productReport.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    		return;
    	}
    	
    	// 管理員下架商品並更改檢舉狀態
    	if ("noUsed".equals(action)) {
    		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
    		Integer productId = Integer.parseInt(req.getParameter("productId"));
    		String reportReason = req.getParameter("reportReason").trim();
    		/*************************** 2.開始更改資料 *****************************************/
    		ProductService productSvc = new ProductService();
    		ProductVO productVO = productSvc.getOneProduct(productId);
    		productSvc.updateProduct(productVO.getProductId(), productVO.getCompanyId(), productVO.getProductTypeId(), 0, productVO.getProductName(), productVO.getProductPrice(), productVO.getProductBrand(), productVO.getProductInventory(), productVO.getProductDescription(), productVO.getShoppingInformation(), productVO.getProductPic1(), productVO.getProductPic2(), productVO.getProductPic3(), productVO.getProductLaunchedTime(), productVO.getProductCommentedAllnum(), productVO.getProductCommentAllstar(), productVO.getProductSellAllnum());
    		
    		List<ProductReportVO> productReportVOList = productReportSvc.getAll();
    		
    		for (ProductReportVO productReportVO : productReportVOList) {
    			if (productReportVO.getProductId().intValue() == productId.intValue()) {
    				productReportSvc.updateProductReport(productReportVO.getProductReportId(), productReportVO.getMemberId(), productReportVO.getProductId(), productReportVO.getReportReason(), 1);
    			}
    		}
    		
    		MailService mailSvc = new MailService();
    		CompanyService companySvc = new CompanyService();
    		companySvc.getOneCompany(productVO.getCompanyId()).getCompanyEmail();
    		mailSvc.sendMail(companySvc.getOneCompany(productVO.getCompanyId()).getCompanyEmail(), "Camping Paradise-商品被下架", "您的商品:\n編號: " + productVO.getProductId() + "\n名稱: " + productVO.getProductName() + "\n因檢舉而被下架\n檢舉內容為:\n" + reportReason);
    		
    		/*************************** 3.準備轉交 *****************************************/
    		String url = "/back_end/admin/productReport.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    		return;
    	}
    	
    }
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


}
