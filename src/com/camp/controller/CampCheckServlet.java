package com.camp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camp.model.CampDAO;
import com.camp.model.CampDAOImpl;
import com.camp.model.CampService;
import com.camp.model.CampVO;
import com.company.model.CompanyService;

import util.MailService;

@WebServlet("/Camp/CampCheckServlet")
public class CampCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CampCheckServlet() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    	req.setCharacterEncoding("UTF-8");
    	res.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = res.getWriter();
    	HttpSession session = req.getSession();
    	
    	CampService campSvc = new CampService();
    	CompanyService companySvc = new CompanyService();
    	MailService mailSvc = new MailService();
    	String action = req.getParameter("action");

    	// 更換排序
    	if ("orderBy".equals(action)) {
    		Integer campCheckorder = Integer.parseInt(req.getParameter("order"));
    		session.setAttribute("campCheckorder", campCheckorder);
    		/*************************************準備轉交****************************************/
    		String url = "/back_end/admin/campCheck.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    		return;
    	}
    	
    	
    	// 營地審核通過
    	if ("pass".equals(action)) {
        	int campId = Integer.parseInt(req.getParameter("campId"));
    		/*************************************修改資料****************************************/
    		CampVO campVO = campSvc.findCampByCampId(campId);
    		// 變更狀態及上架時間
    		campVO.setCampStatus(1);
    		campVO.setCampLaunchedTime(new Timestamp(System.currentTimeMillis()));
    		campSvc.updateCamp(campVO);
    		// 寄email
    		String messageText = "您的營地 " + campVO.getCampName() + " 經管理員審核通過，已幫您上架";
    		mailSvc.sendMail(companySvc.getOneCompany(campVO.getCompanyId()).getCompanyEmail() , "Camping Paradise-營地上架成功", messageText);
    		/*************************************準備轉交****************************************/
    		String url = "/back_end/admin/campCheck.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    		return;
    	}
    	
    	// 營地審核不通過
    	if ("fail".equals(action)) {
        	int campId = Integer.parseInt(req.getParameter("campId"));
    		/*************************************修改資料****************************************/
        	CampVO campVO = campSvc.findCampByCampId(campId);
        	// 變更狀態及上架時間
    		campVO.setCampStatus(0);
    		campSvc.updateCamp(campVO);
    		// 寄email
    		String messageText = "您的營地 " + campVO.getCampName() + " 經管理員審核不通過，請重新提出申請";
    		mailSvc.sendMail(companySvc.getOneCompany(campVO.getCompanyId()).getCompanyEmail() , "Camping Paradise-營地上架失敗", messageText);
    		/*************************************準備轉交****************************************/
    		String url = "/back_end/admin/campCheck.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    		return;
    	}
    }
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


}
