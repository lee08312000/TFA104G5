package com.camp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.campBooking.model.CampBookingService;
import com.campOrder.model.CampOrderService;
import com.campOrder.model.CampOrderVO;

@WebServlet("/CampCalManger")
public class CampCalMangerServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		System.out.println(action);
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "300");
		res.setHeader("Access-Control-Allow-Headers", "content-type, x-requested-with");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setContentType("text/html;charset=UTF-8");
		PrintWriter out = res.getWriter();

		if ("monthchoose".equals(action)) {
			String year = req.getParameter("yearNum"); // 月曆畫面年分
			String month = req.getParameter("monthNum"); // 月曆畫面月份
			String campid = req.getParameter("campid"); // 營地id
			
			CampBookingService campbookingSvc = new CampBookingService();
			List list = campbookingSvc.CalendarUse(Integer.parseInt(campid), year + "-" + month + "-01", 1);

			JSONArray jsArray = new JSONArray(list);

			out.print(jsArray.toString());
			return;
		}

		if ("seeorder".equals(action)) {
			String date = req.getParameter("date"); // 點擊日期
			String campid = req.getParameter("campid"); // 營地id

			CampOrderService camporderSvc = new CampOrderService();
			List<CampOrderVO> orderlist = camporderSvc.OrderByCheckin(java.sql.Date.valueOf(date),
					Integer.parseInt(campid));

			JSONArray jsArray = new JSONArray(orderlist);

			out.print(jsArray.toString());
			return;

		}

		if ("closedate".equals(action)) {

			String closeddate = req.getParameter("closedate");
			String campid = req.getParameter("campid");
			

			System.out.println(closeddate);
			// 驗證日期
			String regex = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

			if (closeddate.matches(regex)) {
				System.out.println("想要新增公休日=" + closeddate);
				CampBookingService campbookingSvc = new CampBookingService();
				int code = campbookingSvc.updateCloseStatus(Integer.parseInt(campid), closeddate);
				String codemessage = null;
				switch (code) {
				case 1:
					codemessage = "此日期已有人預約，請確認訂單";
					break;
				case 2:
					codemessage = "此日期已設公休日，請確認日期";
					break;
				case 3:
					codemessage = "公休日新增成功";
					break;
				case 4:
					codemessage = "請確認輸入格式";
					break;
				default:
					codemessage = "請確認輸入格式";

				}
				
				out.print(codemessage);

			}

		}

	}

}
