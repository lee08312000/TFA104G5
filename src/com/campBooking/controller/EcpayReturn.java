package com.campBooking.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EcpayReturn")
public class EcpayReturn extends HttpServlet {
//	RtnCode 交易狀態 Int 1:成功，其餘為失敗
//	RtnMsg 交易訊息 String (200) 交易訊息 停用成功
//	MerchantID 特店編號 String(10) 2000132
//	MerchantTradeNo 特店交易編號 String(20)
//	訂單產生時傳送給綠界的特店交易編號。123456abc
//	CheckMacValue 檢查碼 String
//	特店必須檢查 CheckMacValue 來

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "300");
		res.setHeader("Access-Control-Allow-Headers", "content-type, x-requested-with");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setContentType("text/html;charset=UTF-8");
		PrintWriter out = res.getWriter();
		String RtnCode = req.getParameter("RtnCode");//付款狀態
		String MerchantTradeNo = req.getParameter("MerchantTradeNo");//訂單編號(綠界查詢用)
		String PaymentDate = req.getParameter("PaymentDate"); //付款日期
		System.out.println("code=" + RtnCode);
		String orderId=null; //訂單id;
		//取出訂單id
		if(MerchantTradeNo!=null&&MerchantTradeNo.length()!=0) {
			StringTokenizer tk=new StringTokenizer(MerchantTradeNo,"cp");
			if(tk.hasMoreElements()) {
				orderId=tk.nextToken();
				System.out.println("訂單id是="+orderId);
			}

		}
		
//傳遞訂單編號跟訂單成立時間
		if ("1".equals(RtnCode)) {
			req.setAttribute("orderId", orderId);
			req.setAttribute("PaymentDate", PaymentDate);
			String url = "/front_end/camp/redirect.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;

		}

//		Enumeration en = req.getParameterNames();
//		System.out.println("綠界回傳參數");
//
//		while (en.hasMoreElements()) {
//
//			String name = (String) en.nextElement();
//			String[] value = req.getParameterValues(name);
//			System.out.println(name + Arrays.toString(value));
//
//		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
