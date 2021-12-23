package com.campOrder.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.camp.model.CampDAO;
import com.camp.model.CampDAOImpl;
import com.campAreaOrderDetail.model.CampAreaOrderDetailDAO;
import com.campAreaOrderDetail.model.CampAreaOrderDetailDAOImpl;
import com.campAreaOrderDetail.model.CampAreaOrderDetailVO;
import com.campBooking.model.CampBookingDAO;
import com.campBooking.model.CampBookingDAOImpl;

public class CampOrderService {

	private CampOrderDAO orderdao;
	private CampAreaOrderDetailDAO detaildao;
	private CampBookingDAO bookdao;
	private CampDAO campdao;

// 建構子
	public CampOrderService() {
		orderdao = new CampOrderDAOImpl();
		detaildao = new CampAreaOrderDetailDAOImpl();
		bookdao = new CampBookingDAOImpl();
		campdao = new CampDAOImpl();
	}

// 新增一筆訂單包含新增訂單明細&日程表訂位數量 回傳訂單編號，當回傳等於=0訂單未成立
	public int addOneOrder(CampOrderVO campOrderVO, List<CampAreaOrderDetailVO> list) {

		if (!(campOrderVO == null || list.size() == 0)) {

			return orderdao.add(campOrderVO, list);

		}
		return 0;
	}

	/********************************************
	 * 更新訂單功能
	 *****************************************/

//更新訂單(只能更新訂單狀態，訂單完成時間，評論時間)暫定
	public void updateOrder(Integer campOrderId, Integer campId, Integer memberId, Integer campOrderStatus,
			Integer campOrderTotalAmount, Date campCheckOutDate, Date campCheckInDate, String creditCardNum,
			String payerName, String payerPhone, Timestamp campOrderConfirmedTime, Timestamp campOrderCompletedTime,
			Integer campCommentStar, String campComment, Timestamp campOrderCommentTime) {
		CampOrderVO order = orderdao.findByPK(campOrderId);
		order.setMemberId(memberId);
		order.setCampOrderStatus(campOrderStatus);
		order.setCampOrderTotalAmount(campOrderTotalAmount);
		order.setCampCheckOutDate(campCheckOutDate);
		order.setCampCheckInDate(campCheckInDate);
		order.setCreditCardNum(creditCardNum);
		order.setPayerName(payerName);
		order.setPayerPhone(payerPhone);
		order.setCampOrderCompletedTime(campOrderCompletedTime);
		order.setCampCommentStar(campCommentStar);
		order.setCampComment(campComment);
		order.setCampOrderCommentTime(campOrderCommentTime);
		orderdao.update(order);

	}
//更新訂單明細(我們專題沒有這個功能)
	public void updateOrderDetail(Integer campAreaOrderDetailId, Integer campAreaId, Integer campOrderId,
			Integer bookingQuantity, Integer campAreaWeekdayPrice, Integer campAreaHolidayPrice,
			Integer capitationQuantity, Integer perCapitationFee, Integer bookingWeekdays, Integer bookingHolidays) {
		CampAreaOrderDetailVO orderdetail = new CampAreaOrderDetailVO();
		orderdetail.setCampAreaOrderDetailId(campAreaOrderDetailId);
		orderdetail.setCampAreaId(campAreaId);
		orderdetail.setCampOrderId(campOrderId);
		orderdetail.setBookingQuantity(bookingQuantity);
		orderdetail.setCampAreaWeekdayPrice(campAreaWeekdayPrice);
		orderdetail.setCampAreaHolidayPrice(campAreaHolidayPrice);
		orderdetail.setCapitationQuantity(capitationQuantity);
		orderdetail.setPerCapitationFee(perCapitationFee);
		orderdetail.setBookingWeekdays(bookingWeekdays);
		orderdetail.setBookingHolidays(bookingHolidays);

		CampOrderVO order = orderdao.findByPK(campOrderId);

		// 對訂單的總金額更改

		int campOrderTotalAmount = bookingQuantity
				* (campAreaWeekdayPrice * bookingWeekdays + campAreaHolidayPrice * bookingHolidays)
				+ (bookingWeekdays + bookingHolidays) * capitationQuantity;

		order.setCampOrderTotalAmount(campOrderTotalAmount);
		orderdao.update(order);
	}

