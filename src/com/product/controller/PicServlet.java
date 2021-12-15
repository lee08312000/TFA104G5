package com.product.controller;

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

import com.product.model.ProductService;
import com.product.model.ProductVO;

// 以下為JSP中的使用方法: productId=商品編號&pic=第幾張圖片
// <img src="<%=request.getContextPath()%>/product/PicServlet?productId=1&pic=1">

@WebServlet("/product/PicServlet")
public class PicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProductService productSvc;
       

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	doGet(req, res);
    }
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out =  res.getOutputStream();
		Integer productId = Integer.parseInt(req.getParameter("productId"));
		int picId = Integer.parseInt(req.getParameter("pic"));
		
		productSvc = new ProductService();
		ProductVO productVO = productSvc.getOneProduct(productId);
		byte[] noDataPic = getPictureFromLocal(getServletContext().getRealPath("NoData/none3.jpg"));
		
		if (productVO == null) {
			out.write(noDataPic);	
			return;
		}
		
		byte[] pic1 = productVO.getProductPic1();
		byte[] pic2 = productVO.getProductPic2();
		byte[] pic3 = productVO.getProductPic3();
		
		switch (picId) {
		case 1:
			out.write((pic1 != null) ? pic1 : noDataPic);
			break;
		case 2:
			out.write((pic2 != null) ? pic2 : noDataPic);
			break;
		case 3:
			out.write((pic3 != null) ? pic3 : noDataPic);
			break;

		default:
			out.write(noDataPic);
			break;
		}
		
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
