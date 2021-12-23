package camp.test;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

/**
 * Servlet implementation class EpatTest
 */
@WebServlet("/EpatTest")
public class EpatTest extends HttpServlet {
	public static AllInOne all;
	
	


		

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "300");
		res.setHeader("Access-Control-Allow-Headers", "content-type, x-requested-with");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setContentType("text/html;charset=UTF-8");
		PrintWriter out=res.getWriter();
		//服務參數
		String ServiceURL  = req.getParameter("ServiceURL");
		String HashKey    = req.getParameter("HashKey");
		String HashIV     = req.getParameter("HashIV");
		String MerchantID =req.getParameter("MerchantID");
		 
		
		
	
		String MerchantTradeNo =  req.getParameter("MerchantTradeNo");
		String MerchantTradeDate =  req.getParameter("MerchantTradeDate");
		String PaymentType =  req.getParameter("PaymentType");
		String TotalAmount =  req.getParameter("TotalAmount");
		String TradeDesc =  req.getParameter("TradeDesc");
		String ReturnURL =  req.getParameter("ReturnURL");
		String ChoosePayment =  req.getParameter("ChoosePayment");
		String CreditInstallment =  req.getParameter("CreditInstallment");
	
	out.println(genAioCheckOutALL());

		
		

	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		doGet(req, res);
	}
	
	
	
	public static String genAioCheckOutALL(){
		all = new AllInOne("");
		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo("testCompany100000324");
		obj.setMerchantTradeDate("2017/01/01 08:05:23");
		obj.setTotalAmount("1200");
		obj.setTradeDesc("test Description");
		obj.setItemName("A區1帳#B區2帳#加購人頭3人");
		
		obj.setReturnURL("http://localhost:8081/Greenisland/Index.html");
		obj.setNeedExtraPaidInfo("N");
		
		System.out.println(obj);
		String form = all.aioCheckOut(obj, null);
		return form;
	}
	

}
