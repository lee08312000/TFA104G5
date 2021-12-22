package com.campEquip.model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Util;

public class CampEquipDAOImpl implements CampEquipDAO {

	private static final String INSERT_STMT = "INSERT INTO camp_equip(camp_equip_name) VALUES (?)";
	private static final String UPDATE_STMT = "UPDATE camp_equip SET camp_equip_name=? WHERE camp_equip_id=?";
	private static final String DELETE_EQUIP = "DELETE FROM camp_equip WHERE camp_equip_id= ?";
	private static final String DELETE_EQUIPDETAIL = "DELETE FROM camp_equip_detail  WHERE camp_equip_id= ?";
	private static final String FIND_BY_PK = "SELECT * FROM camp_equip WHERE  camp_equip_id= ?";
	private static final String GET_ALL = "SELECT * FROM camp_equip";

	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(CampEquipVO campEquipVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, campEquipVO.getCampEquipName());
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
	public void update(CampEquipVO campEquipVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, campEquipVO.getCampEquipName());
			pstmt.setInt(2, campEquipVO.getCampEquipId());
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
	public void delete(Integer campEquipId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_EQUIPDETAIL);
			pstmt.setInt(1, campEquipId);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_EQUIP);
			pstmt.setInt(1, campEquipId);
			pstmt.executeUpdate();
			con.commit();
			
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public CampEquipVO findByPK(Integer campEquipId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampEquipVO campEquipVO=null;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, campEquipId);
			rs = pstmt.executeQuery();
			campEquipVO=new CampEquipVO();
			while(rs.next()) {
			campEquipVO.setCampEquipId(rs.getInt(1));
			campEquipVO.setCampEquipName(rs.getString(2));
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
		return campEquipVO;
	}

	@Override
	public List<CampEquipVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList<CampEquipVO>();
		CampEquipVO campEquipVO =null;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				campEquipVO = new CampEquipVO();
				campEquipVO.setCampEquipId(rs.getInt(1));
				campEquipVO.setCampEquipName(rs.getString(2));
				list.add(campEquipVO);
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
