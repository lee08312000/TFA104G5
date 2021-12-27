package com.campBooking.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.DiffDays;
import util.GetnextMonth;

public class CampBookingService {

	private CampBookingDAO bookdao;

	public CampBookingService() {
		bookdao = new CampBookingDAOImpl();
	}

//新增公休日某個日期(廠商)	確認要新增的公休日是否有人訂位，否則新增成功

	public int updateCloseStatus(Integer campId, String date) {
		try {
			if (campId != null && date != null) {

				List<CampBookingVO> list = bookdao.findByAllArea(campId, date);
				Iterator it = list.iterator();
				while (it.hasNext()) {
					CampBookingVO booking = (CampBookingVO) it.next();
					if (booking.getBookedCampAreaNum() > 0) {
						System.out.println("此日期已有人預約，請確認訂單");
						return 1;
					}
					if (booking.getClosedStatus() == true) {
						System.out.println("此日期已設公休日，請確認日期");
						return 2;
					}
				}

				for (CampBookingVO obj : list) {

					CampBookingVO cb = bookdao.findByPK(obj.getCampBookingId());
					cb.setClosedStatus(true); // 設為公休狀態
					System.out.println(cb);
					bookdao.update(cb);
				}
				System.out.println(date + ":公休日新增成功");
				return 3;
			}
			System.out.println("請確認輸入格式，新增失敗");
			return 4;

		} catch (Exception e) {
		e.printStackTrace();
		System.out.println("請確認輸入格式，新增失敗");
		return 4;
	
		}
	}

//查詢否個營地某一天某一個日期某一個營位的空位狀態
	public CampBookingVO findByPK(Integer campId, Integer campAreaId, String date) {
		if (campId != null && campAreaId != null && date != null) {
			List<CampBookingVO> listall = bookdao.findByAllArea(campId, date);
			for (CampBookingVO obj : listall) {
				if (obj.getCampAreaId() == campAreaId) {

					return obj;

				}

			}

		}
		return null;
	}

	// 查詢否個某一個營位的某一天空位狀態
	public CampBookingVO findByPK(Integer campAreaId, String date) {

		if (campAreaId != null && date != null) {
			return bookdao.findByOneArea(campAreaId, date);
		} else {

			return null;
		}

	}

//拿取全部空位的資訊
	public List<CampBookingVO> getAll() {
		return bookdao.getAll();
	}

//查詢某個營地某一天的空位數量(月曆用)
//	可以設定抓出要幾個月後的所有日期空位數量

	public List CalendarUse(Integer campId, String begindate, Integer monthnum) {

		if (campId != null && begindate != null && monthnum != null) {
			// 先計算最後日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date startdate;
			try {
				startdate = new java.util.Date(java.sql.Date.valueOf(begindate).getTime());

				Date enddate = GetnextMonth.nextMonths(startdate, monthnum);
				// 取得最後日期與起始日期區間的集合
				List<java.util.Date> datelist = DiffDays.getDates(startdate, enddate);
				List list = new ArrayList();
				// 針對每個日期取空位資訊，放進
				for (Date date : datelist) {

					Map<String, Integer> map = bookdao.findByCampId(campId, sdf.format(date));
					list.add(map);
				}
				return list;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

//某營地的空位資訊
	public Map<String, Integer> findByAllArea(Integer campId, String date) {
		if (campId != null && date != null) {
			return bookdao.findByCampId(campId, date);
		}
		return null;

	}

}
