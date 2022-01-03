package com.product.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductService {
	private ProductDAO dao;

	public ProductService() {
		dao = new ProductDAOImpl();
	}

	public ProductVO addProduct(Integer companyId, Integer productTypeId, Integer productStatus, String productName,
			Integer productPrice, String productBrand, Integer productInventory, String productDescription,
			String shoppingInformation, byte[] productPic1, byte[] productPic2, byte[] productPic3) {

		ProductVO ProductVO = new ProductVO();

		ProductVO.setCompanyId(companyId);
		ProductVO.setProductTypeId(productTypeId);
		ProductVO.setProductStatus(productStatus);
		ProductVO.setProductName(productName);
		ProductVO.setProductPrice(productPrice);
		ProductVO.setProductBrand(productBrand);
		ProductVO.setProductInventory(productInventory);
		ProductVO.setProductDescription(productDescription);
		ProductVO.setShoppingInformation(shoppingInformation);
		ProductVO.setProductPic1(productPic1);
		ProductVO.setProductPic2(productPic2);
		ProductVO.setProductPic3(productPic3);
		dao.insert(ProductVO);

		return ProductVO;
	}

	public ProductVO updateProduct(Integer productId, Integer companyId, Integer productTypeId, Integer productStatus,
			String productName, Integer productPrice, String productBrand, Integer productInventory,
			String productDescription, String shoppingInformation, byte[] productPic1, byte[] productPic2,
			byte[] productPic3, Timestamp productLaunchedTime, Integer productCommentedAllnum,
			Integer productCommentAllstar, Integer productSellAllnum) {

		ProductVO ProductVO = new ProductVO();

		ProductVO.setProductId(productId);
		ProductVO.setCompanyId(companyId);
		ProductVO.setProductTypeId(productTypeId);
		ProductVO.setProductStatus(productStatus);
		ProductVO.setProductName(productName);
		ProductVO.setProductPrice(productPrice);
		ProductVO.setProductBrand(productBrand);
		ProductVO.setProductInventory(productInventory);
		ProductVO.setProductDescription(productDescription);
		ProductVO.setShoppingInformation(shoppingInformation);
		ProductVO.setProductPic1(productPic1);
		ProductVO.setProductPic2(productPic2);
		ProductVO.setProductPic3(productPic3);
		ProductVO.setProductLaunchedTime(productLaunchedTime);
		ProductVO.setProductCommentedAllnum(productCommentedAllnum);
		ProductVO.setProductCommentAllstar(productCommentAllstar);
		ProductVO.setProductSellAllnum(productSellAllnum);
		dao.update(ProductVO);

		return ProductVO;
	}

	public void deleteProduct(Integer productId) {
		dao.delete(productId);
	}

	public ProductVO getOneProduct(Integer productId) {
		return dao.findByPrimaryKey(productId);
	}

	public List<ProductVO> getAllProduct() {
		return dao.getAll();
	}

	// update by Lee
	public ProductVO updateProductInventoryAndProductSellAllnum(Integer productId, Integer productInventory,
			Integer productSellAllnum, Connection con) {
		ProductVO productVO = dao.findByPrimaryKey(productId);
		productVO.setProductInventory(productInventory);
		productVO.setProductSellAllnum(productSellAllnum);
		dao.update(productVO, con);

		return productVO;
	}

	// update by Lee
	// 上架商品的前10中的隨機3個查詢
	public List<ProductVO> getProducts10Random3(Integer orderType) {
		List<ProductVO> productsLimit10 = dao.getProducts(0, 0, orderType, 0, 10);

		if (productsLimit10.size() <= 3) {
			return productsLimit10;
		}

		List<ProductVO> productsLimit10Random3 = new ArrayList<ProductVO>();
		int num2 = 10;
		if (productsLimit10.size() < 10) {
			num2 = productsLimit10.size();
		}

		Set<Integer> randomNum3 = new HashSet<Integer>();
		while (randomNum3.size() < 3) {
			randomNum3.add((int) (Math.random() * num2));
		}

		for (int random : randomNum3) {
			productsLimit10Random3.add(productsLimit10.get(random));
		}

		return productsLimit10Random3;

	}

	// 上架商品的分頁查詢，用於mall_products_list.html
	public List<ProductVO> getProducts(Integer productTypeId, Integer companyId, Integer orderType, Integer page) {
		if (page.intValue() == 0) {
			return dao.getProducts(productTypeId, companyId, orderType, 0, 0);
		}

		// 一頁要顯示的筆數
		int viewProducts = 6;
		// 略過的筆數
		int limitX = (page.intValue() - 1) * viewProducts;

		return dao.getProducts(productTypeId, companyId, orderType, limitX, viewProducts);
	}

	// 上架商品的分頁查詢By 商品名稱，用於mall_products_list.html
	public List<ProductVO> getProductsByName(String productName, Integer companyId, Integer orderType, Integer page) {
		if (page.intValue() == 0) {
			return dao.getProductsByName(productName, companyId, orderType, 0, 0);
		}

		// 一頁要顯示的筆數
		int viewProducts = 6;
		// 略過的筆數
		int limitX = (page.intValue() - 1) * viewProducts;

		return dao.getProductsByName(productName, companyId, orderType, limitX, viewProducts);
	}

	// update by ginny
	public ProductVO updateProductInformation(Integer productId, Integer companyId, Integer productTypeId,
			Integer productStatus, String productName, Integer productPrice, String productBrand,
			Integer productInventory, String productDescription, String shoppingInformation, byte[] productPic1,
			byte[] productPic2, byte[] productPic3) {

		ProductVO productVO = dao.findByPrimaryKey(productId);
		productVO.setProductId(productId);
		productVO.setCompanyId(companyId);
		productVO.setProductTypeId(productTypeId);
		productVO.setProductStatus(productStatus);
		productVO.setProductName(productName);
		productVO.setProductPrice(productPrice);
		productVO.setProductBrand(productBrand);
		productVO.setProductInventory(productInventory);
		productVO.setProductDescription(productDescription);
		productVO.setShoppingInformation(shoppingInformation);
		if (productPic1 != null) {
			productVO.setProductPic1(productPic1);
		}
		if (productPic2 != null) {
			productVO.setProductPic2(productPic2);
		}
		if (productPic3 != null) {
			productVO.setProductPic3(productPic3);
		}
		dao.update(productVO);

		return productVO;
	}
	// update by ginny 12/25
		public List<ProductVO> getProductsByCompany(Integer companyId) {
			List<ProductVO> productVoList= dao.getAll();
			List<ProductVO> productList = new ArrayList<ProductVO>();
			for (ProductVO product : productVoList) {
				if(companyId == product.getCompanyId()) {
					productList.add(product);
				}			
			}
			return productList;
		}
	// update by ginny	12/30
		public List<ProductVO> getProductsByProductType(Integer companyId,Integer productTypeId) {
			List<ProductVO> productVoList= dao.getAll();
			List<ProductVO> productList = new ArrayList<ProductVO>();
			List<ProductVO> productListByType = new ArrayList<ProductVO>();
			for (ProductVO product : productVoList) {
				if(companyId == product.getCompanyId()) {
					productList.add(product);
				}			
			}
			for (ProductVO product : productList) {
				if(productTypeId == product.getProductTypeId()) {
					productListByType.add(product);
				}			
			}
			
			return productListByType;
		}
		
		public List<ProductVO> getProductsByLikeName(Integer companyId,String productName) {
			return dao.getProductsByLikeName(companyId, productName);
		}
}
