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
	
	//新增by ginny廠商條件查詢訂單 12/20
		public List<MallOrderVO> getMallOrderByCompany(Integer companyId) {
			List<MallOrderVO> mallOrderAll= dao.getAll();
			List<MallOrderVO> mallOrderList = new ArrayList<MallOrderVO>();
			for (MallOrderVO mallOrder : mallOrderAll) {
				if(companyId == mallOrder.getCompanyId()) {
					mallOrderList.add(mallOrder);
				}			
			}
			return mallOrderList;
		}
		
		public List<MallOrderVO> getMallOrdersByOrderStatus(Integer companyId,Integer mallOrderStatus) {
			List<MallOrderVO> mallOrderList = getMallOrderByCompany(companyId);
			List<MallOrderVO> mallOrderListByOrderStatus = new ArrayList<MallOrderVO>();
			for (MallOrderVO mallOrder : mallOrderList) {
				if(mallOrderStatus == mallOrder.getMallOrderStatus()) {
					mallOrderListByOrderStatus.add(mallOrder);
				}			
			}
			return mallOrderListByOrderStatus;
		}
		
		public List<MallOrderVO> getMallOrdersByDeliveryStatus(Integer companyId,Integer mallOrderDeliveryStatus) {
			List<MallOrderVO> mallOrderList = getMallOrderByCompany(companyId);
			List<MallOrderVO> mallOrderListByDeliveryStatus = new ArrayList<MallOrderVO>();
			for (MallOrderVO mallOrder : mallOrderList) {
				if(mallOrderDeliveryStatus == mallOrder.getMallOrderStatus()) {
					mallOrderListByDeliveryStatus.add(mallOrder);
				}			
			}
			return mallOrderListByDeliveryStatus;
		}
		
		public MallOrderVO getOneMallOrderByCompany(Integer companyId,Integer mallOrderId) {
			MallOrderVO mallOrderVO = dao.findByPrimaryKey(mallOrderId);
			MallOrderVO mallOrderbycompany = null;
			if(companyId == mallOrderVO.getCompanyId()) {
				mallOrderbycompany = mallOrderVO;
			}
			return mallOrderbycompany;
		}
		
		//	ginny會員條件查詢訂單
		public List<MallOrderVO> getMallOrderByMember(Integer memberId) {
			List<MallOrderVO> mallOrderAll= dao.getAll();
			List<MallOrderVO> mallOrderList = new ArrayList<MallOrderVO>();
			for (MallOrderVO mallOrder : mallOrderAll) {
				if(memberId == mallOrder.getMemberId()) {
					mallOrderList.add(mallOrder);
				}			
			}
			return mallOrderList;
		}
		
		//test
//		public static void main(String[] args) {
//			MallOrderService mallOrderSvc = new MallOrderService();
//			List<MallOrderVO> mallOrderList = mallOrderSvc.getMallOrdersByOrderStatus(1,2);
//			for (MallOrderVO mallOrder : mallOrderList) {
//				System.out.println(mallOrder.getCompanyId());
//				System.out.println(mallOrder.getMailOrderTotalAmount());
//			}
//			
//		}

}
