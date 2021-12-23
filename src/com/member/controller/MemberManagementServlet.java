package com.member.controller;

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

import com.member.model.MemberService;
import com.member.model.MemberVO;

@WebServlet("/member/MemberManagementServlet")
public class MemberManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberManagementServlet() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    	req.setCharacterEncoding("UTF-8");
    	res.setContentType("text/html; charset=UTF-8");
//    	PrintWriter out = res.getWriter();
    	String action = req.getParameter("action");
    	MemberService memberSvc = new MemberService();
    	
    	// 將會員狀態設為啟用
    	if ("on".equals(action)) {
    		/*************************** 1.接收請求參數  **********************/
    		Integer memberId = Integer.parseInt(req.getParameter("memberId"));
    		/*************************** 2.開始修改資料 *****************************************/
    		MemberVO memberVO = memberSvc.getOneMember(memberId);
    		if (memberVO != null) {
    			memberSvc.updateMember(memberVO.getMemberId(), 1, memberVO.getMemberName(), memberVO.getMemberAccount(), memberVO.getMemberPassword(), memberVO.getMemberEmail(), memberVO.getMemberAddress(), memberVO.getMemberPhone(), memberVO.getMemberPic());
    		}
    		/*************************** 3.準備轉交(Send the Success view) *************/
    		String url = "/back_end/admin/memberManagement.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    	}
    	
    	// 將會員狀態設為停用
    	if ("off".equals(action)) {
    		/*************************** 1.接收請求參數  **********************/
    		Integer memberId = Integer.parseInt(req.getParameter("memberId"));
    		/*************************** 2.開始修改資料 *****************************************/
    		MemberVO memberVO = memberSvc.getOneMember(memberId);
    		if (memberVO != null) {
    			memberSvc.updateMember(memberVO.getMemberId(), 0, memberVO.getMemberName(), memberVO.getMemberAccount(), memberVO.getMemberPassword(), memberVO.getMemberEmail(), memberVO.getMemberAddress(), memberVO.getMemberPhone(), memberVO.getMemberPic());
    		}
    		/*************************** 3.準備轉交(Send the Success view) *************/
    		String url = "/back_end/admin/memberManagement.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    	}
    	
    	// 用會員編號查詢會員
    	if ("searchByMemberId".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
    		/*************************** 1.接收請求參數  **********************/
    		
    		Integer memberId = 0;
			try {
				memberId = Integer.parseInt(req.getParameter("memberId"));
			} catch (NumberFormatException e) {
				errorMsgs.add("請輸入正整數");
//				e.printStackTrace();
			}
    		/*************************** 2.開始查詢資料 *****************************************/
    		List<MemberVO> memberVOList = new ArrayList<MemberVO>();
    		MemberVO memberVO = memberSvc.getOneMember(memberId);
    		if (memberVO != null) {
    			memberVOList.add(memberVO);
    		}
    		/*************************** 3.準備轉交(Send the Success view) *************/
    		req.setAttribute("memberVOList", memberVOList);
    		String url = "/back_end/admin/memberManagement.jsp";
    		RequestDispatcher rd = req.getRequestDispatcher(url);
    		rd.forward(req, res);
    	}
    }
    
    
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


}
