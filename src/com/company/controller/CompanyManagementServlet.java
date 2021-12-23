package com.company.controller;

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

import com.company.model.CompanyService;
import com.company.model.CompanyVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;

@WebServlet("/company/CompanyManagementServlet")
public class CompanyManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CompanyManagementServlet() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    	req.setCharacterEncoding("UTF-8");
    	res.setContentType("text/html; charset=UTF-8");
//    	PrintWriter out = res.getWriter();
    	String action = req.getParameter("action");
    	CompanyService companySvc = new CompanyService();
    	
    	// 將廠商狀態設為啟用
    	if ("on".equals(action)) {
    		/*************************** 1.接收請求參數  **********************/
    		Integer companyId = Integer.parseInt(req.getParameter("companyId"));
    		/*************************** 2.開始修改資料 *****************************************/
    		CompanyVO companyVO = companySvc.getOneCompany(companyId);
    		if (companyVO != null) {
    			companySvc.updateCompany(companyVO.getCompanyId(), 1, companyVO.getHeadName(), companyVO.getCompanyName(), companyVO.getCompanyAccount(), companyVO.getCompanyPassword(), companyVO.getCompanyEmail(), companyVO.getCompanyTel(), companyVO.getCompanyBankAccount(), companyVO.getCompanyAddress(), companyVO.getCompanyRegisterTime());
    		}
    		/*************************** 3.準備轉交(Send the Success view) *************/
    		String url = "/back_end/admin/companyManagement.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    	}
    	
    	// 將廠商狀態設為停用
    	if ("off".equals(action)) {
    		/*************************** 1.接收請求參數  **********************/
    		Integer companyId = Integer.parseInt(req.getParameter("companyId"));
    		/*************************** 2.開始修改資料 *****************************************/
    		CompanyVO companyVO = companySvc.getOneCompany(companyId);
    		if (companyVO != null) {
    			companySvc.updateCompany(companyVO.getCompanyId(), 0, companyVO.getHeadName(), companyVO.getCompanyName(), companyVO.getCompanyAccount(), companyVO.getCompanyPassword(), companyVO.getCompanyEmail(), companyVO.getCompanyTel(), companyVO.getCompanyBankAccount(), companyVO.getCompanyAddress(), companyVO.getCompanyRegisterTime());
    		}
    		/*************************** 3.準備轉交(Send the Success view) *************/
    		String url = "/back_end/admin/companyManagement.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    	}
    	
    	// 用會員編號查詢會員
    	if ("searchByCompanyId".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
    		/*************************** 1.接收請求參數  **********************/
    		
    		Integer companyId = 0;
			try {
				companyId = Integer.parseInt(req.getParameter("companyId"));
			} catch (NumberFormatException e) {
				errorMsgs.add("請輸入正整數");
//				e.printStackTrace();
			}
    		/*************************** 2.開始查詢資料 *****************************************/
    		List<CompanyVO> companyVOList = new ArrayList<CompanyVO>();
    		CompanyVO companyVO = companySvc.getOneCompany(companyId);
    		if (companyVO != null) {
    			companyVOList.add(companyVO);
    		}
    		/*************************** 3.準備轉交(Send the Success view) *************/
    		req.setAttribute("companyVOList", companyVOList);
    		String url = "/back_end/admin/companyManagement.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    	}
    }
    
    
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


}
