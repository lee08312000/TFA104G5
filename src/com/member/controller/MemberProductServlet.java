package com.member.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mallOrder.model.MallOrderDAOImpl;
import com.mallOrder.model.MallOrderService;
import com.mallOrder.model.MallOrderVO;
import com.mallOrderDetail.model.MallOrderDetailDAOImpl;
import com.mallOrderDetail.model.MallOrderDetailService;
import com.mallOrderDetail.model.MallOrderDetailVO;
import com.product.model.ProductDAO;
import com.product.model.ProductDAOImpl;
import com.product.model.ProductService;
import com.product.model.ProductVO;


@WebServlet("/Member/MemberProductServlet")
@MultipartConfig
public class MemberProductServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getMallOrderDetail".equals(action)) {
			try {
				/***************************1.接收請求參數****************************************/
				Integer mallOrderId = new Integer(req.getParameter("mallOrderId"));
				
				/***************************2.開始查詢資料****************************************/
				MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();
				List<MallOrderDetailVO> mallOrderDetailList = mallOrderDetailSvc.getBymallOrderId(mallOrderId);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("mallOrderDetailList", mallOrderDetailList);         // 資料庫取出的empVO物件,存入req
				String url = "/front_end/member/jsp/member_product_order_detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {				
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/member/jsp/member_product_order_list.jsp");
				failureView.forward(req, res);
			}	
			
		}
		
		//確認訂單(按鈕)
				if ("updateMallOrderAllStatus".equals(action)) {
					try {
						
						/***************************1.修改訂單狀態(Send the Success view)***********/
						Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
						
						MallOrderService mallOrderSvc = new MallOrderService();
						MallOrderVO mallOrderVO = mallOrderSvc.getOneMallOrder(mallOrderId);
						mallOrderVO.setMallOrderStatus(2);
						mallOrderVO.setMallOrderDeliveryStatus(2);
						MallOrderDAOImpl mallOrderDao = new MallOrderDAOImpl();
						mallOrderDao.update(mallOrderVO);
						
						MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();
						List<MallOrderDetailVO> mallOrderDetailList = mallOrderDetailSvc.getBymallOrderId(mallOrderId);
						/***************************2.修改狀態完成,準備轉交(Send the Success view)***********/
						req.setAttribute("mallOrderDetailList", mallOrderDetailList); // 資料庫update成功後,重新抓取訂單明細
						String url = "/front_end/member/jsp/member_product_order_detail.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
						successView.forward(req, res);
										
					}catch (Exception e) {
						e.printStackTrace();
					}					
				}
				
				//確認訂單(掃碼)
				if ("updateMallOrderAllStatusQrcode".equals(action)) {
					try {
						
						/***************************1.修改訂單狀態(Send the Success view)***********/
						Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
						
						MallOrderService mallOrderSvc = new MallOrderService();
						MallOrderVO mallOrderVO = mallOrderSvc.getOneMallOrder(mallOrderId);
						mallOrderVO.setMallOrderStatus(2);
						mallOrderVO.setMallOrderDeliveryStatus(2);
						MallOrderDAOImpl mallOrderDao = new MallOrderDAOImpl();
						mallOrderDao.update(mallOrderVO);
						res.getWriter().println("<h1 style=\"text-align:center; line-height:center\">你已完成取貨動作!!</h1>");
						
						
//						MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();
//						List<MallOrderDetailVO> mallOrderDetailList = mallOrderDetailSvc.getBymallOrderId(mallOrderId);
						/***************************2.修改狀態完成,準備轉交(Send the Success view)***********/
//						req.setAttribute("mallOrderDetailList", mallOrderDetailList); // 資料庫update成功後,重新抓取訂單明細
//						String url = "/front_end/member/jsp/member_product_order_detail.jsp";
//						RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
//						successView.forward(req, res);
										
					}catch (Exception e) {
						e.printStackTrace();
					}					
				}
				
				//訂單評論送出
						if ("updateComment".equals(action)) {
							try {
								
								/***************************1.修改訂單狀態(Send the Success view)***********/
								Integer mallOrderId = Integer.parseInt(req.getParameter("mallOrderId"));
								Integer mallOrderDetailId = Integer.parseInt(req.getParameter("mallOrderDetailId"));
								Integer starNum = Integer.parseInt(req.getParameter("starNum"));						
								String commentText = req.getParameter("commentText").trim();
								
								MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();
								MallOrderDetailVO mallOrderDetailVO = mallOrderDetailSvc.getOneMallOrderDetail(mallOrderDetailId);
								mallOrderDetailVO.setProductCommentStar(starNum);
								mallOrderDetailVO.setProductComment(commentText);
								mallOrderDetailVO.setProductCommentTime(new Timestamp(System.currentTimeMillis()));
								MallOrderDetailDAOImpl mallOrderDetailDao = new MallOrderDetailDAOImpl();
								mallOrderDetailDao.update(mallOrderDetailVO);
								
								// 更新商品的評價總人數及總評價
								ProductService productSvc = new ProductService();
								ProductVO productVO = productSvc.getOneProduct(mallOrderDetailVO.getProductId());
								productVO.setProductCommentedAllnum(productVO.getProductCommentedAllnum() + 1);
								productVO.setProductCommentAllstar(productVO.getProductCommentAllstar() + starNum);
								ProductDAO productDAO = new ProductDAOImpl();
								productDAO.update(productVO);
								List<MallOrderDetailVO> mallOrderDetailList = mallOrderDetailSvc.getBymallOrderId(mallOrderId);
								/***************************2.修改狀態完成,準備轉交(Send the Success view)***********/
								req.setAttribute("mallOrderDetailList", mallOrderDetailList); // 資料庫update成功後,重新抓取訂單明細
								String url = "/front_end/member/jsp/member_product_order_detail.jsp";
								RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
								successView.forward(req, res);
												
							}catch (Exception e) {
								e.printStackTrace();
							}					
						}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
