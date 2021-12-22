package com.mallOrderDetail.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mallOrder.model.MallOrderService;
import com.mallOrderDetail.model.MallOrderDetailService;
import com.mallOrderDetail.model.MallOrderDetailVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;

@WebServlet("/MallOrderDetail/MallOrderDetailServlet")
public class MallOrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MallOrderDetailServlet() {

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		String action = req.getParameter("action");

		// 取得商品評論
		if ("getProductComments".equals(action)) {

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer productId = Integer.parseInt(req.getParameter("productId"));
			Integer orderType = Integer.parseInt(req.getParameter("orderType"));
			Integer page = Integer.parseInt(req.getParameter("page"));
			/*************************** 2.開始查詢資料 *****************************************/
			MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();
			MallOrderService mallOrderSvc = new MallOrderService();
			MemberService memberSvc = new MemberService();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<MallOrderDetailVO> mallOrderDetailVOList = mallOrderDetailSvc.getProductComments(productId, orderType,
					page);
			if (mallOrderDetailVOList.size() == 0) {
				out.println("[]");
				return;
			}
			
			JSONArray jsonArray = new JSONArray();

			try {

				for (MallOrderDetailVO mallOrderDetailVO : mallOrderDetailVOList) {
					JSONObject obj = new JSONObject();
					obj.put("memberName", memberSvc.getOneMember(mallOrderSvc.getOneMallOrder(mallOrderDetailVO.getMallOrderId()).getMemberId()).getMemberName());
					obj.put("productCommentStar", mallOrderDetailVO.getProductCommentStar());
					obj.put("productComment", mallOrderDetailVO.getProductComment());
					obj.put("productCommentTime", sdf.format(mallOrderDetailVO.getProductCommentTime()));
					
					jsonArray.put(obj);
				}
				
				out.println(jsonArray.toString());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

}
