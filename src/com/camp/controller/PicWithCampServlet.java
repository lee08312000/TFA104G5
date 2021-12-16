package com.camp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camp.model.CampService;
import com.camp.model.CampVO;
import com.campArea.model.CampAreaService;
import com.campArea.model.CampAreaVO;

// 以下為JSP中的使用方法: 查詢營地:campid=營地編號&pic=第幾張圖片
//                      查詢營位:campid=營地編號&areaindex=第幾個營位圖片(不是areaid從開始)
// <img src="<%=request.getContextPath()%>/product/PicServlet?productId=1&pic=1">

@WebServlet("/PicWithCampServlet")
public class PicWithCampServlet extends HttpServlet {

	private CampService campSvc;
	private CampAreaService campareaSvc;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		String campid = req.getParameter("campid");
		String picid = req.getParameter("pic");
		String areaindex = req.getParameter("areaindex");

		Integer campId, picId, areaIndex;
		CampVO campVO = null;
		byte[] noDataPic = null;

		try {
			campId = Integer.parseInt(campid);
			campSvc = new CampService();
			campareaSvc = new CampAreaService();
			campVO = campSvc.findCampByCampId(campId);
			List<CampAreaVO> campareaVOlist = campareaSvc.findCampAreaByCampId(campId);
			noDataPic = getPictureFromLocal(getServletContext().getRealPath("NoData/none3.jpg"));

			if (campVO == null) {
				out.write(noDataPic);
				return;
			}

			if (picid != null) {
				picId = Integer.parseInt(picid);
//////////////////////////////////////營地照片/////////////////////////////////
				byte[] pic1 = campVO.getCampPic1();
				byte[] pic2 = campVO.getCampPic2();
				byte[] pic3 = campVO.getCampPic3();
				byte[] pic4 = campVO.getCampPic4();
				byte[] pic5 = campVO.getCampPic5();

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
				case 4:
					out.write((pic4 != null) ? pic4 : noDataPic);
					break;
				case 5:
					out.write((pic5 != null) ? pic5 : noDataPic);
					break;

				default:
					out.write(noDataPic);
					break;
				}
				return;
			}

//////////////////////////////////////營位照片/////////////////////////////////

			if (campareaVOlist == null || campareaVOlist.size() == 0) {
				out.write(noDataPic);
				return;
			}

			List<byte[]> picwithArray = new ArrayList<byte[]>();

			for (CampAreaVO obj : campareaVOlist) {
				picwithArray.add(obj.getCampAreaPic());

			}

			if (areaindex != null) {
				areaIndex = Integer.parseInt(areaindex);
				
				out.write(picwithArray.get(areaIndex - 1));
			}

		} catch (Exception e) {

			out.write(noDataPic);

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
