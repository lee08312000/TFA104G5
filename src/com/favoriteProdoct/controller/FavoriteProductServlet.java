package com.favoriteProdoct.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.favoriteProdoct.model.FavoriteProdoctService; // c + shift + 0
import com.favoriteProdoct.model.FavoriteProdoctVO;


/**
 * Servlet implementation class FavoriteProductServlet
 */
@WebServlet("/favoriteProduct/FavoriteProductServlet")
public class FavoriteProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FavoriteProductServlet() {
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

		String action = req.getParameter("action");
		FavoriteProdoctService favoriteProdoctSvc = new FavoriteProdoctService();
		
		if("delete".equals(action)) {
			Integer favoriteProductId = Integer.parseInt(req.getParameter("favoriteProductId"));
			FavoriteProdoctVO favoriteProdoctVO = favoriteProdoctSvc.getOneFavoriteProdoct(favoriteProductId);
			
			if(favoriteProdoctVO != null) {
				favoriteProdoctSvc.deleteFavoriteProdoct(favoriteProductId);
			} 
			
			String url = "/front_end/member/jsp/member_favorite_product.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 
			successView.forward(req, res);
			return;
		}

	}

}
