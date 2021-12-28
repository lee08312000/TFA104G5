package com.company.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.model.CompanyDAOImpl;
import com.company.model.CompanyService;
import com.company.model.CompanyVO;
import com.company.model.MailService;

import redis.clients.jedis.Jedis;


@WebServlet("/Company/VendorLoginServlet")
public class VendorLoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("login".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String account = req.getParameter("account");
				String stringReg = "^[(a-zA-Z0-9)]{6,20}$";
				if (account == null || account.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				} else if(!account.trim().matches(stringReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("帳號: 只能是英文字母、數字 , 且長度必需在6到20之間");
	            }
				
				String password = req.getParameter("password");
				if (password == null || password.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				} else if(!account.trim().matches(stringReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("密碼: 只能是英文字母、數字 , 且長度必需在6到20之間");
	            }
							
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {					
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/companyLogin/vendorLogin.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料***************************************/
				CompanyService companySvc = new CompanyService();
				CompanyVO companyVO = companySvc.getOneCompanyByAccountPassword(account,password);
				if (companyVO == null) {
					errorMsgs.add("查無資料");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/companyLogin/vendorLogin.jsp");
					failureView.forward(req, res);
					return;
				}else {
					HttpSession session = req.getSession();
				    session.setAttribute("vendorId", companyVO.getCompanyId());   //*工作1: 才在session內做已經登入過的標識
				    session.setAttribute("vendorName", companyVO.getCompanyName());
				    session.setAttribute("companyVO", companyVO);
				    try {                                                        
				         String location = (String) session.getAttribute("location");
				         if (location != null) {
				           session.removeAttribute("location");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
				           res.sendRedirect(location);            
				           return;
				         }
				       }catch (Exception e) {
				    	   e.printStackTrace(); 
				       }
				    
				    String url = "/back_end/companyProduct/jsp/productlist.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
					successView.forward(req, res);
//				    res.sendRedirect(req.getContextPath()+"/back_end/companyProduct/jsp/productlist.jsp");  //*工作3: (-->如無來源網頁:則重導至productlist.jsp)
				}
			
				/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					e.printStackTrace();
//					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/companyLogin/vendorLogin.jsp");
					failureView.forward(req, res);
				}			
		}
		
		
		if ("register".equals(action)) {
			Map<String,String> errorMsgs = new HashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String headName = req.getParameter("headName");
				String stringReg1 = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				String stringReg2 = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,30}$";
				String stringReg3 = "^[(a-zA-Z0-9)]{2,20}$";
				String stringReg4 = "^[(0-9)]{10}$";
				
				if (headName == null || headName.trim().length() == 0) {
					errorMsgs.put("errorHeadName","負責人姓名: 請勿空白");
				} else if(!headName.trim().matches(stringReg1)) { 
					errorMsgs.put("errorHeadName","負責人姓名: 只能是中、英文字母 , 且長度必需在2到10之間");
	            }
				
				String companyName = req.getParameter("companyName");
				if (companyName == null || companyName.trim().length() == 0) {
					errorMsgs.put("errorCompanyName","廠商名稱: 請勿空白");
				} else if(!companyName.trim().matches(stringReg2)) { 
					errorMsgs.put("errorCompanyName","廠商名稱: 只能是中、英文字母 , 且長度必需在2到30之間");
	            }
				
								
				String account = req.getParameter("account");						
				if (account == null || account.trim().length() == 0) {
					errorMsgs.put("errorAccount","廠商帳號請勿空白");
				} else if(!account.trim().matches(stringReg3)) { 
					errorMsgs.put("errorAccount","廠商帳號: 只能是英文字母、數字, 且長度必需在2到20之間");
	            }
				
				String password = req.getParameter("password");						
				if (password == null || password.trim().length() == 0) {
					errorMsgs.put("errorPassword","密碼請勿空白");
				} else if(!password.trim().matches(stringReg3)) { 
					errorMsgs.put("errorPassword","密碼: 只能是英文字母、數字, 且長度必需在2到20之間");
	            }
				
				String rePassword = req.getParameter("rePassword");						
				if (rePassword == null || rePassword.trim().length() == 0) {
					errorMsgs.put("errorRePassword","確認密碼請勿空白");
				} else if(!rePassword.trim().matches(password)) { 
					errorMsgs.put("errorRePassword","密碼: 兩次密碼請輸入一致");
	            }
				
				String email = req.getParameter("email");						
				if (email == null || email.trim().length() == 0) {
					errorMsgs.put("errorEmail","Email請勿空白");
				} else if(!email.trim().matches("[a-zA-Z0-9._]+@([a-zA-Z0-9_]+.[a-zA-Z0-9_]+)+")) { 
					errorMsgs.put("errorEmail","email格式錯誤,請重新輸入");
	            }
				
				String phone = req.getParameter("phone");						
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.put("errorpPhone","電話號碼請勿空白");
				} else if(!phone.trim().matches(stringReg4)) { 
					errorMsgs.put("errorPhone","請輸入長度為10的數字");
	            }
								
				String bankAccount = req.getParameter("bankAccount").trim();
				if (bankAccount == null || bankAccount.trim().length() == 0) {
					errorMsgs.put("errorBankAccount","銀行帳號請勿空白");
				}
				
				String address = req.getParameter("address").trim();
				if (address == null || address.trim().length() == 0) {
					errorMsgs.put("errorAddress","廠商地址請勿空白");
				}			

				Integer companyStatus = 0;
				Timestamp companyRegisterTime = new Timestamp(System.currentTimeMillis());
			
				CompanyVO companyVO = new CompanyVO();				
				companyVO.setHeadName(headName);				
				companyVO.setCompanyName(companyName);
				companyVO.setCompanyAccount(account);
				companyVO.setCompanyPassword(password);
				companyVO.setCompanyEmail(email);
				companyVO.setCompanyTel(phone);
				companyVO.setCompanyBankAccount(bankAccount);
				companyVO.setCompanyAddress(address);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("companyVO", companyVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/companyLogin/register.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.判斷帳號是否已存在***************************************/
				CompanyService companySvc = new CompanyService();
				CompanyVO companyVO1 = companySvc.getOneCompanyByAccount(account);
				if (companyVO1 != null) {
					req.setAttribute("companyVO", companyVO);
					errorMsgs.put("errorAccount","此帳號已被使用");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/companyLogin/register.jsp");
					failureView.forward(req, res);
					return;
				}			
				
				/***************************3.開始新增資料,寄送開通郵件***************************************/
				companyVO = companySvc.addCompany(companyStatus, headName, companyName,
							account, password, email, phone, bankAccount, address,
							companyRegisterTime);
				String scheme = req.getScheme();
				String serverName = req.getServerName();
				String serverPort = req.getServerPort() + "";
				String contextPath = req.getContextPath();
				String realContextPath = scheme +"://"+serverName +":"+ serverPort + contextPath;
				//產生通用唯一識別碼
				UUID uuid = UUID.randomUUID();
				String code = uuid.toString();				
				//存UUID與會員帳號至redis
				Jedis jedis = null;
				try {
					jedis = new Jedis("localhost", 6379);
					jedis.set(code, account);
					jedis.expire(code, 30);
				}finally {
					if(jedis != null)
					   jedis.close();
				}
				
				MailService mailSvc = new MailService();
				mailSvc.sendMail(email,code,"activation",realContextPath);
				/***************************4.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back_end/companyLogin/vendorLogin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					e.printStackTrace();
//					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back_end/companyLogin/register.jsp");
					failureView.forward(req, res);
				}
			
		}	
			
		//郵件驗證開通功能
		if ("activation".equals(action)) {
			try {
				
				String code = req.getParameter("code");
				Jedis jedis = null;
				String account = null;
				try {
					jedis = new Jedis("localhost", 6379);
					account = jedis.get(code);
				}finally {
					if(jedis != null)
					   jedis.close();
				}
				
				CompanyService companySvc= new CompanyService();			
				CompanyVO companyVO = companySvc.getOneCompanyByAccount(account);
				companyVO.setCompanyStatus(1);
				CompanyDAOImpl companyDao = new CompanyDAOImpl();			
				companyDao.update(companyVO);
				
				/***************************修改狀態完成,準備轉交(Send the Success view)***********/
				String url = "/back_end/companyLogin/vendorLogin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);	
				
			}catch (Exception e) {
				e.printStackTrace();
//				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back_end/companyLogin/vendorLogin.jsp");
				failureView.forward(req, res);
			}			
		}
		
	
	}
	
	
	
	
	
	
	
}
