package com.company.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Company/VendorLogoutServlet")
public class VendorLogoutServlet  extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("logout".equals(action)) {
			HttpSession session = req.getSession();
			session.removeAttribute("vendorId");
			session.removeAttribute("vendorName");
			session.removeAttribute("companyVO");
			
			
			String url = "/back_end/companyLogin/vendorLogin.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
//			res.sendRedirect(req.getContextPath() + "/back_end/companyLogin/jsp/vendorLogin.jsp");
//			return;
			
		}
		
		
		
	}
}
