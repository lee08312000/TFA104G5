package com.mallOrder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.company.model.CompanyDAOImpl;
import com.company.model.CompanyService;
import com.company.model.CompanyVO;
import com.mallOrder.model.MallOrderDAOImpl;
import com.mallOrder.model.MallOrderService;
import com.mallOrder.model.MallOrderVO;
import com.mallOrderDetail.model.MallOrderDetailService;
import com.mallOrderDetail.model.MallOrderDetailVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.product.model.ProductService;


@WebServlet("/mallOrder/CompanyBrowseServlet")
public class CompanyBrowseServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public CompanyBrowseServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		MallOrderService mallOrderSvc = new MallOrderService();
		MemberService memberSvc = new MemberService();
		MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();
		ProductService productSvc = new ProductService();
//		HttpSession session = req.getSession();
//		CompanyVO companyVO = (CompanyVO) session.getAttribute("companyVO");
		CompanyVO companyVO = new CompanyVO();
		companyVO.setCompanyId(1);
		
		String action = req.getParameter("action");
		
		//顯示該廠商所有訂單
		if ("getMallOrders".equals(action)) {			
			/*************************** 1.開始查詢資料 *****************************************/
			List<MallOrderVO> mallOrderList = mallOrderSvc.getMallOrderByCompany(companyVO.getCompanyId());
			/*************************** 2.準備轉交(Send the Success view) *************/
			JSONObject jsonObj = null;
			JSONArray jsonArray = new JSONArray();
			String jsonArrayStr = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try {
				for (MallOrderVO mallOrderVO : mallOrderList) {
					jsonObj = new JSONObject();					
					jsonObj.put("mallOrderId", mallOrderVO.getMallOrderId());
					jsonObj.put("memberName", memberSvc.getOneMember(mallOrderVO.getMemberId()).getMemberName());
					jsonObj.put("receiverPhone", mallOrderVO.getReceiverPhone());
					jsonObj.put("mailOrderTotalAmount", mallOrderVO.getMailOrderTotalAmount());
					jsonObj.put("mallOrderStatus", mallOrderVO.getMallOrderStatus());	
					jsonObj.put("mallOrderDeliveryStatus", mallOrderVO.getMallOrderDeliveryStatus());	
					jsonObj.put("mallOrderConfirmedTime", sdf.format(mallOrderVO.getMallOrderConfirmedTime()));					

					jsonArray.put(jsonObj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			jsonArrayStr = jsonArray.toString();
			out.println(jsonArrayStr);
//			System.out.println(jsonArrayStr);
		}
		
		//顯示該廠商訂單by訂單狀態
				if ("getMallOrdersByOrderStatus".equals(action)) {
					
					Integer mallOrderStatus = Integer.parseInt(req.getParameter("mallOrderStatus"));
					/*************************** 1.開始查詢資料 *****************************************/
					List<MallOrderVO> mallOrderList = mallOrderSvc.getMallOrdersByOrderStatus(companyVO.getCompanyId(),mallOrderStatus);
					/*************************** 2.準備轉交(Send the Success view) *************/
					JSONObject jsonObj = null;
					JSONArray jsonArray = new JSONArray();
					String jsonArrayStr = "";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					try {
						for (MallOrderVO mallOrderVO : mallOrderList) {
							jsonObj = new JSONObject();					
							jsonObj.put("mallOrderId", mallOrderVO.getMallOrderId());
							jsonObj.put("memberName", memberSvc.getOneMember(mallOrderVO.getMemberId()).getMemberName());
							jsonObj.put("receiverPhone", mallOrderVO.getReceiverPhone());
							jsonObj.put("mailOrderTotalAmount", mallOrderVO.getMailOrderTotalAmount());
							jsonObj.put("mallOrderStatus", mallOrderVO.getMallOrderStatus());	
							jsonObj.put("mallOrderDeliveryStatus", mallOrderVO.getMallOrderDeliveryStatus());	
							jsonObj.put("mallOrderConfirmedTime", sdf.format(mallOrderVO.getMallOrderConfirmedTime()));					
							
							jsonArray.put(jsonObj);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

					jsonArrayStr = jsonArray.toString();
					out.println(jsonArrayStr);
					System.out.println(jsonArrayStr);
				}
				
				
				//顯示該廠商訂單by物流狀態
				if ("getMallOrdersByDeliveryStatus".equals(action)) {
					
					Integer deliveryStatus = Integer.parseInt(req.getParameter("deliveryStatus"));
					/*************************** 1.開始查詢資料 *****************************************/
					List<MallOrderVO> mallOrderList = mallOrderSvc.getMallOrdersByDeliveryStatus(companyVO.getCompanyId(),deliveryStatus);
					/*************************** 2.準備轉交(Send the Success view) *************/
					JSONObject jsonObj = null;
					JSONArray jsonArray = new JSONArray();
					String jsonArrayStr = "";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					try {
						for (MallOrderVO mallOrderVO : mallOrderList) {
							jsonObj = new JSONObject();					
							jsonObj.put("mallOrderId", mallOrderVO.getMallOrderId());
							jsonObj.put("memberName", memberSvc.getOneMember(mallOrderVO.getMemberId()).getMemberName());
							jsonObj.put("receiverPhone", mallOrderVO.getReceiverPhone());
							jsonObj.put("mailOrderTotalAmount", mallOrderVO.getMailOrderTotalAmount());
							jsonObj.put("mallOrderStatus", mallOrderVO.getMallOrderStatus());	
							jsonObj.put("mallOrderDeliveryStatus", mallOrderVO.getMallOrderDeliveryStatus());	
							jsonObj.put("mallOrderConfirmedTime", sdf.format(mallOrderVO.getMallOrderConfirmedTime()));					

							jsonArray.put(jsonObj);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

					jsonArrayStr = jsonArray.toString();
					out.println(jsonArrayStr);
					System.out.println(jsonArrayStr);
				}				
				
				//顯示該廠商訂單by訂單編號
				if ("getMallOrdersById".equals(action)) {
					
					Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
					/*************************** 1.開始查詢資料 *****************************************/
					MallOrderVO mallOrderVO = mallOrderSvc.getOneMallOrderByCompany(companyVO.getCompanyId(),mallOrderId);
					/*************************** 2.準備轉交(Send the Success view) *************/
					if (mallOrderVO == null) {
						out.println("[]");
						return;
					}
					JSONObject jsonObj = null;
					String jsonObjStr = "";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					try {						
							jsonObj = new JSONObject();					
							jsonObj.put("mallOrderId", mallOrderVO.getMallOrderId());
							jsonObj.put("memberName", memberSvc.getOneMember(mallOrderVO.getMemberId()).getMemberName());
							jsonObj.put("receiverPhone", mallOrderVO.getReceiverPhone());
							jsonObj.put("mailOrderTotalAmount", mallOrderVO.getMailOrderTotalAmount());
							jsonObj.put("mallOrderStatus", mallOrderVO.getMallOrderStatus());	
							jsonObj.put("mallOrderDeliveryStatus", mallOrderVO.getMallOrderDeliveryStatus());	
							jsonObj.put("mallOrderConfirmedTime", sdf.format(mallOrderVO.getMallOrderConfirmedTime()));					
				
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					jsonObjStr = jsonObj.toString();
					out.println(jsonObjStr);
//					System.out.println(jsonObjStr);
				}				
				
		
				//顯示該廠商訂單明細
				if ("getMallOrderDetail".equals(action)) {
					
					Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
					/*************************** 1.開始查詢資料 *****************************************/
					List<MallOrderDetailVO> mallOrderDetailList = mallOrderDetailSvc.getBymallOrderId(mallOrderId);
					/*************************** 2.準備轉交(Send the Success view) *************/
					JSONObject jsonObj = null;
					JSONArray jsonArray = new JSONArray();
					String jsonArrayStr = "";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					try {
						for (MallOrderDetailVO mallOrderDetailVO : mallOrderDetailList) {
							jsonObj = new JSONObject();					
							jsonObj.put("mallOrderId", mallOrderDetailVO.getMallOrderId());
							jsonObj.put("mallOrderConfirmedTime", sdf.format(mallOrderSvc.getOneMallOrder(mallOrderDetailVO.getMallOrderId()).getMallOrderConfirmedTime()));
							jsonObj.put("productId", mallOrderDetailVO.getProductId());
							jsonObj.put("productName", productSvc.getOneProduct(mallOrderDetailVO.getMallOrderId()).getProductName());
							jsonObj.put("productPurchasePrice", mallOrderDetailVO.getProductPurchasePrice());
							jsonObj.put("productPurchaseQuantity", mallOrderDetailVO.getProductPurchaseQuantity());
							jsonObj.put("mallOrderStatus", mallOrderSvc.getOneMallOrder(mallOrderDetailVO.getMallOrderId()).getMallOrderStatus());
							jsonObj.put("mallOrderDeliveryStatus", mallOrderSvc.getOneMallOrder(mallOrderDetailVO.getMallOrderId()).getMallOrderDeliveryStatus());
							jsonObj.put("mailOrderTotalAmount", mallOrderSvc.getOneMallOrder(mallOrderDetailVO.getMallOrderId()).getMailOrderTotalAmount());
							jsonObj.put("receiverName", mallOrderSvc.getOneMallOrder(mallOrderDetailVO.getMallOrderId()).getReceiverName());
							jsonObj.put("receiverPhone", mallOrderSvc.getOneMallOrder(mallOrderDetailVO.getMallOrderId()).getReceiverPhone());
							jsonObj.put("receiverAddress", mallOrderSvc.getOneMallOrder(mallOrderDetailVO.getMallOrderId()).getReceiverAddress());

							jsonArray.put(jsonObj);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

					jsonArrayStr = jsonArray.toString();
					out.println(jsonArrayStr);
					System.out.println(jsonArrayStr);
				}
		
		
				//確認訂單
				if ("updateMallOrderStatus".equals(action)) {
					try {
						String jsonObjStr = "[]";
						
						Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
						MallOrderVO mallOrderVO = mallOrderSvc.getOneMallOrder(mallOrderId);
						mallOrderVO.setMallOrderStatus(1);
						MallOrderDAOImpl mallOrderDao = new MallOrderDAOImpl();
						mallOrderDao.update(mallOrderVO);						
						/***************************修改狀態完成,準備轉交(Send the Success view)***********/
						out.println(jsonObjStr);
					}catch (Exception e) {
						e.printStackTrace();
					}					
				}
		
		
				//確認訂單
				if ("updateMallOrderdDeliveryStatus".equals(action)) {
					try {
						String jsonObjStr = "[]";
						
						Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
						MallOrderVO mallOrderVO = mallOrderSvc.getOneMallOrder(mallOrderId);
						mallOrderVO.setMallOrderDeliveryStatus(1);
						MallOrderDAOImpl mallOrderDao = new MallOrderDAOImpl();
						mallOrderDao.update(mallOrderVO);						
						/***************************修改狀態完成,準備轉交(Send the Success view)***********/
						out.println(jsonObjStr);
					}catch (Exception e) {
						e.printStackTrace();
					}					
				}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
