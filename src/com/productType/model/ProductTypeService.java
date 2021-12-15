package com.productType.model;

import java.util.List;

public class ProductTypeService {
	private ProductTypeDAO dao;

	public ProductTypeService() {
		dao = new ProductTypeDAOImpl();
	}
	
	public ProductTypeVO addProductType(String productTypeName) {

		ProductTypeVO ProductTypeVO = new ProductTypeVO();

		ProductTypeVO.setProductTypeName(productTypeName);
		
		dao.insert(ProductTypeVO);

		return ProductTypeVO;
	}

	public ProductTypeVO updateProductType(Integer productTypeId, String productTypeName) {

		ProductTypeVO ProductTypeVO = new ProductTypeVO();

		ProductTypeVO.setProductTypeId(productTypeId);
		ProductTypeVO.setProductTypeName(productTypeName);
		dao.update(ProductTypeVO);
		
		return ProductTypeVO;
	}

	public void deleteProductType(Integer productTypeId) {
		dao.delete(productTypeId);
	}

	public ProductTypeVO getOneProductType(Integer productTypeId) {
		return dao.findByPrimaryKey(productTypeId);
	}

	public List<ProductTypeVO> getAllProductType() {
		return dao.getAll();
	}
}
