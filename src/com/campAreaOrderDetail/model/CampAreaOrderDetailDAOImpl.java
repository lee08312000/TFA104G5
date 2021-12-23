package com.campAreaOrderDetail.model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import util.Util;

public class CampAreaOrderDetailDAOImpl implements CampAreaOrderDetailDAO {
	private static final String INSERT_STMT = "INSERT INTO camp_area_order_detail(camp_area_id,camp_order_id,booking_quantity,camp_area_weekday_price,camp_area_holiday_price,capitation_quantity,per_capitation_fee,booking_weekdays,booking_holidays) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE camp_area_order_detail SET booking_quantit=?,camp_area_weekday_price=?,camp_area_holiday_price=?,capitation_quantity=?,per_capitation_fee=?,booking_weekdays=?,booking_holidays=? WHERE camp_area_id=? and camp_order_id=?";
	private static final String DELETE_STMT = "DELETE FROM camp_area_order_detail WHERE camp_area_order_detail_id= ?";
	private static final String FIND_BY_PK = "SELECT * FROM camp_area_order_detail WHERE camp_area_order_detail_id=?";
	private static final String GET_ALL = "SELECT * FROM camp_area_order_detail";
	private static final String	FIND_BY_CAMP_ORDER_ID="SELECT * FROM camp_area_order_detail WHERE camp_order_id=?";
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(CampAreaOrderDetailVO campAreaOrderDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, campAreaOrderDetailVO.getCampAreaId());
			pstmt.setInt(2, campAreaOrderDetailVO.getCampOrderId());
			pstmt.setInt(3, campAreaOrderDetailVO.getBookingQuantity());
			pstmt.setInt(4, campAreaOrderDetailVO.getCampAreaWeekdayPrice());
			pstmt.setInt(5, campAreaOrderDetailVO.getCampAreaHolidayPrice());
			pstmt.setInt(6, campAreaOrderDetailVO.getCapitationQuantity());
			pstmt.setInt(7, campAreaOrderDetailVO.getPerCapitationFee());
			pstmt.setInt(8, campAreaOrderDetailVO.getBookingWeekdays());
			pstmt.setInt(9, campAreaOrderDetailVO.getBookingHolidays());
			int suc_row = pstmt.executeUpdate();
			System.out.println("成功更新" + suc_row + "筆");
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

		}
	}

	@Override
	public void update(CampAreaOrderDetailVO campAreaOrderDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, campAreaOrderDetailVO.getBookingQuantity());
			pstmt.setInt(2, campAreaOrderDetailVO.getCampAreaWeekdayPrice());
			pstmt.setInt(3, campAreaOrderDetailVO.getCampAreaHolidayPrice());
			pstmt.setInt(4, campAreaOrderDetailVO.getCapitationQuantity());
			pstmt.setInt(5, campAreaOrderDetailVO.getPerCapitationFee());
			pstmt.setInt(6, campAreaOrderDetailVO.getBookingWeekdays());
			pstmt.setInt(7, campAreaOrderDetailVO.getBookingHolidays());
			pstmt.setInt(8, campAreaOrderDetailVO.getCampAreaId());
			pstmt.setInt(9, campAreaOrderDetailVO.getCampOrderId());
			int suc_row = pstmt.executeUpdate();
			System.out.println("成功更新" + suc_row + "筆");
		} catch (SQLException se) {
			se.printStackTrace();

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public CampAreaOrderDetailVO findByPK(Integer campAreaOrderDetailId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampAreaOrderDetailVO campAreaOrderDetailVO = new CampAreaOrderDetailVO();
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, campAreaOrderDetailId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				campAreaOrderDetailVO.setCampAreaOrderDetailId(rs.getInt(1));
				campAreaOrderDetailVO.setCampAreaId(rs.getInt(2));
				campAreaOrderDetailVO.setCampOrderId(rs.getInt(3));
				campAreaOrderDetailVO.setBookingQuantity(rs.getInt(4));
				campAreaOrderDetailVO.setCampAreaWeekdayPrice(rs.getInt(5));
				campAreaOrderDetailVO.setCampAreaHolidayPrice(rs.getInt(6));
				campAreaOrderDetailVO.setCapitationQuantity(rs.getInt(7));
				campAreaOrderDetailVO.setPerCapitationFee(rs.getInt(8));
				campAreaOrderDetailVO.setBookingWeekdays(rs.getInt(9));
				campAreaOrderDetailVO.setBookingHolidays(rs.getInt(10));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return campAreaOrderDetailVO;
	}

	@Override
	public List<CampAreaOrderDetailVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CampAreaOrderDetailVO> list = new ArrayList<CampAreaOrderDetailVO>();
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CampAreaOrderDetailVO campAreaOrderDetailVO = new CampAreaOrderDetailVO();

				campAreaOrderDetailVO.setCampAreaOrderDetailId(rs.getInt(1));
				campAreaOrderDetailVO.setCampAreaId(rs.getInt(2));
				campAreaOrderDetailVO.setCampOrderId(rs.getInt(3));
				campAreaOrderDetailVO.setBookingQuantity(rs.getInt(4));
				campAreaOrderDetailVO.setCampAreaWeekdayPrice(rs.getInt(5));
				campAreaOrderDetailVO.setCampAreaHolidayPrice(rs.getInt(6));
				campAreaOrderDetailVO.setCapitationQuantity(rs.getInt(7));
				campAreaOrderDetailVO.setPerCapitationFee(rs.getInt(8));
				campAreaOrderDetailVO.setBookingWeekdays(rs.getInt(9));
				campAreaOrderDetailVO.setBookingHolidays(rs.getInt(10));
				list.add(campAreaOrderDetailVO);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void delete(Integer campAreaOrderDetailId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, campAreaOrderDetailId);
			int suc_row = pstmt.executeUpdate();
			System.out.println("成功刪除" + suc_row + "筆");
		} catch (SQLException se) {
			se.printStackTrace();

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	// 新增用訂單編號找營位訂單明細
	@Override
	public List<CampAreaOrderDetailVO> findByCampOrderId(Integer campOrderId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampAreaOrderDetailVO campAreaOrderDetailVO = null;
		List<CampAreaOrderDetailVO> list=new ArrayList<CampAreaOrderDetailVO>();
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_CAMP_ORDER_ID);
			pstmt.setInt(1, campOrderId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				campAreaOrderDetailVO = new CampAreaOrderDetailVO();
				campAreaOrderDetailVO.setCampAreaOrderDetailId(rs.getInt(1));
				campAreaOrderDetailVO.setCampAreaId(rs.getInt(2));
				campAreaOrderDetailVO.setCampOrderId(rs.getInt(3));
				campAreaOrderDetailVO.setBookingQuantity(rs.getInt(4));
				campAreaOrderDetailVO.setCampAreaWeekdayPrice(rs.getInt(5));
				campAreaOrderDetailVO.setCampAreaHolidayPrice(rs.getInt(6));
				campAreaOrderDetailVO.setCapitationQuantity(rs.getInt(7));
				campAreaOrderDetailVO.setPerCapitationFee(rs.getInt(8));
				campAreaOrderDetailVO.setBookingWeekdays(rs.getInt(9));
				campAreaOrderDetailVO.setBookingHolidays(rs.getInt(10));
				list.add(campAreaOrderDetailVO);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
				}
			}
			return list;
		}
}

