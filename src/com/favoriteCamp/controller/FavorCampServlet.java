package com.favoriteCamp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.favoriteCamp.model.FavoriteCampService;
import com.favoriteCamp.model.FavoriteCampVO;

@WebServlet("/FavorCampServlet")
public class FavorCampServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		System.out.println(action);

		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "300");
		res.setHeader("Access-Control-Allow-Headers", "content-type, x-requested-with");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = res.getWriter();

		if ("addfav".equals(action)) {
			String memberid = req.getParameter("memberid");
			String campid = req.getParameter("campid");

			String statusText = null;
			// 檢查有無登入
			if (memberid == null || memberid.length() == 0) {
				statusText = "請先登入會員，謝謝!";
				out.print(statusText);

				return;
			}

			// 檢查是否已加入過，有的話進行移除，沒有的話新增成功

			FavoriteCampService favoritecampSvc = new FavoriteCampService();
			List<FavoriteCampVO> list = favoritecampSvc.findBymemberId(Integer.parseInt(memberid));
			try {
				if (list != null && list.size() > 0) {

					for (FavoriteCampVO faobj : list) {

						if (faobj.getCampId()== Integer.parseInt(campid)) {

							statusText = "已經加入過了，移除我的最愛成功";
							favoritecampSvc.deleteFavoritecamp(faobj.getFavoriteCampId());
							out.print(statusText);
							return;
						}

					}
				}
				if (list != null && list.size() == 0) {
					statusText = "新增我的最愛成功";
					favoritecampSvc.addFavoriteCamp(Integer.parseInt(memberid), Integer.parseInt(campid));
					out.print(statusText);
					return;
				}

			} catch (Exception e) {
				e.printStackTrace();
				statusText = "新增失敗，請重新登入";
				out.print(statusText);
			}
		}

	}
}
