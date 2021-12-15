package com.productReport.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.member.model.MemberVO;
import com.productReport.model.ProductReportService;
import com.productReport.model.ProductReportVO;

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
    	
    	// 加入檢舉
    	if ("addReport".equals(action)) {
    		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
    		Integer productId = Integer.parseInt(req.getParameter("productId"));
    		String reportReason = req.getParameter("reportReason").trim();
    		/*************************** 2.開始新增資料 *****************************************/
    		ProductReportService productReportSvc = new ProductReportService();
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
    }
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


}
