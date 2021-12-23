package com.member.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camp.model.CampService;
import com.camp.model.CampVO;
import com.campOrder.model.CampOrderService;
import com.campOrder.model.CampOrderVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.campAreaOrderDetail.model.*;

@WebServlet("/member/MemberOrderServlet")
public class MemberOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberOrderServlet() {
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

		String detail = req.getParameter("detail");
		
		if ("detail".equals(detail)) {

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			CampOrderService campOrderService = new CampOrderService();
			CampAreaOrderDetailDAO campAreaOrderDetailDAO =  new CampAreaOrderDetailDAOImpl();
			Integer campOrderId = Integer.parseInt(req.getParameter("campOrderId"));
			System.out.println(campOrderId);

			/*************************** 2.開始查詢資料 *****************************************/
			CampOrderVO campOrderVO = campOrderService.findByCampOrderId(campOrderId);
			if (campOrderVO != null) {
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				session.setAttribute("campOrderVO", campOrderVO); // 成功登入的話
				session.setAttribute("campAreaOrderDetailList", campAreaOrderDetailDAO.findByCampOrderId(campOrderId));
				System.out.println("跳轉明細成功");

				String url = "/front_end/member/jsp/member_camp_order_detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功跳頁面
				successView.forward(req, res);

			} else {
				String url = "/front_end/member/jsp/member_camp_order_list.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url); // 失敗轉交 login.jsp
				failureView.forward(req, res);
				return; // 程式在此中斷
			}

		}
		
		
		
		
		
	}

}
