package com.campArea.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

public class CampAreaDAOImpl implements CampAreaDAO {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/campingParadise?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";

	private static final String CLOUM_FOR_INSERT = "camp_Id,camp_Area_Name,camp_Area_Max,weekday_Price,holiday_Price,capitation_Max,per_Capitation_Fee,camp_Area_Pic";
	private static final String CLOUM_FOR_ALL = "camp_Area_Id," + CLOUM_FOR_INSERT;
	private static final String INSERT_STMT = "INSERT INTO camp_area (" + CLOUM_FOR_INSERT + ") "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM camp_area order by camp_Area_Id";
	private static final String GET_ONE_STMT = "SELECT " + CLOUM_FOR_ALL + " FROM camp_area where camp_Area_Id = ?";
	private static final String DELETE = "DELETE FROM camp_area where camp_Area_Id = ?";
	private static final String UPDATE = "UPDATE camp_area set camp_Id=?,camp_Area_Name=?,camp_Area_Max=?,weekday_Price=?,holiday_Price=?,capitation_Max=?,per_Capitation_Fee=?,camp_Area_Pic=? where camp_Area_Id = ?";
	private static final String GET_CAMPAREALIST = "SELECT " + CLOUM_FOR_ALL + " FROM camp_area where camp_Id = ?";

	@Override
	public void add(CampAreaVO campAreaVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, campAreaVO.getCampId());
			pstmt.setString(2, campAreaVO.getCampAreaName());
			pstmt.setInt(3, campAreaVO.getCampAreaMax());
			pstmt.setInt(4, campAreaVO.getWeekdayPrice());
			pstmt.setInt(5, campAreaVO.getHolidayPrice());
			pstmt.setInt(6, campAreaVO.getCapitationMax());
			pstmt.setInt(7, campAreaVO.getPerCapitationFee());
			if (campAreaVO.getCampAreaPic() != null) {
				pstmt.setBlob(8, new SerialBlob(campAreaVO.getCampAreaPic()));
			} else {
				pstmt.setNull(8, java.sql.Types.BLOB);
			}

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public void update(CampAreaVO campAreaVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, campAreaVO.getCampId());
			pstmt.setString(2, campAreaVO.getCampAreaName());
			pstmt.setInt(3, campAreaVO.getCampAreaMax());
			pstmt.setInt(4, campAreaVO.getWeekdayPrice());
			pstmt.setInt(5, campAreaVO.getHolidayPrice());
			pstmt.setInt(6, campAreaVO.getCapitationMax());
			pstmt.setInt(7, campAreaVO.getPerCapitationFee());
			if (campAreaVO.getCampAreaPic() != null) {
				pstmt.setBytes(8, campAreaVO.getCampAreaPic());
			} else {
				pstmt.setNull(8, java.sql.Types.BLOB);
			}
			pstmt.setInt(9, campAreaVO.getCampAreaId());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public void delete(Integer campAreaId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, campAreaId);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public CampAreaVO findByPrimaryKey(Integer campAreaId) {

		CampAreaVO CampAreaVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, campAreaId);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				CampAreaVO = new CampAreaVO();
				CampAreaVO.setCampAreaId(rs.getInt("camp_Area_Id"));
				CampAreaVO.setCampId(rs.getInt("camp_Id"));
				CampAreaVO.setCampAreaName(rs.getString("camp_Area_Name"));
				CampAreaVO.setCampAreaMax(rs.getInt("camp_Area_Max"));
				CampAreaVO.setWeekdayPrice(rs.getInt("weekday_Price"));
				CampAreaVO.setHolidayPrice(rs.getInt("holiday_Price"));
				CampAreaVO.setCapitationMax(rs.getInt("capitation_Max"));
				CampAreaVO.setPerCapitationFee(rs.getInt("per_Capitation_Fee"));
				CampAreaVO.setCampAreaPic(rs.getBytes("camp_Area_Pic"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
		return CampAreaVO;
	}

	private byte[] checkBlob(Blob blob) throws SQLException {
		int campPic1length = blob == null ? 0 : (int) blob.length();
		byte[] b = null;
		if (blob != null) {
			try {
				b = blob.getBytes(1, campPic1length);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				blob.free();
			}
		}
		return b;

	}

	@Override
	public List<CampAreaVO> getAll() {
		List<CampAreaVO> list = new ArrayList<CampAreaVO>();
		CampAreaVO CampAreaVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CampAreaVO = new CampAreaVO();
				CampAreaVO.setCampAreaId(rs.getInt("camp_area_id"));
				CampAreaVO.setCampId(rs.getInt("camp_Id"));
				CampAreaVO.setCampAreaName(rs.getString("camp_Area_Name"));
				CampAreaVO.setCampAreaMax(rs.getInt("camp_Area_Max"));
				CampAreaVO.setWeekdayPrice(rs.getInt("weekday_Price"));
				CampAreaVO.setHolidayPrice(rs.getInt("holiday_Price"));
				CampAreaVO.setCapitationMax(rs.getInt("capitation_Max"));
				CampAreaVO.setPerCapitationFee(rs.getInt("per_Capitation_Fee"));
				CampAreaVO.setCampAreaPic(checkBlob(rs.getBlob("camp_Area_Pic")));
				list.add(CampAreaVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public List<CampAreaVO> camparealist(Integer campId) {
		List<CampAreaVO> campAreaList = new ArrayList<CampAreaVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_CAMPAREALIST);

			pstmt.setInt(1, campId);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				CampAreaVO camparea = new CampAreaVO();
				camparea.setCampAreaId(rs.getInt("camp_Area_Id"));
				camparea.setCampId(rs.getInt("camp_Id"));
				camparea.setCampAreaName(rs.getString("camp_Area_Name"));
				camparea.setCampAreaMax(rs.getInt("camp_Area_Max"));
				camparea.setWeekdayPrice(rs.getInt("weekday_Price"));
				camparea.setHolidayPrice(rs.getInt("holiday_Price"));
				camparea.setCapitationMax(rs.getInt("capitation_Max"));
				camparea.setPerCapitationFee(rs.getInt("per_Capitation_Fee"));
				camparea.setCampAreaPic(checkBlob(rs.getBlob("camp_Area_Pic")));
				campAreaList.add(camparea);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
		return campAreaList;
	}

	@Override
	public void insert(CampAreaVO campAreaVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, campAreaVO.getCampId());
			pstmt.setString(2, campAreaVO.getCampAreaName());
			pstmt.setInt(3, campAreaVO.getCampAreaMax());
			pstmt.setInt(4, campAreaVO.getWeekdayPrice());
			pstmt.setInt(5, campAreaVO.getHolidayPrice());
			pstmt.setInt(6, campAreaVO.getCapitationMax());
			pstmt.setInt(7, campAreaVO.getPerCapitationFee());
			if (campAreaVO.getCampAreaPic() != null) {
				pstmt.setBytes(8, campAreaVO.getCampAreaPic());
			} else {
				pstmt.setNull(8, java.sql.Types.BLOB);
			}

			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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

}
