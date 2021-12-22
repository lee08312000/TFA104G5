package com.favoriteProdoct.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FavoriteProdoctDAOImpl implements FavoriteProdoctDAO{
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/campingParadise?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO favorite_product (member_id,product_id) VALUES (?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT favorite_product_id,member_id,product_id FROM favorite_product order by favorite_product_id";
	private static final String GET_ONE_STMT = 
		"SELECT favorite_product_id,member_id,product_id FROM favorite_product where favorite_product_id = ?";
	private static final String DELETE = 
		"DELETE FROM favorite_product where favorite_product_id = ?";
	private static final String UPDATE = 
		"UPDATE favorite_product set member_id=?, product_id=? where favorite_product_id = ?";
	// update by Lee
	private static final String GET_ONE_BY_MEMBERID_AND_PRODUCTID = 
			"SELECT * FROM favorite_product WHERE member_id = ? AND product_id = ?";
	@Override
	public void insert(FavoriteProdoctVO FavoriteProdoctVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, FavoriteProdoctVO.getMemberId());
			pstmt.setInt(2, FavoriteProdoctVO.getProductId());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void update(FavoriteProdoctVO FavoriteProdoctVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, FavoriteProdoctVO.getMemberId());
			pstmt.setInt(2, FavoriteProdoctVO.getProductId());
			pstmt.setInt(3, FavoriteProdoctVO.getFavoriteProductId());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void delete(Integer favoriteProductId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, favoriteProductId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public FavoriteProdoctVO findByPrimaryKey(Integer favoriteProductId) {
		FavoriteProdoctVO FavoriteProdoctVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, favoriteProductId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				FavoriteProdoctVO = new FavoriteProdoctVO();
				FavoriteProdoctVO.setFavoriteProductId(rs.getInt("favorite_product_id"));
				FavoriteProdoctVO.setMemberId(rs.getInt("member_id"));
				FavoriteProdoctVO.setProductId(rs.getInt("product_id"));				
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return FavoriteProdoctVO;
	}

	@Override
	public List<FavoriteProdoctVO> getAll() {
		List<FavoriteProdoctVO> list = new ArrayList<FavoriteProdoctVO>();
		FavoriteProdoctVO FavoriteProdoctVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				FavoriteProdoctVO = new FavoriteProdoctVO();
				FavoriteProdoctVO.setFavoriteProductId(rs.getInt("favorite_product_id"));
				FavoriteProdoctVO.setMemberId(rs.getInt("member_id"));
				FavoriteProdoctVO.setProductId(rs.getInt("product_id"));				
				list.add(FavoriteProdoctVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	
	// update by Lee 
	@Override
	public FavoriteProdoctVO findByMemberIdAndProductId(Integer memberId, Integer productId) {
		FavoriteProdoctVO FavoriteProdoctVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_MEMBERID_AND_PRODUCTID);

			pstmt.setInt(1, memberId);
			pstmt.setInt(2, productId);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				FavoriteProdoctVO = new FavoriteProdoctVO();
				FavoriteProdoctVO.setFavoriteProductId(rs.getInt("favorite_product_id"));
				FavoriteProdoctVO.setMemberId(rs.getInt("member_id"));
				FavoriteProdoctVO.setProductId(rs.getInt("product_id"));				
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return FavoriteProdoctVO;
	}

	public static void main(String[] args) {

		FavoriteProdoctDAOImpl dao = new FavoriteProdoctDAOImpl();

		// 新增
//		FavoriteProdoctVO FavoriteProdoctVO1 = new FavoriteProdoctVO();
//		FavoriteProdoctVO1.setMemberId(4);
//		FavoriteProdoctVO1.setProductId(4);	
//		dao.insert(FavoriteProdoctVO1);

		// 修改
//		FavoriteProdoctVO FavoriteProdoctVO2 = new FavoriteProdoctVO();
//		FavoriteProdoctVO2.setMemberId(3);
//		FavoriteProdoctVO2.setProductId(3);
//		FavoriteProdoctVO2.setFavoriteProductId(5);		
//		dao.update(FavoriteProdoctVO2);
//
//		// 刪除
//		dao.delete(5);
//
//		// 查詢
//		FavoriteProdoctVO FavoriteProdoctVO3 = dao.findByPrimaryKey(4);
//		System.out.print(FavoriteProdoctVO3.getFavoriteProductId() + ",");
//		System.out.print(FavoriteProdoctVO3.getMemberId() + ",");
//		System.out.print(FavoriteProdoctVO3.getProductId() + ",");
//		System.out.println("---------------------");
//
		// 查詢
		List<FavoriteProdoctVO> list = dao.getAll();
		for (FavoriteProdoctVO aFP : list) {
			System.out.print(aFP.getFavoriteProductId() + ",");
			System.out.print(aFP.getMemberId() + ",");
			System.out.print(aFP.getProductId() + ",");
			System.out.println();
		}
		
//		// update by Lee
//		FavoriteProdoctVO FavoriteProdoctVO4 = dao.findByMemberIdAndProductId(3, 3);
//		System.out.print(FavoriteProdoctVO4.getFavoriteProductId() + ",");
//		System.out.print(FavoriteProdoctVO4.getMemberId() + ",");
//		System.out.print(FavoriteProdoctVO4.getProductId() + ",");
//		System.out.println("---------------------");
	}

}
