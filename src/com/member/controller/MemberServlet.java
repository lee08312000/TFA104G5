package com.member.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.cart.model.CartRedisService;
import com.cart.model.CartVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;

@WebServlet("/member/MemberServlet")
@MultipartConfig
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MemberServlet() {
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
		// 防止非法登入 得到session
		// PrintWriter out = res.getWriter();

		String action = req.getParameter("action");
		System.out.println(action);
		if ("login".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// 登入畫面轉jsp才可以
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String account = null;
			String password = null;
			MemberService memberSvc = new MemberService();

			account = req.getParameter("account");
			System.out.println(account);
			password = req.getParameter("password");
			System.out.println(password);

			/*************************** 2.開始查詢資料 *****************************************/
			MemberVO memberVO = memberSvc.login(account, password);
			if (memberVO != null) {
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				session.setAttribute("memberVO", memberVO); // 成功登入的話
				session.setAttribute("memberId", memberVO.getMemberId());
				session.setAttribute("memberId", memberVO.getMemberName());
				System.out.println("登入成功");
				// 合併session 和 Redis的購買清單
				CartRedisService cartRedisSvc = new CartRedisService();
				List<CartVO> redisBuyList = cartRedisSvc.getBuyList(memberVO.getMemberId());
				List<CartVO> buyList = (List<CartVO>) session.getAttribute("buyList");
				
				// 若buyList有東西時
				if (buyList != null && buyList.size() != 0 ) {
					// 若redisBuyList不為空
					if (redisBuyList != null && redisBuyList.size() != 0) {
						// 合併開始
						for (int i = 0; i < buyList.size(); i++) {
							boolean match = false;
							CartVO cartVOb = buyList.get(i);
							for (int j = 0; j < redisBuyList.size(); j++) {
								CartVO cartVOr = redisBuyList.get(j);
								// Redis中已有相同商品時
								if (cartVOb.getProductId().intValue() == cartVOr.getProductId().intValue()) {
									match = true;
									if (cartVOr.getProductPurchaseQuantity().intValue() <= cartVOb.getProductPurchaseQuantity().intValue()) {
										redisBuyList.set(j, cartVOb);
									}
									
								}
							}
							
							if (!match) {
								redisBuyList.add(cartVOb);
							}
							
							
						}
						
					} else {
						// 若redisBuyList為空
						redisBuyList = buyList;
					}
					
					cartRedisSvc.setBuyList(memberVO.getMemberId(), redisBuyList);
					session.removeAttribute("buyList");
				}
				/////////////////////////導回原本頁面////////////////////

			       try {                                                        
			           String location = (String) session.getAttribute("location");
			           System.out.println(location);
			           if (location != null) {
			             session.removeAttribute("location");   
			             res.sendRedirect(location);   
			             return;
			           }
			         }catch (Exception ignored) { }
				/////////////////////////導回原本頁面////////////////////

				String url = "/front_end/member/jsp/member_main.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);

			} else {
				errorMsgs.add("<BODY>你的帳號或密碼出現錯誤，請重新輸入<BR></BODY>");
				String url = "/front_end/member/login/login.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url); // 失敗轉交 login.jsp
				failureView.forward(req, res);
				return; // 程式在此中斷
			}

		}

		if ("logout".equals(action)) {

			if (session != null) {

				session.removeAttribute("memberVO");

//				session.invalidate();

				System.out.println("登出成功");
				String url = "/TFA104G5/front_end/camp/camp_index.html";
//				.RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
//				successView.forward(req, res);
				 res.sendRedirect(url);   
	             return;

			}
		}

		if ("register".equals(action)) { // 來自register.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// 註冊畫面轉jsp才可以
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

			String name = req.getParameter("name");
			System.out.println(name);
			String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (name == null || name.trim().length() == 0) {
				errorMsgs.add("<BODY>會員姓名: 請勿空白<BR></BODY>");
			} else if (!name.trim().matches(nameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("<BODY>會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間<BR></BODY>");
			}

			String account = req.getParameter("account");
			String accountReg = "^[(a-zA-Z0-9_)]{8,15}$";
			System.out.println(account);
			if (account == null || account.trim().length() == 0) {
				errorMsgs.add("<BODY>帳號請勿空白<BR></BODY>");
			} else if (!account.trim().matches(accountReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("<BODY>帳號只能是英文字母、數字和_ , 且長度必需在8到15之間<BR></BODY>");
			}

			String password = req.getParameter("password");
			String passwordReg = "^[(a-zA-Z0-9_)]{8,15}$";
			System.out.println(password);
			if (password == null || password.trim().length() == 0) {
				errorMsgs.add("<BODY>密碼請勿空白<BR></BODY>");
			} else if (!password.trim().matches(passwordReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("<BODY>密碼只能是英文字母、數字和_ , 且長度必需在8到15之間<BR></BODY>");
			}

			String check = req.getParameter("check");
			System.out.println(check);
			if (check == null || check.trim().length() == 0) {
				errorMsgs.add("<BODY>確認密碼請勿空白<BR></BODY>");
			} else if (!check.equals(password)) {
				errorMsgs.add("<BODY>確認密碼與密碼不符，請重新確認密碼<BR></BODY>");
			}

			String address = req.getParameter("address");
			System.out.println(address);
			if (address == null || address.trim().length() == 0) {
				errorMsgs.add("<BODY>地址請勿空白<BR></BODY>");
			} else if (address.trim().length() > 80) {
				errorMsgs.add("<BODY>地址長度過長請重新填寫<BR></BODY>");
			}

			String email = req.getParameter("email");
			String emailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
			System.out.println(email);
			if (email == null || email.trim().length() == 0) {
				errorMsgs.add("<BODY>email請勿空白<BR></BODY>");
			} else if (!email.trim().matches(emailReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("<BODY>email格式有錯誤請再次確認<BR></BODY>");
			}

			String phone = req.getParameter("phone");
			String phoneReg = "0\\d{2,3}[-]?\\d{7,8}|0\\d{2,3}\\s?\\d{7,8}|13[0-9]\\d{8}|15[1089]\\d{8}";
			;
			System.out.println(phone);
			if (phone == null || phone.trim().length() == 0) {
				errorMsgs.add("<BODY>手機號碼請勿空白<BR></BODY>");
			} else if (!phone.trim().matches(phoneReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("<BODY>手機號碼格式有錯誤請再次確認<BR></BODY>");
			}

			MemberVO memberVO = new MemberVO();
			memberVO.setMemberAccountStatus(1);
			memberVO.setMemberName(name);
			memberVO.setMemberAccount(account);
			memberVO.setMemberPassword(password);
			memberVO.setMemberEmail(email);
			memberVO.setMemberAddress(address);
			memberVO.setMemberPhone(phone);
			memberVO.setMemberPic(null);

			/*************************** 2.開始新增資料 ***************************************/
			if (!errorMsgs.isEmpty()) {
				/*************************** 3.完成,準備轉交(Send the Success view) *************/
				errorMsgs.add("<BODY>註冊資料出現錯誤，請重新輸入<BR></BODY>");
				String url = "/front_end/member/register/register.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url); // 失敗轉交 login.jsp
				failureView.forward(req, res);
				return; // 程式在此中斷

			} else {
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				session.setAttribute("memberVO", memberVO); // 成功後將資料傳進session
				MemberService memberSvc = new MemberService();
				memberVO = memberSvc.addMember(1, name, account, password, email, address, phone, null);
				System.out.println("新增成功");
				String url = "/front_end/member/login/login.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);

			}

		}

		if ("reset_password_id_check".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// 登入畫面轉jsp才可以
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String account = null;
			String email = null;
			MemberService memberSvc = new MemberService();

			account = req.getParameter("account");
			System.out.println(account);
			email = req.getParameter("email");
			System.out.println(email);

			/*************************** 2.開始查詢資料 *****************************************/
			MemberVO memberVO = memberSvc.resetPasswordCheck(account, email);
			if (memberVO != null) {
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				session.setAttribute("memberVO", memberVO); // 成功登入的話
				System.out.println("轉頁面成功");
				String url = "/front_end/member/jsp/memeber_password_reset.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);

			} else {
				errorMsgs.add("<BODY>你的帳號或email出現錯誤，請重新輸入<BR></BODY>");
				String url = "/front_end/member/jsp/member_forgot_password.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url); // 失敗轉交 login.jsp
				failureView.forward(req, res);
				return; // 程式在此中斷
			}

		}

		if ("reset_password".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// 登入畫面轉jsp才可以
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			MemberService memberSvc = new MemberService();

			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			Integer id = memberVO.getMemberId(); // 會員Id
			Integer accountStatus = memberVO.getMemberAccountStatus(); // 會員狀態
			String name = memberVO.getMemberName(); // 會員姓名
			String account = memberVO.getMemberAccount(); // 會員帳號
			String password = null; // 會員密碼
			String check = null; // 會員密碼
			String address = memberVO.getMemberAddress(); // 會員地址
			String email = memberVO.getMemberEmail(); // 會員email
			String phone = memberVO.getMemberPhone(); // 會員電話
			byte[] pic = memberVO.getMemberPic(); // 會員照片
			System.out.println(password);
			System.out.println(password);

			password = req.getParameter("password");
			String passwordReg = "^[(a-zA-Z0-9_)]{8,15}$";
			System.out.println(password);
			if (password == null || password.trim().length() == 0) {
				errorMsgs.add("<BODY>密碼請勿空白<BR></BODY>");
			} else if (!password.trim().matches(passwordReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("<BODY>密碼只能是英文字母、數字和_ , 且長度必需在8到15之間<BR></BODY>");
			}

			check = req.getParameter("check");
			System.out.println(check);
			if (check == null || check.trim().length() == 0) {
				errorMsgs.add("<BODY>確認密碼請勿空白<BR></BODY>");
			} else if (!check.equals(password)) {
				errorMsgs.add("<BODY>確認密碼與密碼不符，請重新確認密碼<BR></BODY>");
			}

			memberVO.setMemberId(id);
			memberVO.setMemberAccountStatus(accountStatus);
			memberVO.setMemberName(name);
			memberVO.setMemberAccount(account);
			memberVO.setMemberPassword(password);
			memberVO.setMemberEmail(email);
			memberVO.setMemberAddress(address);
			memberVO.setMemberPhone(phone);
			memberVO.setMemberPic(pic);

			if (!errorMsgs.isEmpty()) {
				/*************************** 3.完成,準備轉交(Send the Success view) *************/
				errorMsgs.add("<BODY>註冊資料出現錯誤，請重新輸入<BR></BODY>");
				String url = "/front_end/member/jsp/memeber_password_reset.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url); // 失敗轉交 login.jsp
				failureView.forward(req, res);
				return; // 程式在此中斷

			} else {
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				memberVO = memberSvc.updateMember(id, accountStatus, name, account, password, email, address, phone,
						pic);
				session.setAttribute("memberVO", memberVO); // 成功後將資料傳進session
				System.out.println("更改成功");
				String url = "/front_end/member/login/login.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);

			}

		}

		if ("reset_info".equals(action)) { // 來自member_reset_info.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// 登入畫面轉jsp才可以
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			MemberService memberSvc = new MemberService();

			MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			Integer id = memberVO.getMemberId(); // 會員Id
			Integer accountStatus = memberVO.getMemberAccountStatus(); // 會員狀態
			String name = memberVO.getMemberName(); // 會員姓名
			String account = memberVO.getMemberAccount(); // 會員帳號
			String password = memberVO.getMemberPassword(); // 會員密碼 // 會員密碼
			String address = memberVO.getMemberAddress(); // 會員地址
			String email = memberVO.getMemberEmail(); // 會員email
			String phone = memberVO.getMemberPhone(); // 會員電話
			byte[] pic = memberVO.getMemberPic(); // 會員照片
//    		System.out.println(name);
//    		System.out.println(password);
//    		System.out.println(address);
//    		System.out.println(email);
//    		System.out.println(phone);

			name = req.getParameter("name");
			System.out.println(name);
			String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (name == null || name.trim().length() == 0) {
				errorMsgs.add("<BODY>會員姓名: 請勿空白<BR></BODY>");
			} else if (!name.trim().matches(nameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("<BODY>會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間<BR></BODY>");
			}

			password = req.getParameter("password");
			String passwordReg = "^[(a-zA-Z0-9_)]{8,15}$";
			System.out.println(password);
			if (password == null || password.trim().length() == 0) {
				errorMsgs.add("<BODY>密碼請勿空白<BR></BODY>");
			} else if (!password.trim().matches(passwordReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("<BODY>密碼只能是英文字母、數字和_ , 且長度必需在8到15之間<BR></BODY>");
			}

			String check = req.getParameter("check");
			System.out.println(check);
			if (check == null || check.trim().length() == 0) {
				errorMsgs.add("<BODY>確認密碼請勿空白<BR></BODY>");
			} else if (!check.equals(password)) {
				errorMsgs.add("<BODY>確認密碼與密碼不符，請重新確認密碼<BR></BODY>");
			}

			address = req.getParameter("address");
			System.out.println(address);
			if (address == null || address.trim().length() == 0) {
				errorMsgs.add("<BODY>地址請勿空白<BR></BODY>");
			} else if (address.trim().length() > 80) {
				errorMsgs.add("<BODY>地址長度過長請重新填寫<BR></BODY>");
			}

			email = req.getParameter("email");
			String emailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
			System.out.println(email);
			if (email == null || email.trim().length() == 0) {
				errorMsgs.add("<BODY>email請勿空白<BR></BODY>");
			} else if (!email.trim().matches(emailReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("<BODY>email格式有錯誤請再次確認<BR></BODY>");
			}

			phone = req.getParameter("phone");
			String phoneReg = "0\\d{2,3}[-]?\\d{7,8}|0\\d{2,3}\\s?\\d{7,8}|13[0-9]\\d{8}|15[1089]\\d{8}";
			;
			System.out.println(phone);
			if (phone == null || phone.trim().length() == 0) {
				errorMsgs.add("<BODY>手機號碼請勿空白<BR></BODY>");
			} else if (!phone.trim().matches(phoneReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("<BODY>手機號碼格式有錯誤請再次確認<BR></BODY>");
			}

			memberVO.setMemberId(id);
			memberVO.setMemberAccountStatus(accountStatus);
			memberVO.setMemberName(name);
			memberVO.setMemberAccount(account);
			memberVO.setMemberPassword(password);
			memberVO.setMemberEmail(email);
			memberVO.setMemberAddress(address);
			memberVO.setMemberPhone(phone);
			memberVO.setMemberPic(pic);

			/*************************** 2.開始新增資料 ***************************************/
			if (!errorMsgs.isEmpty()) {
				/*************************** 3.完成,準備轉交(Send the Success view) *************/
				errorMsgs.add("<BODY>註冊資料出現錯誤，請重新輸入<BR></BODY>");
				String url = "/front_end/member/jsp/member_reset_info.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url); // 失敗轉交 login.jsp
				failureView.forward(req, res);
				return; // 程式在此中斷

			} else {
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				session.setAttribute("memberVO", memberVO);
				memberVO = memberSvc.updateMember(id, accountStatus, name, account, password, email, address, phone,
						pic);
				System.out.println("更新資料成功");
				String url = "/front_end/member/jsp/member_main.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 l
				successView.forward(req, res);

			}

		}

		if("pic_upload".equals(action)) {
			System.out.println("COOL");
        	MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
    		MemberService memberSvc = new MemberService();
			byte[] memberPic = null;
			Part parts1 = req.getPart("member_pic_upload");
			
			if(parts1.getInputStream().available() != 0) {
				memberPic = getBytesFromPart(parts1);
			}
			
			memberSvc.updateMember(memberVO.getMemberId(), memberVO.getMemberAccountStatus(), memberVO.getMemberName(), memberVO.getMemberAccount(), memberVO.getMemberPassword(), memberVO.getMemberEmail(), memberVO.getMemberAddress(), memberVO.getMemberPhone(), memberPic);
			String url = "/front_end/member/jsp/member_main.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 l
			successView.forward(req, res);
			return;
        }
    	
        
    		
        }

		//處理圖片讀取
		private byte[] getBytesFromPart(Part part) {
			byte[] buf = null;
			InputStream in;
			try {
				in = part.getInputStream();
				buf = new byte[in.available()];
				in.read(buf);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			return buf;	
	}

}
