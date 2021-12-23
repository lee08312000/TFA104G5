package com.favoriteCamp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.favoriteCamp.model.FavoriteCampService;
import com.favoriteCamp.model.FavoriteCampVO;

/**
 * Servlet implementation class FavoriteCampServlet
 */
@WebServlet("/favoriteCamp/FavoriteCampServlet")
public class FavoriteCampServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoriteCampServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		
		FavoriteCampService favoriteCampSvc = new FavoriteCampService();
		
		if("delete".equals(action)) {
			Integer favoriteCampId = Integer.parseInt(req.getParameter("favoriteCampId"));
			FavoriteCampVO favoriteCampVO = favoriteCampSvc.getFavoritecamp(favoriteCampId);
			
			if(favoriteCampVO != null) {
				favoriteCampSvc.deleteFavoritecamp(favoriteCampId);
			} 
			
			String url = "/front_end/member/jsp/member_favorite_camp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 
			successView.forward(req, res);
			return;
		}
	}
}
