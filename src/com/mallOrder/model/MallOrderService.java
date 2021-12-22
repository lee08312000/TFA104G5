package com.mallOrder.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cart.model.CartVO;

public class MallOrderService {

	private MallOrderDAO dao;

	public MallOrderService() {
		dao = new MallOrderDAOImpl();
	}

	public MallOrderVO addMallOrder(Integer companyId, Integer memberId, Integer mailOrderTotalAmount,
			String creditCardNum, String receiverName, String receiverPhone, String receiverAddress) {

		MallOrderVO mallOrderVO = new MallOrderVO();

		mallOrderVO.setCompanyId(companyId);
		mallOrderVO.setMemberId(memberId);
		mallOrderVO.setMailOrderTotalAmount(mailOrderTotalAmount);
		mallOrderVO.setCreditCardNum(creditCardNum);
		mallOrderVO.setReceiverName(receiverName);
		mallOrderVO.setReceiverPhone(receiverPhone);
		mallOrderVO.setReceiverAddress(receiverAddress);
		dao.insert(mallOrderVO);

		return mallOrderVO;
	}

	public Integer addMallOrderWithMallOrderDetail(MallOrderVO mallOrderVO, List<CartVO> buyList) {
		Integer mallOrderId = dao.insert(mallOrderVO, buyList);
		return mallOrderId;
	}

//	public List<Integer> checkout(Integer memberId, String creditCardNum, String receiverName, String receiverPhone,
//			String receiverAddress, List<CartVO> buyList) {
//
//		// 存放增加的訂單編號
//		List<Integer> mallOrderIdList = new ArrayList<Integer>();
//		// 創造companySet
//		Set<Integer> companySet = new TreeSet<Integer>();
//		for (CartVO c : buyList) {
//			companySet.add(c.getCompanyId());
//		}
//
//		// 用set分別成立訂單
//		for (Integer companyId : companySet) {
//			// 存放增加的主鍵
//			Integer mallOrderId = null;
//
//			List<CartVO> newBuyList = new ArrayList<CartVO>();
//			Integer mailOrderTotalAmount = 0;
//
//			// 將此廠商的商品存到新的List
//			for (int i = 0; i < buyList.size(); i++) {
//
//				CartVO cartVO = buyList.get(i);
//				if (companyId.intValue() == cartVO.getCompanyId().intValue()) {
//					newBuyList.add(cartVO);
//				}
//			}
//
//			// 計算此訂單的總金額
//			for (int i = 0; i < newBuyList.size(); i++) {
//				CartVO cartVO = newBuyList.get(i);
//				mailOrderTotalAmount += cartVO.getProductPrice() * cartVO.getProductPurchaseQuantity();
//			}
//
//			// 新增訂單主檔、訂單明細、修改商品庫存量及銷量
//
//			MallOrderVO mallOrderVO = new MallOrderVO();
//
//			mallOrderVO.setCompanyId(companyId);
//			mallOrderVO.setMemberId(memberId);
//			mallOrderVO.setMailOrderTotalAmount(mailOrderTotalAmount);
//			mallOrderVO.setCreditCardNum(creditCardNum);
//			mallOrderVO.setReceiverName(receiverName);
//			mallOrderVO.setReceiverPhone(receiverPhone);
//			mallOrderVO.setReceiverAddress(receiverAddress);
//
//			mallOrderId = dao.insert(mallOrderVO, newBuyList);
//			mallOrderIdList.add(mallOrderId);
//		}
//
//		return mallOrderIdList;
//	}
	
	public List<Integer> checkout(Integer memberId, String creditCardNum, String receiverName, String receiverPhone,
			String receiverAddress, List<CartVO> buyList) {
		return dao.checkout(memberId, creditCardNum, receiverName, receiverPhone, receiverAddress, buyList);
	}

	public MallOrderVO updateMallOrder(Integer mallOrderId, Integer companyId, Integer memberId,
			Integer mallOrderStatus, Integer mallOrderDeliveryStatus, Integer mailOrderTotalAmount,
			String creditCardNum, String receiverName, String receiverPhone, String receiverAddress,
			Timestamp mallOrderCompletedTime) {

		MallOrderVO mallOrderVO = new MallOrderVO();

		mallOrderVO.setCompanyId(companyId);
		mallOrderVO.setMemberId(memberId);
		mallOrderVO.setMallOrderStatus(mallOrderStatus);
		mallOrderVO.setMallOrderDeliveryStatus(mallOrderDeliveryStatus);
		mallOrderVO.setMailOrderTotalAmount(mailOrderTotalAmount);
		mallOrderVO.setCreditCardNum(creditCardNum);
		mallOrderVO.setReceiverName(receiverName);
		mallOrderVO.setReceiverPhone(receiverPhone);
		mallOrderVO.setReceiverAddress(receiverAddress);
		mallOrderVO.setMallOrderCompletedTime(mallOrderCompletedTime);
		mallOrderVO.setMallOrderId(mallOrderId);
		dao.update(mallOrderVO);

		return mallOrderVO;
	}

	public void deleteMallOrder(Integer mallOrderId) {
		dao.delete(mallOrderId);
	}

	public MallOrderVO getOneMallOrder(Integer mallOrderId) {
		return dao.findByPrimaryKey(mallOrderId);
	}
	
	public List<MallOrderVO> getAll() {
		return dao.getAll();
	}

}
