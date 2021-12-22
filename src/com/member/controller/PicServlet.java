package com.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;

// 以下為JSP中的使用方法: productId=商品編號&pic=第幾張圖片
// <img src="<%=request.getContextPath()%>/member/PicServlet?memberId=1">

@WebServlet("/member/PicServlet")
public class PicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MemberService memberSvc;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		// 輸出二位元資料
		ServletOutputStream out = res.getOutputStream();
		// 東西本來是String用下面這一行變成整數Integer
		Integer memberId = Integer.parseInt(req.getParameter("memberId"));

		memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.getOneMember(memberId);
		byte[] noDataPic = getPictureFromLocal(getServletContext().getRealPath("NoData/none3.jpg"));

		if (memberVO == null) {
			out.write(noDataPic);
			return;
		}

		byte[] pic = memberVO.getMemberPic();

		out.write((pic != null) ? pic : noDataPic);
	}

	public byte[] getPictureFromLocal(String path) {
		FileInputStream fis = null;
		byte[] b = null;
		try {
			fis = new FileInputStream(new File(path));
			b = new byte[fis.available()];
			fis.read(b);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return b;
	}

}
