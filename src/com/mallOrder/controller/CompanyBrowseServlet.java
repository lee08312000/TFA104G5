package com.mallOrder.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Hashtable;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mallOrder.model.MallOrderDAOImpl;
import com.mallOrder.model.MallOrderService;
import com.mallOrder.model.MallOrderVO;
import com.mallOrderDetail.model.MallOrderDetailService;
import com.mallOrderDetail.model.MallOrderDetailVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.product.model.ProductService;

import redis.clients.jedis.Jedis;


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
		HttpSession session = req.getSession();
		CompanyVO companyVO = (CompanyVO) session.getAttribute("companyVO");
//		CompanyVO companyVO = new CompanyVO();
//		companyVO.setCompanyId(1);
		
		String action = req.getParameter("action");
		
		//???????????????????????????
		if ("getMallOrders".equals(action)) {			
			/*************************** 1.?????????????????? *****************************************/
			List<MallOrderVO> mallOrderList = mallOrderSvc.getMallOrderByCompany(companyVO.getCompanyId());
			/*************************** 2.????????????(Send the Success view) *************/
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
		
		//?????????????????????by????????????
				if ("getMallOrdersByOrderStatus".equals(action)) {
					
					Integer mallOrderStatus = Integer.parseInt(req.getParameter("mallOrderStatus"));
					/*************************** 1.?????????????????? *****************************************/
					List<MallOrderVO> mallOrderList = mallOrderSvc.getMallOrdersByOrderStatus(companyVO.getCompanyId(),mallOrderStatus);
					/*************************** 2.????????????(Send the Success view) *************/
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
				
				
				//?????????????????????by????????????
				if ("getMallOrdersByDeliveryStatus".equals(action)) {
					
					Integer deliveryStatus = Integer.parseInt(req.getParameter("deliveryStatus"));
					/*************************** 1.?????????????????? *****************************************/
					List<MallOrderVO> mallOrderList = mallOrderSvc.getMallOrdersByDeliveryStatus(companyVO.getCompanyId(),deliveryStatus);
					/*************************** 2.????????????(Send the Success view) *************/
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
				
				//?????????????????????by????????????
				if ("getMallOrdersById".equals(action)) {
					
					Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
					/*************************** 1.?????????????????? *****************************************/
					MallOrderVO mallOrderVO = mallOrderSvc.getOneMallOrderByCompany(companyVO.getCompanyId(),mallOrderId);
					/*************************** 2.????????????(Send the Success view) *************/
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
				
		
				//???????????????????????????
				if ("getMallOrderDetail".equals(action)) {
					
					Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
					/*************************** 1.?????????????????? *****************************************/
					List<MallOrderDetailVO> mallOrderDetailList = mallOrderDetailSvc.getBymallOrderId(mallOrderId);
					/*************************** 2.????????????(Send the Success view) *************/
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
		
		
				//????????????
				if ("updateMallOrderStatus".equals(action)) {
					try {
						String jsonObjStr = "[]";
						
						Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
						MallOrderVO mallOrderVO = mallOrderSvc.getOneMallOrder(mallOrderId);
						mallOrderVO.setMallOrderStatus(1);
						MallOrderDAOImpl mallOrderDao = new MallOrderDAOImpl();
						mallOrderDao.update(mallOrderVO);						
						/***************************??????????????????,????????????(Send the Success view)***********/
						out.println(jsonObjStr);
					}catch (Exception e) {
						e.printStackTrace();
					}					
				}
		
		
				//????????????
				if ("updateMallOrderdDeliveryStatus".equals(action)) {
					try {
						String jsonObjStr = "[]";
						
						Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
						MallOrderVO mallOrderVO = mallOrderSvc.getOneMallOrder(mallOrderId);
						mallOrderVO.setMallOrderDeliveryStatus(1);
						MallOrderDAOImpl mallOrderDao = new MallOrderDAOImpl();
						mallOrderDao.update(mallOrderVO);
						
						//??????qrcode___________
						String scheme = req.getScheme();
						String serverName = req.getServerName();
						String serverPort = req.getServerPort() + "";
						String contextPath = req.getContextPath();
						String realContextPath = scheme +"://"+serverName +":"+ serverPort + contextPath;
						String text = realContextPath + "/Member/MemberProductServlet?action=updateMallOrderAllStatusQrcode&mallOrderId="+mallOrderId;
						int width = 300;
						int height = 300;
						String format = "jpg";
						// ??????????????????????????????
						Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
						hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
						hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);				
						byte[] qrCode = null;
						// ????????????QRCode
						BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						
						MatrixToImageWriter.writeToStream(matrix, format, baos);
						qrCode = baos.toByteArray();
						String base64str = Base64.getEncoder().encodeToString(qrCode);
						Jedis jedis = null;
						try {
							jedis = new Jedis("localhost", 6379);
							jedis.set("mallOrder:"+ mallOrderId, base64str);
						}finally {
							if(jedis != null)
							   jedis.close();
						}
						/***************************??????????????????,????????????(Send the Success view)***********/
						out.println(jsonObjStr);
					}catch (Exception e) {
						e.printStackTrace();
					}					
				}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