	/********************************************
	 * 查詢訂單功能
	 *****************************************/
//查詢訂單by訂單編號(廠商)
	public CampOrderVO findByCampOrderId(Integer campOrderId) {
		return orderdao.findByPK(campOrderId);

	}

//查詢訂單by訂單狀態(廠商)
	public List OrderByStatus(char statusnum) {
		List<CampOrderVO> daolist = orderdao.getAll();
		List<CampOrderVO> querylist = new ArrayList<CampOrderVO>();
		for (CampOrderVO obj : daolist) {
			if (obj.getCampOrderStatus() == statusnum) {
				querylist.add(obj);
			}
		}

		return querylist;
	}

//查詢訂單by預計入住日期(廠商查詢當日的訂單)
//參數日期要轉換java.sql.Date
	public List OrderByCheckin(Date checkinDate) {
		List<CampOrderVO> daolist = orderdao.getAll();
		List<CampOrderVO> querylist = new ArrayList<CampOrderVO>();
		for (CampOrderVO obj : daolist) {
			if (obj.getCampCheckInDate() == checkinDate) {
				querylist.add(obj);
			}
		}

		return querylist;
	}

//查詢訂單by營地編號(廠商查自己營地所有訂單)
	public List OrderByCompanyId(Integer companyId) {
		List<CampOrderVO> daolist = orderdao.getAll();
		List<CampOrderVO> querylist = new ArrayList<CampOrderVO>();

		for (CampOrderVO obj : daolist) {
			int companyidnum = campdao.findByPrimaryKey(obj.getCampId()).getCompanyId();
			if (companyidnum == companyId) {
				querylist.add(obj);
			}
		}

		return querylist;
	}

//查詢已完成評論的訂單(給使用者觀看訂單評價)評論時間排序(新到舊)
	public List<CampOrderVO> OrderByCommented(Integer campId) {
		List<CampOrderVO> daolist = orderdao.getAll(4);
		List<CampOrderVO> querylist = new ArrayList<CampOrderVO>();
		for (CampOrderVO obj : daolist) {
			if (obj.getCampId() == campId && obj.getCampOrderCommentTime() != null) {
				querylist.add(obj);
			}

		}
		return querylist;

	}

//查詢訂單by會員編號(使用者)
	public List<CampOrderVO> OrderByUserId(Integer memberId) {
		List<CampOrderVO> daolist = orderdao.getAll(0);
		List<CampOrderVO> querylist = new ArrayList<CampOrderVO>();
		for (CampOrderVO obj : daolist) {
			if (obj.getMemberId() == memberId) {
				querylist.add(obj);
			}
		}

		return querylist;
	}

//查詢訂單明細by訂單編號(使用者)

	public List<CampOrderVO> OrderByOrderId(Integer orderId, Integer memberId) {
		List<CampOrderVO> daolist = orderdao.getAll(0);
		List<CampOrderVO> querylist = new ArrayList<CampOrderVO>();
		for (CampOrderVO obj : daolist) {
			if (obj.getMemberId() == memberId && obj.getCampOrderId() == memberId) {
				querylist.add(obj);
			}
		}

		return querylist;
	}

//查詢熱門營地排行(for訂單)
	public List<Integer> findhotcamp() {

		return orderdao.findhotcamp();

	}

	// 查詢訂單by訂單狀態(廠商) 預計入住日期(廠商查詢當日的訂單)
	public List<CampOrderVO> findByParams(int statusnum, java.util.Date begindate, java.util.Date finaldate) {
		List<CampOrderVO> daolist = orderdao.findByParams(statusnum, begindate, finaldate);
		return daolist;
	}


	// 營地訂單評論
	public List<CampOrderVO> selectCampComment(Timestamp startDateTimestamp, Timestamp endDateTimestamp,
			int campOrder) {
	
		return orderdao.selectCampComment(startDateTimestamp, endDateTimestamp, campOrder);
	}

}
