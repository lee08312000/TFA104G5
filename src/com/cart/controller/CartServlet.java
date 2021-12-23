package com.cart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

import com.cart.model.CartVO;
import com.cart.model.ReceiverVO;
import com.mallOrder.model.MallOrderService;
import com.mallOrder.model.MallOrderVO;
import com.mallOrderDetail.model.MallOrderDetailService;
import com.mallOrderDetail.model.MallOrderDetailVO;
import com.member.model.MemberVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;

import util.MailService;

@WebServlet("/Cart/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CartServlet() {
		super();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		HttpSession session = req.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		List<CartVO> buyList = (List<CartVO>) session.getAttribute("buyList");
		String action = req.getParameter("action");

		// 從商城新增商品到購物車
		if ("add".equals(action)) {

			ProductService productSvc = new ProductService();
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer productId = Integer.parseInt(req.getParameter("productId").trim());
			Integer productPurchaseQuantity = Integer.parseInt(req.getParameter("productPurchaseQuantity").trim());
			/*************************** 2.開始修改資料 *****************************************/
			ProductVO productVO = productSvc.getOneProduct(productId);
			int productInventory = productVO.getProductInventory().intValue();
			int newProductPurchaseQuantity = 0;
			boolean match = false;
			String msg = "denied";

			if (productPurchaseQuantity.intValue() <= 0) {
				out.println(msg);
				return;
			}
			// 新的商品
			CartVO cartVO = new CartVO();
			cartVO.setCompanyId(productVO.getCompanyId());
			cartVO.setProductId(productId);
			cartVO.setProductTypeId(productVO.getProductTypeId());
			cartVO.setProductName(productVO.getProductName());
			cartVO.setProductPrice(productVO.getProductPrice());
			cartVO.setProductPurchaseQuantity(productPurchaseQuantity);

			// 若沒有buyList 或 裡面沒東西時
			if (buyList == null || buyList.size() == 0) {
				// 若庫存量大於等於選購數量
				if (productInventory >= productPurchaseQuantity.intValue()) {
					buyList = new ArrayList<CartVO>();
					buyList.add(cartVO);
					msg = "success";
				} else {
					// 若庫存量小於選購數量
					// 若庫存量>0將購買數量設為庫存量加回購物車中
					if (productInventory > 0) {
						buyList = new ArrayList<CartVO>();
						cartVO.setProductPurchaseQuantity(productInventory);
						buyList.add(cartVO);
					}
				}
			} else {
				// buyList 有東西時
				for (int i = 0; i < buyList.size(); i++) {
					CartVO c = buyList.get(i);
					// buyList 有東西時，且購物車已有欲加入的商品時
					if (cartVO.getProductId().intValue() == c.getProductId().intValue()) {
						match = true;
						newProductPurchaseQuantity = cartVO.getProductPurchaseQuantity()
								+ c.getProductPurchaseQuantity();
						// 若庫存量大於等於選購數量(剛剛新增的加上購物車裡的)
						if (productInventory >= newProductPurchaseQuantity) {
							cartVO.setProductPurchaseQuantity(newProductPurchaseQuantity);
							buyList.set(i, cartVO);
							msg = "success";
						} else {
							// 若庫存量小於選購數量(剛剛新增的加上購物車裡的)
							// 若庫存量>0將購買數量設為庫存量加回購物車中
							if (productInventory > 0) {
								cartVO.setProductPurchaseQuantity(productInventory);
								buyList.set(i, cartVO);
							}
						}

					}
				}
				// buyList 有東西時，購物車沒有欲加入的商品時
				if (!match) {
					// 若庫存量大於等於選購數量
					if (productInventory >= productPurchaseQuantity.intValue()) {
						buyList.add(cartVO);
						msg = "success";
					} else { // 若庫存量小於選購數量
						if (productInventory > 0) {
							cartVO.setProductPurchaseQuantity(productInventory);
							buyList.add(cartVO);
						}
					}
				}

			}
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			session.setAttribute("buyList", buyList);
			// 回傳Json給Ajax
			JSONObject obj = new JSONObject();
			try {
				obj.put("msg", msg);
				obj.put("productInventory", productInventory);
				String msgJson = obj.toString();
//				System.out.println(msgJson);
				out.println(msgJson);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// 刪除購物車商品
		if ("delete".equals(action)) {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			int productId = Integer.parseInt(req.getParameter("productId"));
			/*************************** 2.開始修改資料 *****************************************/
			for (int i = 0; i < buyList.size(); i++) {
				CartVO cartVO = buyList.get(i);
				if (productId == cartVO.getProductId().intValue()) {
					buyList.remove(i);
					i--;
				}
			}
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			session.setAttribute("buyList", buyList);
			// 回傳Json給Ajax
			JSONObject obj = new JSONObject();
			try {
				obj.put("msg", "success");
				String msg = obj.toString();
//				System.out.println(msg);
				out.println(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		// 更新購買清單by Ajax
		if ("updateByAjax".equals(action)) {
			ProductService productSvc = new ProductService();
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer productId = Integer.parseInt(req.getParameter("productId").trim());
			Integer productPurchaseQuantity = Integer.parseInt(req.getParameter("productPurchaseQuantity").trim());
			Integer productInventory = productSvc.getOneProduct(productId).getProductInventory();
			String msg = "";
			/*************************** 2.開始修改資料 *****************************************/
			if (productPurchaseQuantity.intValue() > productInventory.intValue()) {

				for (int i = 0; i < buyList.size(); i++) {
					CartVO c = buyList.get(i);
					if (productId.intValue() == c.getProductId().intValue()) {
						c.setProductPurchaseQuantity(productInventory);
						buyList.set(i, c);
					}
				}
				msg = "denied";
			} else {
				for (int i = 0; i < buyList.size(); i++) {
					CartVO c = buyList.get(i);
					if (productId.intValue() == c.getProductId().intValue()) {
						c.setProductPurchaseQuantity(productPurchaseQuantity);
						buyList.set(i, c);
					}
				}
				msg = "success";
			}
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			session.setAttribute("buyList", buyList);
			// 回傳Json給Ajax
			JSONObject obj = new JSONObject();
			try {
				obj.put("msg", msg);
				obj.put("productInventory", productInventory);
				String msgJson = obj.toString();
//				System.out.println(msgJson);
				out.println(msgJson);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// 更新購買清單
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String[] productPurchaseQuantityArray = req.getParameterValues("productPurchaseQuantity");
			String[] productNameArray = req.getParameterValues("productName");

			for (int i = 0; i < productPurchaseQuantityArray.length; i++) {
				if ("".equals(productPurchaseQuantityArray[i])
						|| Integer.parseInt(productPurchaseQuantityArray[i]) <= 0) {
					errorMsgs.add(productNameArray[i] + " 請輸入正確數量");
				}
			}

			buyList = getBuyList(req);

			// 檢查庫存量及購買流程時廠商是否更動價格
			ProductService productSvc = new ProductService();
			for (int i = 0; i < buyList.size(); i++) {
				CartVO cartVO = buyList.get(i);
				ProductVO productVO = productSvc.getOneProduct(cartVO.getProductId());
				// 檢查庫存量
				Integer productInventory = productVO.getProductInventory();
				Integer productPurchaseQuantity = cartVO.getProductPurchaseQuantity();
				if ((productInventory.intValue() - productPurchaseQuantity.intValue()) < 0) {
					cartVO.setProductPurchaseQuantity(productInventory);
					if (cartVO.getProductPurchaseQuantity().intValue() == 0) {
						buyList.remove(i);
						i--;
					} else {
						buyList.set(i, cartVO);
					}
					errorMsgs.add(cartVO.getProductName() + " 的庫存量為" + productInventory + "，請重新確認數量");
				}

				// 檢查購買流程時廠商是否更動價格
				Integer realPrice = productVO.getProductPrice();
				Integer cartPrice = cartVO.getProductPrice();
				if (cartPrice.intValue() != realPrice.intValue()) {
					cartVO.setProductPrice(realPrice);
					buyList.set(i, cartVO);
					errorMsgs.add(cartVO.getProductName() + " : 廠商在您 shopping 時更動了價格，請重新確認");
				}
				// 檢查購買流程時廠商是否下價商品
				if (productVO.getProductStatus().intValue() == 0) {
					buyList.remove(i);
					i--;
					errorMsgs.add(cartVO.getProductName() + " : 商品已下架，請重新確認");
				}
			}

			session.setAttribute("buyList", buyList);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/mall/shoppingCart01.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/

//			buyList = getBuyList(req);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
//			session.setAttribute("buyList", buyList);
			//
			ReceiverVO receiverVO = new ReceiverVO();
			receiverVO.setReceiverName(req.getParameter("receiverName"));
			receiverVO.setReceiverPhone(req.getParameter("receiverPhone"));
			receiverVO.setReceiverAddress(req.getParameter("receiverAddress"));
			receiverVO.setCreditCardNum(req.getParameter("creditCardNum"));
			receiverVO.setSecurityCode(req.getParameter("securityCode"));
			receiverVO.setEffectiveDate(req.getParameter("effectiveDate"));

			req.setAttribute("receiverVO", receiverVO);
			//
			String url = "/front_end/mall/shoppingCart02.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}
		// 購物車step2 得到會員資料資料
		if ("getMemberInfo".equals(action)) {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String msg = "denied";
			String memberName = "";
			String memberPhone = "";
			String memberAddress = "";

			if (memberVO != null) {
				memberName = memberVO.getMemberName();
				memberPhone = memberVO.getMemberPhone();
				memberAddress = memberVO.getMemberAddress();
				msg = "success";
			}

			// 回傳Json給Ajax
			JSONObject obj = new JSONObject();
			try {
				obj.put("msg", msg);
				obj.put("memberName", memberName);
				obj.put("memberPhone", memberPhone);
				obj.put("memberAddress", memberAddress);
				String msgJson = obj.toString();
//				System.out.println(msgJson);
				out.println(msgJson);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// 購物車step2 輸入收件人資料

		if ("inputReceiverInfo".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			String receiverName = req.getParameter("receiverName").trim();
			String receiverNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,10}$";
			if (receiverName == null || receiverName.trim().length() == 0) {
				errorMsgs.add("收件人姓名: 請勿空白");
			} else if (!receiverName.trim().matches(receiverNameReg)) {
				errorMsgs.add("收件人姓名: 只能是中、英文字母、數字和_，且長度必需在1到10之間");
			}

			String receiverPhone = req.getParameter("receiverPhone").trim();
			String receiverPhoneReg = "^[0][9]\\d{8}$";
			if (receiverPhone == null || receiverPhone.trim().length() == 0) {
				errorMsgs.add("收件人手機: 請勿空白");
			} else if (!receiverPhone.trim().matches(receiverPhoneReg)) {
				errorMsgs.add("收件人手機: 只能是正整數，長度為10，EX:0912345678");
			}

			String receiverAddress = req.getParameter("receiverAddress").trim();
			if (receiverAddress == null || receiverAddress.trim().length() == 0) {
				errorMsgs.add("收件人地址: 請勿空白");
			} else if (!(receiverPhone.trim().length() <= 80)) {
				errorMsgs.add("收件人地址: 長度不可超過80");
			}

			String creditCardNum = req.getParameter("creditCardNum").trim();
			String creditCardNumReg = "^\\d{16}$";
			if (creditCardNum == null || creditCardNum.trim().length() == 0) {
				errorMsgs.add("信用卡號: 請勿空白");
			} else if (!creditCardNum.trim().matches(creditCardNumReg)) {
				errorMsgs.add("信用卡號: 只能是正整數，長度為16");
			}

			String securityCode = req.getParameter("securityCode").trim();
			String securityCodeReg = "^\\d{3}$";
			if (securityCode == null || securityCode.trim().length() == 0) {
				errorMsgs.add("安全碼: 請勿空白");
			} else if (!securityCode.trim().matches(securityCodeReg)) {
				errorMsgs.add("安全碼: 只能是正整數，長度為3");
			}

			String effectiveDate = req.getParameter("effectiveDate").trim();
			String effectiveDateReg = "^[0-1][0-9]\\d{2}$";
			if (effectiveDate == null || effectiveDate.trim().length() == 0) {
				errorMsgs.add("有效日期: 請勿空白");
			} else if (!effectiveDate.trim().matches(effectiveDateReg)) {
				errorMsgs.add("有效日期: 只能是正整數，長度為4，格式為MMYY --> 2025年8月，EX:0825");
			}
			/*************************** 2.準備轉交(Send the Success view) *************/
			ReceiverVO receiverVO = new ReceiverVO();
			receiverVO.setReceiverName(receiverName);
			receiverVO.setReceiverPhone(receiverPhone);
			receiverVO.setReceiverAddress(receiverAddress);
			receiverVO.setCreditCardNum(creditCardNum);
			receiverVO.setSecurityCode(securityCode);
			receiverVO.setEffectiveDate(effectiveDate);

			req.setAttribute("receiverVO", receiverVO);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {

				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/mall/shoppingCart02.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			String url = "/front_end/mall/shoppingCart03.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}

		// step3tostep2
		if ("step3tostep2".equals(action)) {

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			/*************************** 2.準備轉交(Send the Success view) *************/

			ReceiverVO receiverVO = new ReceiverVO();
			receiverVO.setReceiverName(req.getParameter("receiverName"));
			receiverVO.setReceiverPhone(req.getParameter("receiverPhone"));
			receiverVO.setReceiverAddress(req.getParameter("receiverAddress"));
			receiverVO.setCreditCardNum(req.getParameter("creditCardNum"));
			receiverVO.setSecurityCode(req.getParameter("securityCode"));
			receiverVO.setEffectiveDate(req.getParameter("effectiveDate"));

			req.setAttribute("receiverVO", receiverVO);

			String url = "/front_end/mall/shoppingCart02.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}

		// 購物車結帳
		if ("checkout".equals(action)) {
			MailService mailSvc = new MailService();
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			String receiverName = req.getParameter("receiverName");
			String receiverPhone = req.getParameter("receiverPhone");
			String receiverAddress = req.getParameter("receiverAddress");
			String creditCardNum = req.getParameter("creditCardNum");
			String securityCode = req.getParameter("securityCode");
			String effectiveDate = req.getParameter("effectiveDate");

			// 檢查庫存量及購買流程時廠商是否更動價格
			ProductService productSvc = new ProductService();
			for (int i = 0; i < buyList.size(); i++) {
				CartVO cartVO = buyList.get(i);
				ProductVO productVO = productSvc.getOneProduct(cartVO.getProductId());
				// 檢查庫存量
				Integer productInventory = productVO.getProductInventory();
				Integer productPurchaseQuantity = cartVO.getProductPurchaseQuantity();
				if ((productInventory.intValue() - productPurchaseQuantity.intValue()) < 0) {
					cartVO.setProductPurchaseQuantity(productInventory);
					if (cartVO.getProductPurchaseQuantity().intValue() == 0) {
						buyList.remove(i);
						i--;
					} else {
						buyList.set(i, cartVO);
					}
					errorMsgs.add(cartVO.getProductName() + " 的庫存量為" + productInventory + "，請重新確認數量");
				}
				// 檢查購買流程時廠商是否更動價格
				Integer realPrice = productVO.getProductPrice();
				Integer cartPrice = cartVO.getProductPrice();
				if (cartPrice.intValue() != realPrice.intValue()) {
					cartVO.setProductPrice(realPrice);
					buyList.set(i, cartVO);
					errorMsgs.add(cartVO.getProductName() + " : 廠商在您 shopping 時更動了價格，請重新確認");
				}
				// 檢查購買流程時廠商是否下價商品
				if (productVO.getProductStatus().intValue() == 0) {
					buyList.remove(i);
					i--;
					errorMsgs.add(cartVO.getProductName() + " : 商品已下架，請重新確認");
				}
			}

			session.setAttribute("buyList", buyList);
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {

				ReceiverVO receiverVO = new ReceiverVO();
				receiverVO.setReceiverName(receiverName);
				receiverVO.setReceiverPhone(receiverPhone);
				receiverVO.setReceiverAddress(receiverAddress);
				receiverVO.setCreditCardNum(creditCardNum);
				receiverVO.setSecurityCode(securityCode);
				receiverVO.setEffectiveDate(effectiveDate);

				req.setAttribute("receiverVO", receiverVO);

				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/mall/shoppingCart01.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始新增資料 ***************************************/

			MallOrderService mallOrderSvc = new MallOrderService();
			// 存放增加的訂單的ID
			List<Integer> mallOrderIdList = mallOrderSvc.checkout(memberVO.getMemberId(), creditCardNum, receiverName,
					receiverPhone, receiverAddress, buyList);
			// 測試:印出增加的mallOrderId
			for (Integer mallOrderId : mallOrderIdList) {
				System.out.println(mallOrderId);
			}

			/*************************** 3.準備轉交(Send the Success view) *************/

			if (mallOrderIdList == null || mallOrderIdList.size() == 0) {
				errorMsgs.add("訂單成立失敗，請重新下訂或聯繫客服");
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/mall/shoppingCart01.jsp");
				failureView.forward(req, res);
				return;
			} else {
				session.removeAttribute("buyList");
				req.setAttribute("mallOrderIdList", mallOrderIdList);
				// 寄送email給會員
				mailSvc.sendMailByMallOrder(memberVO.getMemberEmail(), "Camping Paradise-商城訂單成立", mallOrderIdList);
				
				String url = "/front_end/mall/shoppingCart04.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
				return;
			}

		}

		if ("showOrderDetail".equals(action)) {
			ProductService productSvc = new ProductService();
			MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			if (req.getParameter("mallOrderId") == null || req.getParameter("mallOrderId").trim().isEmpty()) {
				out.println("[]");
				return;
			} else {
				Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId").trim());
				List<MallOrderDetailVO> MallOrderDetailVOList = mallOrderDetailSvc.getBymallOrderId(mallOrderId);
				JSONArray jsonArray = new JSONArray();
				for (MallOrderDetailVO mallOrderDetailVO : MallOrderDetailVOList) {
					JSONObject obj = new JSONObject(mallOrderDetailVO);
					try {
						obj.put("productName",
								productSvc.getOneProduct(mallOrderDetailVO.getProductId()).getProductName());
						jsonArray.put(obj);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				out.println(jsonArray.toString());
			}

		}
		
		if ("getCartNum".equals(action)) {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			/*************************** 2.開始查詢資料 ***************************************/
			/*************************** 3.準備轉交(Send the Success view) *************/
			int cartNum = 0;
			
			if (buyList == null || buyList.size() == 0) {
				try {
					JSONObject obj = new JSONObject();
					obj.put("cartNum", cartNum);
					out.println(obj.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return;
			}
			
			for (CartVO cartVO : buyList) {
				cartNum += cartVO.getProductPurchaseQuantity();
			}
			
			try {
				JSONObject obj = new JSONObject();
				obj.put("cartNum", cartNum);
				out.println(obj.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return;
			
		}

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	private List<CartVO> getBuyList(HttpServletRequest req) {

		List<CartVO> buyList = new ArrayList<CartVO>();

		String[] companyIdArray = req.getParameterValues("companyId");
		String[] productIdArray = req.getParameterValues("productId");
		String[] productTypeIdArray = req.getParameterValues("productTypeId");
		String[] productNameArray = req.getParameterValues("productName");
		String[] productPriceArray = req.getParameterValues("productPrice");
		String[] productPurchaseQuantityArray = req.getParameterValues("productPurchaseQuantity");

		for (int i = 0; i < companyIdArray.length; i++) {

			CartVO cartVO = new CartVO();

			Integer companyId = Integer.parseInt(companyIdArray[i]);
			Integer productId = Integer.parseInt(productIdArray[i]);
			Integer productTypeId = Integer.parseInt(productTypeIdArray[i]);
			String productName = productNameArray[i];
			Integer productPrice = Integer.parseInt(productPriceArray[i]);
			Integer productPurchaseQuantity = Integer.parseInt(productPurchaseQuantityArray[i]);

			cartVO.setCompanyId(companyId);
			cartVO.setProductId(productId);
			cartVO.setProductTypeId(productTypeId);
			cartVO.setProductName(productName);
			cartVO.setProductPrice(productPrice);
			cartVO.setProductPurchaseQuantity(productPurchaseQuantity);

			buyList.add(cartVO);
		}

		return buyList;
	}

}
