package com.productType.model;

import java.util.List;

public interface ProductTypeDAO {

	public void insert(ProductTypeVO ProductTypeVO);
    public void update(ProductTypeVO ProductTypeVO);
    public void delete(Integer productTypeId);
    public ProductTypeVO findByPrimaryKey(Integer productTypeId);
    public List<ProductTypeVO> getAll();
}
