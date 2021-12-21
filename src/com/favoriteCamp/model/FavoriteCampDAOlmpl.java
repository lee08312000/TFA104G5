
package com.favoriteCamp.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteCampDAOlmpl implements FavoriteCampDAO {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/campingParadise?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";
	

	private static final String CLOUM_FOR_INSERT = "member_Id,camp_Id";
	private static final String CLOUM_FOR_ALL = "favorite_camp_id,"
			+ CLOUM_FOR_INSERT;
	private static final String INSERT_STMT = "INSERT INTO favorite_camp ("
			+ CLOUM_FOR_INSERT + ") " + "VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT "+CLOUM_FOR_ALL +" FROM favorite_camp where  member_id=?";//12/21新增favorite_camp_id
	
	private static final String GET_ONE_STMT = "SELECT " + CLOUM_FOR_INSERT
			+ " FROM favorite_camp where favorite_camp_id = ?";
	private static final String DELETE = "DELETE FROM favorite_camp where favorite_camp_id = ?";
	private static final String UPDATE = "UPDATE favorite_camp set member_Id=?,camp_Id=? where favorite_camp_id = ?";

	//依照member_id刪除favorite_camp
	private static final String DELETE_BY_MEMBER_ID = "DELETE FROM favorite_camp where member_id = ?";
	
	@Override
	public void add(FavoriteCampVO favoriteCampVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, favoriteCampVO.getMemberId());
			pstmt.setInt(2, favoriteCampVO.getCampId());

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

	@Override
	public void update(FavoriteCampVO favoriteCampVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, favoriteCampVO.getMemberId());
			pstmt.setInt(2, favoriteCampVO.getCampId());
			pstmt.setInt(3, favoriteCampVO.getFavoriteCampId());
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

	@Override
	public void delete(Integer favoriteCampId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, favoriteCampId);

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

	@Override
	public FavoriteCampVO findByPrimaryKey(Integer favoriteCampId) {

		FavoriteCampVO favoriteCampVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, favoriteCampId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				favoriteCampVO = new FavoriteCampVO();
				favoriteCampVO.setCampId(rs.getInt("camp_Id"));
				favoriteCampVO.setMemberId(rs.getInt("member_Id"));
				favoriteCampVO.setFavoriteCampId(rs.getInt("favorite_camp_Id"));//12/21新增
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
		return favoriteCampVO;
	}

	private byte[] checkBlob(Blob blob) throws SQLException {
		int campPic1length = blob == null ? 0 : (int) blob.length();
		blob.free();
		return blob.getBytes(1, campPic1length);

	}

	@Override
	public List<FavoriteCampVO> getAllByMemberId(Integer memberId) {
		List<FavoriteCampVO> list = new ArrayList<FavoriteCampVO>();
		FavoriteCampVO favoriteCampVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setInt(1, memberId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				favoriteCampVO = new FavoriteCampVO();
				favoriteCampVO.setCampId(rs.getInt("camp_Id"));
				favoriteCampVO.setMemberId(rs.getInt("member_Id"));
				favoriteCampVO.setFavoriteCampId(rs.getInt("favorite_camp_id"));
				list.add(favoriteCampVO); 
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
		return list;
	}

	@Override
	public void deleteByMemberId(Integer memberId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_BY_MEMBER_ID);

			pstmt.setInt(1,  memberId);

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