package com.productType.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductTypeDAOImpl implements ProductTypeDAO{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/campingParadise?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO product_type (product_type_name) VALUES (?)";
	private static final String GET_ALL_STMT = 
		"SELECT product_type_id,product_type_name FROM product_type order by product_type_id";
	private static final String GET_ONE_STMT = 
		"SELECT product_type_id,product_type_name FROM product_type where product_type_id = ?";
	private static final String DELETE = 
		"DELETE FROM product_type where product_type_id = ?";
	private static final String UPDATE = 
		"UPDATE product_type set product_type_name=? where product_type_id = ?";
	@Override
	public void insert(ProductTypeVO ProductTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, ProductTypeVO.getProductTypeName());
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
	public void update(ProductTypeVO ProductTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, ProductTypeVO.getProductTypeName());
			pstmt.setInt(2, ProductTypeVO.getProductTypeId());	

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
	public void delete(Integer productTypeId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, productTypeId);

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
	public ProductTypeVO findByPrimaryKey(Integer productTypeId) {
		ProductTypeVO ProductTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, productTypeId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				ProductTypeVO = new ProductTypeVO();
				ProductTypeVO.setProductTypeId(rs.getInt("product_type_id"));
				ProductTypeVO.setProductTypeName(rs.getString("product_type_name"));		
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
		return ProductTypeVO;
	}
	@Override
	public List<ProductTypeVO> getAll() {
		List<ProductTypeVO> list = new ArrayList<ProductTypeVO>();
		ProductTypeVO ProductTypeVO = null;

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
				ProductTypeVO = new ProductTypeVO();
				ProductTypeVO.setProductTypeId(rs.getInt("product_type_id"));
				ProductTypeVO.setProductTypeName(rs.getString("product_type_name"));			
				list.add(ProductTypeVO); // Store the row in the list
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
	
	public static void main(String[] args) {

		ProductTypeDAOImpl dao = new ProductTypeDAOImpl();

		// 新增
//		ProductTypeVO ProductTypeVO1 = new ProductTypeVO();
//		ProductTypeVO1.setProductTypeName("鍋具");;
//		dao.insert(ProductTypeVO1);

		// 修改
//		ProductTypeVO ProductTypeVO2 = new ProductTypeVO();
//		ProductTypeVO2.setProductTypeId(9);
//		ProductTypeVO2.setProductTypeName("烤肉架");
//		dao.update(ProductTypeVO2);
//
//		// 刪除
//		dao.delete(10);
//
//		// 查詢
//		ProductTypeVO ProductTypeVOVO3 = dao.findByPrimaryKey(5);
//		System.out.print(ProductTypeVOVO3.getProductTypeId() + ",");
//		System.out.print(ProductTypeVOVO3.getProductTypeName());
//		System.out.println("---------------------");
//
//		// 查詢
		List<ProductTypeVO> list = dao.getAll();
		for (ProductTypeVO aPT : list) {
			System.out.print(aPT.getProductTypeId() + ",");
			System.out.print(aPT.getProductTypeName());
			System.out.println();
		}
	}
	
}
