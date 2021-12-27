package com.mallOrderDetail.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MallOrderDetailService{
	
	private MallOrderDetailDAO dao;
	
	public MallOrderDetailService() {
		dao = new MallOrderDetailDAOImpl();
	}

	public MallOrderDetailVO addMallOrderDetail(Integer mallOrderId, Integer productId, Integer productPurchaseQuantity,
			Integer productPurchasePrice) {
		
		MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
		
		mallOrderDetailVO.setMallOrderId(mallOrderId);
		mallOrderDetailVO.setProductId(productId);
		mallOrderDetailVO.setProductPurchaseQuantity(productPurchaseQuantity);
		mallOrderDetailVO.setProductPurchasePrice(productPurchasePrice);
		dao.insert(mallOrderDetailVO);
		
		return mallOrderDetailVO;
	}
	
	public MallOrderDetailVO addMallOrderDetailFromOrder(Integer mallOrderId, Integer productId, Integer productPurchaseQuantity,
			Integer productPurchasePrice, Connection con) {
		
		MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
		
		mallOrderDetailVO.setMallOrderId(mallOrderId);
		mallOrderDetailVO.setProductId(productId);
		mallOrderDetailVO.setProductPurchaseQuantity(productPurchaseQuantity);
		mallOrderDetailVO.setProductPurchasePrice(productPurchasePrice);
		dao.insert(mallOrderDetailVO, con);
		
		return mallOrderDetailVO;
	}

	public MallOrderDetailVO updateMallOrderDetail(Integer mallOrderDetailId, Integer mallOrderId, Integer productId,
			Integer productPurchaseQuantity, Integer productPurchasePrice, Integer productCommentStar,
			String productComment, Timestamp productCommentTime) {
		
		MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
		
		mallOrderDetailVO.setMallOrderDetailId(mallOrderDetailId);
		mallOrderDetailVO.setMallOrderId(mallOrderId);
		mallOrderDetailVO.setProductId(productId);
		mallOrderDetailVO.setProductPurchaseQuantity(productPurchaseQuantity);
		mallOrderDetailVO.setProductPurchasePrice(productPurchasePrice);
		mallOrderDetailVO.setProductCommentStar(productCommentStar);
		mallOrderDetailVO.setProductComment(productComment);
		mallOrderDetailVO.setProductCommentTime(productCommentTime);
		dao.update(mallOrderDetailVO);
		
		return mallOrderDetailVO;
	}

	public void deleteMallOrderDetail(Integer mallOrderDetailId) {
		dao.delete(mallOrderDetailId);
	}

	public MallOrderDetailVO getOneMallOrderDetail(Integer mallOrderDetailId) {
		return dao.findByPrimaryKey(mallOrderDetailId);
	}
	
	public List<MallOrderDetailVO> getBymallOrderId(Integer mallOrderId) {
		return dao.findBymallOrderId(mallOrderId);
	}

	public List<MallOrderDetailVO> getAll() {
		return dao.getAll();
	}
	public List<MallOrderDetailVO> getProductComments(Integer productId, Integer orderType, Integer page) {
		if (page.intValue() == 0) {
			return dao.getProductComments(productId, orderType, 0, 0);
		}
		
		// 顯示筆數
		int viewNum = 6;
		// 略過筆數
		int limitX = (page.intValue() - 1) * viewNum;
		
		return dao.getProductComments(productId, orderType, limitX, viewNum);
	}
	
	//增加by ginny 12/25
		public List<MallOrderDetailVO> getAllProductComment(Integer productId) {
			List<MallOrderDetailVO> mallOrderDetailAll = dao.getAll();
			List<MallOrderDetailVO> mallOrderDetailList = new ArrayList<MallOrderDetailVO>();
			for (MallOrderDetailVO mallOrderDetail : mallOrderDetailAll) {
				if(productId == mallOrderDetail.getProductId()) {
					mallOrderDetailList.add(mallOrderDetail);
				}			
			}
			return mallOrderDetailList;
		}

}
