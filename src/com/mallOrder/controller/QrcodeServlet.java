package com.mallOrder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mallOrder.model.MallOrderVO;
import com.mallOrder.model.QrcodeService;

@WebServlet("/mallOrder/QrcodeServlet")
public class QrcodeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");
		
		//傳回base64str
				if ("getQrcode".equals(action)) {
					
					/*************************** 1.開始查詢資料 *****************************************/
					Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
					QrcodeService qrcodeSvc= new QrcodeService();
					String base64str= qrcodeSvc.getQrCode(mallOrderId);
					/*************************** 2.準備轉交(Send the Success view) *************/
					JSONObject jsonObj = null;
					String jsonObjStr = "";
					try {						
						jsonObj = new JSONObject();					
						jsonObj.put("qrcode", base64str);
						
					
					} catch (JSONException e) {
						e.printStackTrace();
					}
						
						jsonObjStr = jsonObj.toString();
						out.println(jsonObjStr);

					
				}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
