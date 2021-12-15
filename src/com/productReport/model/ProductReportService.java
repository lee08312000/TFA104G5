package com.productReport.model;

import java.util.List;

public class ProductReportService {
	
	private ProductReportDAO dao;
	
	public ProductReportService() {
		dao = new ProductReportDAOImpl();
	}

	public ProductReportVO addProductReport(Integer memberId, Integer productId, String reportReason) {
		
		ProductReportVO productReportVO = new ProductReportVO();
		
		productReportVO.setMemberId(memberId);
		productReportVO.setProductId(productId);
		productReportVO.setReportReason(reportReason);
		
		dao.insert(productReportVO);
		return productReportVO;
	}

	public ProductReportVO updateProductReport(Integer productReportId, Integer memberId, Integer productId,
			String reportReason, Integer reportStatus) {
		
		ProductReportVO productReportVO = new ProductReportVO();
		
		productReportVO.setMemberId(memberId);
		productReportVO.setProductId(productId);
		productReportVO.setReportReason(reportReason);
		productReportVO.setReportStatus(reportStatus);
		productReportVO.setProductReportId(productReportId);
		
		dao.update(productReportVO);
		return productReportVO;
	}

	public void deleteProductReport(Integer productReportId) {
		dao.delete(productReportId);
	}

	public ProductReportVO getOneProductReport(Integer productReportId) {
		return dao.findByPrimaryKey(productReportId);
	}

	public List<ProductReportVO> getAll() {
		return dao.getAll();
	}

	

}
