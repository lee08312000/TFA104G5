package com.productReport.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Util;

public class ProductReportDAOImpl implements ProductReportDAO{
	
	private static final String INSERT_STMT = "INSERT INTO product_report(member_id, product_id, report_reason) VALUES (?, ?, ?)";
	private static final String UPDATE = "UPDATE product_report SET member_id = ?, product_id = ?, report_reason = ?, report_status = ? WHERE product_report_id = ?";
	private static final String DELETE = "DELETE FROM product_report WHERE product_report_id = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM product_report WHERE product_report_id = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM product_report";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(ProductReportVO productReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, productReportVO.getMemberId());
			pstmt.setInt(2, productReportVO.getProductId());
			pstmt.setString(3, productReportVO.getReportReason());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(ProductReportVO productReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, productReportVO.getMemberId());
			pstmt.setInt(2, productReportVO.getProductId());
			pstmt.setString(3, productReportVO.getReportReason());
			pstmt.setInt(4, productReportVO.getReportStatus());
			pstmt.setInt(5, productReportVO.getProductReportId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(Integer productReportId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, productReportId);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ProductReportVO findByPrimaryKey(Integer productReportId) {
		ProductReportVO productReportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, productReportId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				productReportVO = new ProductReportVO();
				productReportVO.setProductReportId(rs.getInt("product_report_id"));
				productReportVO.setMemberId(rs.getInt("member_id"));
				productReportVO.setProductId(rs.getInt("product_id"));
				productReportVO.setReportTime(rs.getTimestamp("report_time"));
				productReportVO.setReportReason(rs.getString("report_reason"));
				productReportVO.setReportStatus(rs.getInt("report_status"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return productReportVO;
	}

	@Override
	public List<ProductReportVO> getAll() {
		List<ProductReportVO> list = new ArrayList<ProductReportVO>();
		ProductReportVO productReportVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				productReportVO = new ProductReportVO();
				productReportVO.setProductReportId(rs.getInt("product_report_id"));
				productReportVO.setMemberId(rs.getInt("member_id"));
				productReportVO.setProductId(rs.getInt("product_id"));
				productReportVO.setReportTime(rs.getTimestamp("report_time"));
				productReportVO.setReportReason(rs.getString("report_reason"));
				productReportVO.setReportStatus(rs.getInt("report_status"));
				list.add(productReportVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	
	
//	public static void main(String[] args) {
//		
//		ProductReportVO productReportVO = new ProductReportVO();
//		ProductReportDAO dao = new ProductReportDAOImpl();
//		// insert 測試
//		
//		productReportVO.setMemberId(1);
//		productReportVO.setProductId(4);
//		productReportVO.setReportReason("太醜了!!!!");
//		
//		dao.insert(productReportVO);
		
		// delete 測試
//		dao.delete(2);
		
		// update 測試
//		productReportVO.setMemberId(2);
//		productReportVO.setProductId(2);
//		productReportVO.setReportReason("lol");
//		productReportVO.setReportStatus(1);
//		productReportVO.setProductReportId(3);
//		
//		dao.update(productReportVO);

		// findByPrimaryKey 測試
//		productReportVO = dao.findByPrimaryKey(4);
//		
//		System.out.println(productReportVO.getProductReportId());
//		System.out.println(productReportVO.getMemberId());
//		System.out.println(productReportVO.getProductId());
//		System.out.println(productReportVO.getReportTime());
//		System.out.println(productReportVO.getReportReason());
//		System.out.println(productReportVO.getReportStatus());
		
		// getAll 測試
//		List<ProductReportVO> list = dao.getAll();
//		
//		for (ProductReportVO p : list) {
//			System.out.println(p.getProductReportId());
//			System.out.println(p.getMemberId());
//			System.out.println(p.getProductId());
//			System.out.println(p.getReportTime());
//			System.out.println(p.getReportReason());
//			System.out.println(p.getReportStatus());
//			System.out.println("========================");
//		}
//		
//	}

}
