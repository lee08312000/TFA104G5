package com.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.model.AdminService;
import com.admin.model.AdminVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;

import util.Util;

@WebServlet("/admin/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminServlet() {
		super();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// 中文亂碼解決方法
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		// 防止非法登入 得到session
		// PrintWriter out = res.getWriter();

		String action = req.getParameter("action");

		if ("login".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// 登入畫面轉jsp才可以
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String account = null;
			String password = null;
			AdminService adminSvc = new AdminService();

			account = req.getParameter("account").trim();
			System.out.println(account);
			password = req.getParameter("password").trim();
			System.out.println(password);

			/*************************** 2.開始查詢資料 *****************************************/
			List<AdminVO> adminVOList = adminSvc.getAllAdmin();
			for (AdminVO adminVO : adminVOList) {
				if (account.equals(adminVO.getAdminAccount()) && password.equals(adminVO.getAdminPassword())) {
					if (adminVO.getAdminAccountStatus().intValue() == 0) {
						errorMsgs.add("此帳戶已被停權");
						
						req.setAttribute("adminAccount", account);
						req.setAttribute("adminPassword", password);
						String url = "/back_end/adminLogin/adminLogin.jsp";
						RequestDispatcher failureView = req.getRequestDispatcher(url); // 失敗轉交 login.jsp
						failureView.forward(req, res);
						return; // 程式在此中斷

					} else {
						session.setAttribute("adminVO", adminVO); // 成功登入的話
						System.out.println("登入成功");

						try {
							String location = (String) session.getAttribute("location");
							if (location != null) {
								session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
								res.sendRedirect(location);
								return;
							}
						} catch (Exception ignored) {
							
						}
						
						res.sendRedirect(req.getContextPath()+"/back_end/admin/adminIndex.jsp");  //*工作3: (-->如無來源網頁:則重導至login_success.jsp)
						return;
					}
				}
			}

			errorMsgs.add("帳號或密碼輸入錯誤");
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("adminAccount", account);
				req.setAttribute("adminPassword", password);
				String url = "/back_end/adminLogin/adminLogin.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url); // 失敗轉交 login.jsp
				failureView.forward(req, res);
				return; // 程式在此中斷
			}

		}

		if ("logout".equals(action)) {

			if (session != null) {

				session.removeAttribute("adminVO");

//    			session.invalidate();

				System.out.println("登出成功");
				
				res.sendRedirect(req.getContextPath()+"/back_end/adminLogin/adminLogin.jsp");
				return;
			}
		}

		if ("add".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// 註冊畫面轉jsp才可以
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			AdminService adminSvc = new AdminService();
			String account = req.getParameter("account").trim();
			String accountReg = "^[(a-zA-Z0-9_)]{2,20}$";
			System.out.println(account);
			if (account == null || account.trim().length() == 0) {
				errorMsgs.add("帳號請勿空白");
			} else if (!account.trim().matches(accountReg)) {
				errorMsgs.add("帳號只能是英文字母、數字和_ , 且長度必需在2到20之間");
			}
			
			List<AdminVO> adminVOList = adminSvc.getAllAdmin();
			for (AdminVO adminVO : adminVOList) {
				if (account.equals(adminVO.getAdminAccount())) {
					errorMsgs.add("此帳號已有人使用");
					break;
				}
			}

			String password = req.getParameter("password").trim();
			String passwordReg = "^[(a-zA-Z0-9_)]{2,20}$";
			System.out.println(password);
			if (password == null || password.trim().length() == 0) {
				errorMsgs.add("密碼請勿空白");
			} else if (!password.trim().matches(passwordReg)) {
				errorMsgs.add("密碼只能是英文字母、數字和_ , 且長度必需在2到20之間");
			}

			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("adminAccount", account);
				req.setAttribute("adminPassword", password);
				String url = "/back_end/admin/addAdmin.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;

			} else {
				/*************************** 2.開始新增資料 ***************************************/
				adminSvc.addAdmin(1, account, password);
				System.out.println("新增成功");
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/admin/adminManagement.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);
				return;
			}

		}
		
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// 註冊畫面轉jsp才可以
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			AdminService adminSvc = new AdminService();
			
			String password = req.getParameter("password").trim();
			String passwordReg = "^[(a-zA-Z0-9_)]{2,20}$";
			System.out.println(password);
			if (password == null || password.trim().length() == 0) {
				errorMsgs.add("密碼請勿空白");
			} else if (!password.trim().matches(passwordReg)) {
				errorMsgs.add("密碼只能是英文字母、數字和_ , 且長度必需在2到20之間");
			}
			
			Integer adminId = Integer.parseInt(req.getParameter("adminId"));
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("adminPassword", password);
				String url = "/back_end/admin/updateAdmin.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;

			} else {
				/*************************** 2.開始新增資料 ***************************************/
				
				AdminVO adminVO = adminSvc.getOneAdmin(adminId);
				AdminVO newAdminVO = adminSvc.updateAdmin(adminVO.getAdminId(), adminVO.getAdminAccountStatus(), adminVO.getAdminAccount(), password);
				session.setAttribute("adminVO", newAdminVO);
				System.out.println("更新成功");
				/*************************** 3.更新完成,準備轉交(Send the Success view) ***********/
				String url = "/back_end/admin/adminInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);
				return;
			}

		}
		
	}

}
