package com.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.model.AdminService;
import com.admin.model.AdminVO;
import com.company.model.CompanyService;
import com.company.model.CompanyVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;

@WebServlet("/admin/AdminManagementServlet")
public class AdminManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminManagementServlet() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    	req.setCharacterEncoding("UTF-8");
    	res.setContentType("text/html; charset=UTF-8");
//    	PrintWriter out = res.getWriter();
    	String action = req.getParameter("action");
    	AdminService adminSvc = new AdminService();
    	
    	// 將管理員狀態設為啟用
    	if ("on".equals(action)) {
    		/*************************** 1.接收請求參數  **********************/
    		Integer adminId = Integer.parseInt(req.getParameter("adminId"));
    		/*************************** 2.開始修改資料 *****************************************/
    		AdminVO adminVO = adminSvc.getOneAdmin(adminId);
    		if (adminVO != null) {
    			adminSvc.updateAdmin(adminVO.getAdminId(), 1, adminVO.getAdminAccount(), adminVO.getAdminPassword());
    		}
    		/*************************** 3.準備轉交(Send the Success view) *************/
    		String url = "/back_end/admin/adminManagement.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    	}
    	
    	// 將管理員狀態設為停用
    	if ("off".equals(action)) {
    		/*************************** 1.接收請求參數  **********************/
    		Integer adminId = Integer.parseInt(req.getParameter("adminId"));
    		/*************************** 2.開始修改資料 *****************************************/
    		AdminVO adminVO = adminSvc.getOneAdmin(adminId);
    		if (adminVO != null) {
    			adminSvc.updateAdmin(adminVO.getAdminId(), 0, adminVO.getAdminAccount(), adminVO.getAdminPassword());
    		}
    		/*************************** 3.準備轉交(Send the Success view) *************/
    		String url = "/back_end/admin/adminManagement.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    	}
    	
    	// 用管理員編號查詢管理員
    	if ("searchByAdminId".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
    		/*************************** 1.接收請求參數  **********************/
    		
    		Integer adminId = 0;
			try {
				adminId = Integer.parseInt(req.getParameter("adminId"));
			} catch (NumberFormatException e) {
				errorMsgs.add("請輸入正整數");
//				e.printStackTrace();
			}
    		/*************************** 2.開始查詢資料 *****************************************/
    		List<AdminVO> adminVOList = new ArrayList<AdminVO>();
    		AdminVO adminVO = adminSvc.getOneAdmin(adminId);
    		if (adminVO != null) {
    			adminVOList.add(adminVO);
    		}
    		/*************************** 3.準備轉交(Send the Success view) *************/
    		req.setAttribute("adminVOList", adminVOList);
    		String url = "/back_end/admin/adminManagement.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    	}
    }
    
    
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


}
