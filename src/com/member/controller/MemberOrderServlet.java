package com.member.controller;

import java.io.IOException;
import java.sql.Timestamp;
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
import com.campOrder.model.CampOrderDAOImpl;
import com.campOrder.model.CampOrderService;
import com.campOrder.model.CampOrderVO;
import com.mallOrderDetail.model.MallOrderDetailDAOImpl;
import com.mallOrderDetail.model.MallOrderDetailService;
import com.mallOrderDetail.model.MallOrderDetailVO;
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
		String action = req.getParameter("action");
		
		/*************************** 轉交資訊給memberCampOrderDetail得以顯示資料 **********************/
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
		/*************************** 轉交資訊給memberCampOrderDetail得以顯示資料 **********************/
	
		
		/*************************** 營地評論  **********************/
		if ("action".equals(action)) {
			try {
				/***************************1.接收請求參數****************************************/
				Integer campOrderId = new Integer(req.getParameter("campOrderId"));
				MemberVO memberVO =  (MemberVO)session.getAttribute("memberVO");
				
				/***************************2.開始查詢資料****************************************/
				CampOrderService campOrderSvc =  new CampOrderService();
				List<CampOrderVO> list = campOrderSvc.OrderByUserId(memberVO.getMemberId());
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("list", list);         // 資料庫取出的empVO物件,存入req
				String url = "/front_end/member/jsp/member_camp_order_detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {				
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/member/jsp/member_product_order_list.jsp");
				failureView.forward(req, res);
			}
			
		/*************************** 營地評論  **********************/	
				
		}
		
		//訂單評論送出
		if ("updateComment".equals(action)) {
			try {
				
				/***************************1.修改訂單狀態(Send the Success view)***********/
				Integer campOrderId = Integer.parseInt(req.getParameter("campOrderId"));
				MemberVO memberVO =  (MemberVO)session.getAttribute("memberVO");
				Integer starNum = Integer.parseInt(req.getParameter("starNum"));						
				String commentText = req.getParameter("commentText").trim();
				
				CampOrderService campOrderSvc = new CampOrderService();
				CampOrderVO campOrderVO = campOrderSvc.findByCampOrderId(campOrderId);
				campOrderVO.setCampCommentStar(starNum);
				campOrderVO.setCampComment(commentText);
				campOrderVO.setCampOrderCommentTime(new Timestamp(System.currentTimeMillis()));
				CampOrderDAOImpl campOrderDAO = new CampOrderDAOImpl();
				campOrderDAO.update(campOrderVO);
				

				List<CampOrderVO> list = campOrderSvc.OrderByUserId(memberVO.getMemberId());
				/***************************2.修改狀態完成,準備轉交(Send the Success view)***********/
				req.setAttribute("list", list); // 資料庫update成功後,重新抓取訂單明細
				String url = "/front_end/member/jsp/member_camp_order_detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
								
			}catch (Exception e) {
				e.printStackTrace();
			}					
		}

	}
}
