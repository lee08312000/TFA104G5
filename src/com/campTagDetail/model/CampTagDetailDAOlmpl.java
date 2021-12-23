package com.campTagDetail.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.campEquipDetail.model.CampEquipDetailVO;

import util.Util;

import java.util.List;


public class CampTagDetailDAOlmpl implements CampTagDetailDAO {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/campingParadise?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";


	private static final String CLOUM_FOR_INSERT = "camp_Id,camp_Tag_Id";
	private static final String INSERT_STMT = "INSERT INTO camp_tag_detail (" + CLOUM_FOR_INSERT + ") "
			+ "VALUES (?, ?)";
	private static final String GET_ONE_STMT = "SELECT " + CLOUM_FOR_INSERT
			+ " FROM camp_tag_detail where camp_Tag_Id = ? and camp_Id=?";
	private static final String DELETE = "DELETE FROM camp_tag_detail where  camp_Id=? and camp_Tag_Id = ? ";
	private static final String UPDATE = "UPDATE camp_tag_detail set camp_Tag_Id=?,camp_Id=? where camp_Tag_Id = ? and camp_Id=?";

	private static final String GET_ALL2 = "SELECT*FROM camp_tag_detail";
	private static final String GET_Tag_BY_CAMPID = "SELECT*FROM camp_tag_detail where camp_id=?";

	private static final String DELETE_BY_CAMP_ID = "DELETE FROM camp_tag_detail where  camp_Id=? ";

	private static final String GET_ONE_BY_CAMP_ID ="select ct.camp_tag_name,ct.camp_tag_id from camp_tag_detail ctd join camp_tag ct on ctd.camp_tag_id = ct.camp_tag_id where ctd.camp_id = ? group by ct.camp_tag_id";
	
	private static final String GET_ALL ="select ct.camp_tag_name,ct.camp_tag_id from camp_tag_detail ctd join camp_tag ct on ctd.camp_tag_id = ct.camp_tag_id group by ct.camp_tag_id";

	@Override
	public void insert(CampTagDetailVO campTagDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, campTagDetailVO.getCampId());
			pstmt.setInt(2, campTagDetailVO.getCampTagId());
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
	public void update(CampTagDetailVO campTagDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, campTagDetailVO.getCampTagIdNew());
			pstmt.setInt(2, campTagDetailVO.getCampIdNew());
			pstmt.setInt(3, campTagDetailVO.getCampTagId());
			pstmt.setInt(4, campTagDetailVO.getCampId());
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
	public void delete(Integer campTagId, Integer campId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, campTagId);
			pstmt.setInt(2, campId);

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
	public List<CampTagDetailVO> findByCampId(Integer campId) {
		List<CampTagDetailVO> camptagDetailList = new ArrayList<CampTagDetailVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_CAMP_ID);
			pstmt.setInt(1, campId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CampTagDetailVO campTagDetailVO = new CampTagDetailVO();
				campTagDetailVO.setCampTagName(rs.getString("ct.camp_tag_name"));
				campTagDetailVO.setCampTagId(rs.getInt("ct.camp_tag_id"));
				camptagDetailList.add(campTagDetailVO);
			}

			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		
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
		return camptagDetailList;
	}
	
	@Override
	public CampTagDetailVO findByPrimaryKey(Integer campTagId, Integer campId) {

		CampTagDetailVO campTagDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, campTagId);
			pstmt.setInt(2, campId);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				campTagDetailVO = new CampTagDetailVO();
				campTagDetailVO.setCampId(rs.getInt("camp_Id"));
				campTagDetailVO.setCampTagId(rs.getInt("camp_Tag_Id"));
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
		return campTagDetailVO;
	}

	@Override
	public List<CampTagDetailVO> getAll() {

		CampTagDetailVO campTagDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CampTagDetailVO> list = new ArrayList<CampTagDetailVO>();
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				campTagDetailVO = new CampTagDetailVO();
				campTagDetailVO.setCampTagName(rs.getString("ct.camp_tag_name"));
				campTagDetailVO.setCampTagId(rs.getInt("ct.camp_tag_id"));
				list.add(campTagDetailVO);
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
	public List<Integer> findByCampId2(Integer campId) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> list = new ArrayList<Integer>();
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Tag_BY_CAMPID);
			pstmt.setInt(1, campId);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Integer tags = rs.getInt("camp_Tag_Id");
				list.add(tags);
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
	public Set<CampTagDetailVO> findByMultireq(Set<Integer> camptagSet) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer conditions = new StringBuffer();
		for (Integer item : camptagSet) {
			conditions.append(item).append(",");
		}
		String str = conditions.substring(0, conditions.length() - 1);

		String FIND_BY_MUTICONDITION = "SELECT camp_id,count(*) FROM camp_tag_detail WHERE camp_tag_id in(" + str
				+ ") GROUP BY camp_id HAVING count(*)=" + camptagSet.size();
		Set<CampTagDetailVO> set = new LinkedHashSet<CampTagDetailVO>();
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_MUTICONDITION);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CampTagDetailVO	campTagDetailVO = new CampTagDetailVO();
				campTagDetailVO.setCampId(rs.getInt(1));
				campTagDetailVO.setCampTagId(rs.getInt(2));
				set.add(campTagDetailVO);
			}
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
		return set;
	}

	private byte[] checkBlob(Blob blob) throws SQLException {
		int campPic1length = blob == null ? 0 : (int) blob.length();
		blob.free();
		return blob.getBytes(1, campPic1length);

	}


	@Override
	public List<CampTagDetailVO> getAll2() {
        List<CampTagDetailVO> camptagDetailList = new ArrayList<CampTagDetailVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CampTagDetailVO campTagDetailVO = new CampTagDetailVO();
				campTagDetailVO.setCampTagName(rs.getString("ct.camp_tag_name"));
				campTagDetailVO.setCampTagId(rs.getInt("ct.camp_tag_id"));
				camptagDetailList.add(campTagDetailVO);
			}

			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		
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
		return camptagDetailList;
	}

	@Override
	public void deleteByKey(Integer campId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_BY_CAMP_ID);

			pstmt.setInt(1, campId);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
	
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