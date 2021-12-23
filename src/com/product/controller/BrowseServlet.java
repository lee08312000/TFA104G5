package com.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SimpleFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.company.model.CompanyDAO;
import com.company.model.CompanyDAOImpl;
import com.company.model.CompanyVO;
import com.favoriteProdoct.model.FavoriteProdoctService;
import com.favoriteProdoct.model.FavoriteProdoctVO;
import com.member.model.MemberVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.productType.model.ProductTypeService;
import com.productType.model.ProductTypeVO;

@WebServlet("/product/BrowseServlet")
public class BrowseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BrowseServlet() {
		super();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		ProductService productSvc = new ProductService();
		FavoriteProdoctService favoriteProdoctSvc = new FavoriteProdoctService();

		HttpSession session = req.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		/****************** 假會員 *******************/
//		MemberVO memberVO = new MemberVO();
//		memberVO.setMemberId(1);
		/****************** 假會員 *******************/
		String action = req.getParameter("action");

		// 首頁熱門商品3 及最新上架商品3 區塊
		if ("getMallProducts3".equals(action)) {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer orderBy = Integer.parseInt(req.getParameter("orderBy"));
			/*************************** 2.開始查詢資料 *****************************************/
			List<ProductVO> hotProducts = productSvc.getProducts10Random3(orderBy);
			/*************************** 3.準備轉交(Send the Success view) *************/
			JSONObject jsonObj = null;
			JSONArray jsonArray = new JSONArray();
			String jsonArrayStr = "";

			try {
				for (ProductVO productVO : hotProducts) {
					jsonObj = new JSONObject();
					jsonObj.put("productId", productVO.getProductId());
					jsonObj.put("productTypeId", productVO.getProductTypeId());
					jsonObj.put("productName", productVO.getProductName());
					jsonObj.put("productPrice", productVO.getProductPrice());
					jsonObj.put("productCommentstarAvg",
							productVO.getProductCommentedAllnum().intValue() == 0 ? 0
									: Math.round((float) productVO.getProductCommentAllstar()
											/ (float) productVO.getProductCommentedAllnum()));

					if (memberVO != null) {
						FavoriteProdoctVO favoriteProdoctVO = favoriteProdoctSvc
								.getOneFavoriteProdoctByMemberIdAndProductId(memberVO.getMemberId(),
										productVO.getProductId());
						if (favoriteProdoctVO != null) {
							jsonObj.put("heart", 1);
						} else {
							jsonObj.put("heart", 0);
						}
					} else {
						jsonObj.put("heart", 0);
					}

					jsonArray.put(jsonObj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			jsonArrayStr = jsonArray.toString();

			out.println(jsonArrayStr);
//			System.out.println(jsonArrayStr);
		}

		// 加入我的最愛商品
		if ("addFavoriteProduct".equals(action)) {
			String msg = "denied";
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer productId = Integer.parseInt(req.getParameter("productId"));
			/*************************** 2.開始新增資料 *****************************************/
			if (memberVO != null) {
				if (favoriteProdoctSvc.getOneFavoriteProdoctByMemberIdAndProductId(memberVO.getMemberId(),
						productId) == null) {
					favoriteProdoctSvc.addFavoriteProdoct(memberVO.getMemberId(), productId);
					msg = "success";
				} else {
					msg = "isExisted";
				}
			} else {
				msg = "noLogin";
			}

			/*************************** 3.準備轉交(Send the Success view) *************/
			JSONObject obj = new JSONObject();
			try {
				obj.put("msg", msg);
				String msgJson = obj.toString();
				out.println(msgJson);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		// 移除我的最愛商品
		if ("deleteFavoriteProduct".equals(action)) {
			String msg = "denied";
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer productId = Integer.parseInt(req.getParameter("productId"));
			/*************************** 2.開始新增資料 *****************************************/
			if (memberVO != null) {
				FavoriteProdoctVO favoriteProdoctVO = favoriteProdoctSvc
						.getOneFavoriteProdoctByMemberIdAndProductId(memberVO.getMemberId(), productId);
				if (favoriteProdoctVO == null) {
					msg = "isDeleted";
				} else {
					favoriteProdoctSvc.deleteFavoriteProdoct(favoriteProdoctVO.getFavoriteProductId());
					msg = "success";
				}
			} else {
				msg = "noLogin";
			}
			/*************************** 3.準備轉交(Send the Success view) *************/
			JSONObject obj = new JSONObject();
			try {
				obj.put("msg", msg);
				String msgJson = obj.toString();
				out.println(msgJson);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		// mall_product_list.html 得到商品分類
		if ("getProductType".equals(action)) {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			/*************************** 2.開始查詢資料 *****************************************/
			ProductTypeService productTypeSvc = new ProductTypeService();
			List<ProductTypeVO> productTypeVOList = productTypeSvc.getAllProductType();
			/*************************** 3.準備轉交(Send the Success view) *************/
			String jsonArrayStr = "";
			jsonArrayStr = new JSONArray(productTypeVOList).toString();
			out.println(jsonArrayStr);
		}

		// mall_product_list.html 得到廠商
		if ("getCompany".equals(action)) {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			/*************************** 2.開始查詢資料 *****************************************/
			CompanyDAO companyDAO = new CompanyDAOImpl();
			List<CompanyVO> companyVOList = companyDAO.getAll();
			JSONObject jsonObj = null;
			JSONArray jsonArray = new JSONArray();
			for (CompanyVO companyVO : companyVOList) {
				try {
					jsonObj = new JSONObject();
					jsonObj.put("companyId", companyVO.getCompanyId());
					jsonObj.put("companyName", companyVO.getCompanyName());
					jsonArray.put(jsonObj);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			/*************************** 3.準備轉交(Send the Success view) *************/
			String jsonArrayStr = "";
			jsonArrayStr = jsonArray.toString();
//			System.out.println(jsonArrayStr);
			out.println(jsonArrayStr);
		}

		// mall_product_list.html 得到商品
		if ("getMallProducts".equals(action)) {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer productTypeId = Integer.parseInt(req.getParameter("productTypeId"));
			Integer companyId = Integer.parseInt(req.getParameter("companyId"));
			Integer orderType = Integer.parseInt(req.getParameter("orderType"));
			Integer page = Integer.parseInt(req.getParameter("page"));
			/*************************** 2.開始查詢資料 *****************************************/
			List<ProductVO> productList = productSvc.getProducts(productTypeId, companyId, orderType, page);
			/*************************** 3.準備轉交(Send the Success view) *************/
			JSONObject jsonObj = null;
			JSONArray jsonArray = new JSONArray();
			String jsonArrayStr = "";

			try {
				for (ProductVO productVO : productList) {
					jsonObj = new JSONObject();
					jsonObj.put("productId", productVO.getProductId());
					jsonObj.put("productTypeId", productVO.getProductTypeId());
					jsonObj.put("productName", productVO.getProductName());
					jsonObj.put("productPrice", productVO.getProductPrice());
					jsonObj.put("productCommentstarAvg",
							productVO.getProductCommentedAllnum().intValue() == 0 ? 0
									: Math.round((float) productVO.getProductCommentAllstar()
											/ (float) productVO.getProductCommentedAllnum()));

					if (memberVO != null) {
						FavoriteProdoctVO favoriteProdoctVO = favoriteProdoctSvc
								.getOneFavoriteProdoctByMemberIdAndProductId(memberVO.getMemberId(),
										productVO.getProductId());
						if (favoriteProdoctVO != null) {
							jsonObj.put("heart", 1);
						} else {
							jsonObj.put("heart", 0);
						}
					} else {
						jsonObj.put("heart", 0);
					}

					jsonArray.put(jsonObj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			jsonArrayStr = jsonArray.toString();

			out.println(jsonArrayStr);
//			System.out.println(jsonArrayStr);
		}

		// mall_product_list.html 得到商品by Name
		if ("getMallProductsByName".equals(action)) {
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String productName = req.getParameter("productName");
			if (productName == null || productName.trim().length() == 0) {
				out.println("[]");
				return;
			}
			Integer companyId = Integer.parseInt(req.getParameter("companyId"));
			Integer orderType = Integer.parseInt(req.getParameter("orderType"));
			Integer page = Integer.parseInt(req.getParameter("page"));
			/*************************** 2.開始查詢資料 *****************************************/
			List<ProductVO> productList = productSvc.getProductsByName(productName, companyId, orderType, page);
			/*************************** 3.準備轉交(Send the Success view) *************/
			JSONObject jsonObj = null;
			JSONArray jsonArray = new JSONArray();
			String jsonArrayStr = "";

			try {
				for (ProductVO productVO : productList) {
					jsonObj = new JSONObject();
					jsonObj.put("productId", productVO.getProductId());
					jsonObj.put("productTypeId", productVO.getProductTypeId());
					jsonObj.put("productName", productVO.getProductName());
					jsonObj.put("productPrice", productVO.getProductPrice());
					jsonObj.put("productCommentstarAvg",
							productVO.getProductCommentedAllnum().intValue() == 0 ? 0
									: Math.round((float) productVO.getProductCommentAllstar()
											/ (float) productVO.getProductCommentedAllnum()));

					if (memberVO != null) {
						FavoriteProdoctVO favoriteProdoctVO = favoriteProdoctSvc
								.getOneFavoriteProdoctByMemberIdAndProductId(memberVO.getMemberId(),
										productVO.getProductId());
						if (favoriteProdoctVO != null) {
							jsonObj.put("heart", 1);
						} else {
							jsonObj.put("heart", 0);
						}
					} else {
						jsonObj.put("heart", 0);
					}

					jsonArray.put(jsonObj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			jsonArrayStr = jsonArray.toString();

			out.println(jsonArrayStr);
			System.out.println(jsonArrayStr);
		}

		// mall_product_detail.html 得到商品
		if ("getMallProductDetail".equals(action)) {
			ProductTypeService productTypeSvc = new ProductTypeService();
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer productId = Integer.parseInt(req.getParameter("productId"));
			/*************************** 2.開始查詢資料 *****************************************/
			ProductVO productVO = productSvc.getOneProduct(productId);
			/*************************** 3.準備轉交(Send the Success view) *************/
			if (productVO == null) {
				out.println("[]");
				return;
			}
			
			JSONObject jsonObj = null;
			String jsonObjStr = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try {
					jsonObj = new JSONObject();
					jsonObj.put("productId", productVO.getProductId());
					jsonObj.put("productTypeId", productVO.getProductTypeId());
					jsonObj.put("productTypeName", productTypeSvc.getOneProductType(productVO.getProductTypeId()).getProductTypeName());
					jsonObj.put("productBrand", productVO.getProductBrand());
					jsonObj.put("productName", productVO.getProductName());
					jsonObj.put("productDescription", productVO.getProductDescription());
					jsonObj.put("productLaunchedTime", sdf.format(productVO.getProductLaunchedTime()));
					jsonObj.put("productPrice", productVO.getProductPrice());
					jsonObj.put("productInventory", productVO.getProductInventory());
					jsonObj.put("shoppingInformation", productVO.getShoppingInformation());
					jsonObj.put("productCommentstarAvg",
							productVO.getProductCommentedAllnum().intValue() == 0 ? 0
									: Math.round((float) productVO.getProductCommentAllstar()
											/ (float) productVO.getProductCommentedAllnum()));
					if (memberVO != null) {
						FavoriteProdoctVO favoriteProdoctVO = favoriteProdoctSvc
								.getOneFavoriteProdoctByMemberIdAndProductId(memberVO.getMemberId(),
										productVO.getProductId());
						if (favoriteProdoctVO != null) {
							jsonObj.put("heart", 1);
						} else {
							jsonObj.put("heart", 0);
						}
					} else {
						jsonObj.put("heart", 0);
					}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			jsonObjStr = jsonObj.toString();

			out.println(jsonObjStr);
			System.out.println(jsonObjStr);
		}

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

}
