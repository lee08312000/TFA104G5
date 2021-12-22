package com.product.model;

import java.sql.Connection;
import java.util.List;

public interface ProductDAO {
	public void insert(ProductVO ProductVO);
	public void update(ProductVO ProductVO);
	public void delete(Integer productId);
	public ProductVO findByPrimaryKey(Integer productId);
	public List<ProductVO> getAll();
	// update by Lee
	// 查詢上架商品條件參數為(商品類別流水號, 廠商編號, 排序種類, limitX, limitY)
	// 商品類別流水號 0 為全部商品
	// 廠商編號 0 為全部廠商
	// 排序種類 0.不排序 1.最熱門 2.最新上架3.最高評價4.價格低到高5.價格高到低
	// limit x
	// limit y，x==0 && y==0 不limit
	public List<ProductVO> getProducts(Integer productTypeId, Integer companyId, Integer orderType, Integer limitX,
			Integer limitY);
	
	public List<ProductVO> getProductsByName(String productName, Integer companyId, Integer orderType, Integer limitX,
			Integer limitY);

	public void update(ProductVO ProductVO, Connection con);
}
