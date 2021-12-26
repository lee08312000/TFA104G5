package com.campBooking.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import util.Util;

public class CampBookingDAOImpl implements CampBookingDAO {
	private static final String INSERT_STMT = "INSERT INTO  camp_booking (camp_id,camp_area_id,date,booking_camp_area_max,booked_camp_area_num,close_status) VALUES (?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE camp_booking  SET camp_id=?,camp_area_id=?,date=?,camp_area_max=?,booked_camp_area_num=?,close_status=? WHERE camp_booking_id=?";
	private static final String DELETE_STMT = "DELETE FROM camp_booking WHERE camp_booking_id= ?";
	private static final String FIND_BY_PK = "SELECT * FROM camp_booking WHERE camp_booking_id=?";
	private static final String FIND_BY_AREAID = "SELECT * FROM camp_booking WHERE camp_area_id=? and date=?";

	private static final String FIND_BY_ALLAREA = "SELECT * FROM camp_booking WHERE camp_id=? and date=?";
	private static final String GET_ALL = "SELECT * FROM camp_area_order_detail";
	private static final String FIND_BY_CampId = "SELECT \r\n" + "    camp_id,\r\n" + "    date,\r\n"
			+ "    SUM(camp_area_max - booked_camp_area_num) AS remain_num,\r\n" + "    close_status\r\n" + "    \r\n"
			+ "FROM\r\n" + "    campingParadise.camp_booking\r\n" + "WHERE\r\n" + "    date = ?\r\n"
			+ "GROUP BY camp_id , date, close_status\r\n" + "HAVING camp_id = ?";
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void delete(Integer campBookingId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, campBookingId);
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

	@Override
	public List<CampBookingVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CampBookingVO> list = new ArrayList<CampBookingVO>();
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CampBookingVO campBookingVO = new CampBookingVO();
				campBookingVO.setCampBookingId(rs.getInt(1));
				campBookingVO.setCampId(rs.getInt(2));
				campBookingVO.setCampAreaId(rs.getInt(3));
				campBookingVO.setDate(rs.getDate(4));
				campBookingVO.setBookingCampAreaMax(rs.getInt(5));
				campBookingVO.setBookedCampAreaNum(rs.getInt(6));
				campBookingVO.setClosedStatus(rs.getBoolean(7));
				list.add(campBookingVO);
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
	public void add(CampBookingVO campBookingVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, campBookingVO.getCampId());
			pstmt.setInt(2, campBookingVO.getCampAreaId());
			pstmt.setDate(3, campBookingVO.getDate());
			pstmt.setInt(4, campBookingVO.getBookingCampAreaMax());
			pstmt.setInt(5, campBookingVO.getBookedCampAreaNum());
			pstmt.setBoolean(6, campBookingVO.getClosedStatus());
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
	public void update(CampBookingVO campBookingVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, campBookingVO.getCampId());
			pstmt.setInt(2, campBookingVO.getCampAreaId());
			pstmt.setDate(3, campBookingVO.getDate());
			pstmt.setInt(4, campBookingVO.getBookingCampAreaMax());
			pstmt.setInt(5, campBookingVO.getBookedCampAreaNum());
			pstmt.setBoolean(6, campBookingVO.getClosedStatus());
			pstmt.setInt(7, campBookingVO.getCampBookingId());
			 pstmt.executeUpdate();
			
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
	public CampBookingVO findByPK(Integer campBookingId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampBookingVO campBookingVO = new CampBookingVO();
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, campBookingId);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				campBookingVO.setCampBookingId(rs.getInt(1));
				campBookingVO.setCampId(rs.getInt(2));
				campBookingVO.setCampAreaId(rs.getInt(3));
				campBookingVO.setDate(rs.getDate(4));
				campBookingVO.setBookingCampAreaMax(rs.getInt(5));
				campBookingVO.setBookedCampAreaNum(rs.getInt(6));
				campBookingVO.setClosedStatus(rs.getBoolean(7));
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
		return campBookingVO;
	}

	@Override
	public List<CampBookingVO> findByAllArea(Integer campId, String date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampBookingVO campBookingVO = null;
		List<CampBookingVO> list = new ArrayList<CampBookingVO>();
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_ALLAREA);
			pstmt.setInt(1, campId);
			pstmt.setDate(2, java.sql.Date.valueOf(date));

			rs = pstmt.executeQuery();
			while (rs.next()) {
				campBookingVO = new CampBookingVO();
				campBookingVO.setCampBookingId(rs.getInt(1));
				campBookingVO.setCampId(rs.getInt(2));
				campBookingVO.setCampAreaId(rs.getInt(3));
				campBookingVO.setDate(rs.getDate(4));
				campBookingVO.setBookingCampAreaMax(rs.getInt(5));
				campBookingVO.setBookedCampAreaNum(rs.getInt(6));
				campBookingVO.setClosedStatus(rs.getBoolean(7));
				list.add(campBookingVO);
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
	public Map<String, Integer> findByCampId(Integer campId, String date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Integer> map = new TreeMap<String, Integer>();
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_CampId);
			pstmt.setDate(1, java.sql.Date.valueOf(date));
			pstmt.setInt(2, campId);
			rs = pstmt.executeQuery();

			// key="2021-11-01" value=2
			while (rs.next()) {
				if (rs.getBoolean(4)) {
					map.put(rs.getString(2), -1);
				} else {
					map.put(rs.getString(2), rs.getInt(3));
				}
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
		return map;
	}

	@Override
	public CampBookingVO findByOneArea(Integer campAreaId, String date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampBookingVO campBookingVO = new CampBookingVO();
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_AREAID);
			pstmt.setInt(1, campAreaId);
			pstmt.setDate(2, java.sql.Date.valueOf(date));


			rs = pstmt.executeQuery();
			while (rs.next()) {
				campBookingVO.setCampBookingId(rs.getInt(1));
				campBookingVO.setCampId(rs.getInt(2));
				campBookingVO.setCampAreaId(rs.getInt(3));
				campBookingVO.setDate(rs.getDate(4));
				campBookingVO.setBookingCampAreaMax(rs.getInt(5));
				campBookingVO.setBookedCampAreaNum(rs.getInt(6));
				campBookingVO.setClosedStatus(rs.getBoolean(7));
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
		return campBookingVO;
	}
		
		
		
		
		
		
		
		
		
		
		
		

}
