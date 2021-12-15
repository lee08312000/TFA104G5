package com.mallOrderDetail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Util;

public class MallOrderDetailDAOImpl implements MallOrderDetailDAO {

	private static final String INSERT_STMT = "INSERT INTO mall_order_detail(mall_order_id, product_id, product_purchase_quantity, product_purchase_price) VALUES (?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE mall_order_detail SET mall_order_id = ?, product_id = ?, product_purchase_quantity = ?, product_purchase_price = ?, product_comment_star = ?, product_comment = ?, product_comment_time = ? WHERE mall_order_detail_id = ?";
	private static final String DELETE = "DELETE FROM mall_order_detail WHERE mall_order_detail_id = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM mall_order_detail WHERE mall_order_detail_id = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM mall_order_detail";
	private static final String GET_BY_MALL_ORDER_ID = "SELECT * FROM mall_order_detail WHERE mall_order_id = ?";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(MallOrderDetailVO mallOrderDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, mallOrderDetailVO.getMallOrderId());
			pstmt.setInt(2, mallOrderDetailVO.getProductId());
			pstmt.setInt(3, mallOrderDetailVO.getProductPurchaseQuantity());
			pstmt.setInt(4, mallOrderDetailVO.getProductPurchasePrice());
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
	public void insert(MallOrderDetailVO mallOrderDetailVO, Connection con) {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, mallOrderDetailVO.getMallOrderId());
			pstmt.setInt(2, mallOrderDetailVO.getProductId());
			pstmt.setInt(3, mallOrderDetailVO.getProductPurchaseQuantity());
			pstmt.setInt(4, mallOrderDetailVO.getProductPurchasePrice());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			throw new RuntimeException("訂單明細成立失敗");
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
		}
	}

	@Override
	public void update(MallOrderDetailVO mallOrderDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, mallOrderDetailVO.getMallOrderId());
			pstmt.setInt(2, mallOrderDetailVO.getProductId());
			pstmt.setInt(3, mallOrderDetailVO.getProductPurchaseQuantity());
			pstmt.setInt(4, mallOrderDetailVO.getProductPurchasePrice());
			pstmt.setInt(5, mallOrderDetailVO.getProductCommentStar());
			pstmt.setString(6, mallOrderDetailVO.getProductComment());
			pstmt.setTimestamp(7, mallOrderDetailVO.getProductCommentTime());
			pstmt.setInt(8, mallOrderDetailVO.getMallOrderDetailId());
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
	public void delete(Integer mallOrderDetailId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, mallOrderDetailId);
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
	public MallOrderDetailVO findByPrimaryKey(Integer mallOrderDetailId) {
		MallOrderDetailVO mallOrderDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, mallOrderDetailId);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mallOrderDetailVO = new MallOrderDetailVO();
				mallOrderDetailVO.setMallOrderDetailId(rs.getInt("mall_order_detail_id"));
				mallOrderDetailVO.setMallOrderId(rs.getInt("mall_order_id"));
				mallOrderDetailVO.setProductId(rs.getInt("product_id"));
				mallOrderDetailVO.setProductPurchaseQuantity(rs.getInt("product_purchase_quantity"));
				mallOrderDetailVO.setProductPurchasePrice(rs.getInt("product_purchase_price"));
				mallOrderDetailVO.setProductCommentStar(rs.getInt("product_comment_star"));
				mallOrderDetailVO.setProductComment(rs.getString("product_comment"));
				mallOrderDetailVO.setProductCommentTime(rs.getTimestamp("product_comment_time"));
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
		return mallOrderDetailVO;
	}

	@Override
	public List<MallOrderDetailVO> findBymallOrderId(Integer mallOrderId) {
		List<MallOrderDetailVO> list = new ArrayList<MallOrderDetailVO>();
		MallOrderDetailVO mallOrderDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_BY_MALL_ORDER_ID);
			pstmt.setInt(1, mallOrderId);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {

				mallOrderDetailVO = new MallOrderDetailVO();
				mallOrderDetailVO.setMallOrderDetailId(rs.getInt("mall_order_detail_id"));
				mallOrderDetailVO.setMallOrderId(rs.getInt("mall_order_id"));
				mallOrderDetailVO.setProductId(rs.getInt("product_id"));
				mallOrderDetailVO.setProductPurchaseQuantity(rs.getInt("product_purchase_quantity"));
				mallOrderDetailVO.setProductPurchasePrice(rs.getInt("product_purchase_price"));
				mallOrderDetailVO.setProductCommentStar(rs.getInt("product_comment_star"));
				mallOrderDetailVO.setProductComment(rs.getString("product_comment"));
				mallOrderDetailVO.setProductCommentTime(rs.getTimestamp("product_comment_time"));
				list.add(mallOrderDetailVO);
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

	@Override
	public List<MallOrderDetailVO> getAll() {
		List<MallOrderDetailVO> list = new ArrayList<MallOrderDetailVO>();
		MallOrderDetailVO mallOrderDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mallOrderDetailVO = new MallOrderDetailVO();
				mallOrderDetailVO.setMallOrderDetailId(rs.getInt("mall_order_detail_id"));
				mallOrderDetailVO.setMallOrderId(rs.getInt("mall_order_id"));
				mallOrderDetailVO.setProductId(rs.getInt("product_id"));
				mallOrderDetailVO.setProductPurchaseQuantity(rs.getInt("product_purchase_quantity"));
				mallOrderDetailVO.setProductPurchasePrice(rs.getInt("product_purchase_price"));
				mallOrderDetailVO.setProductCommentStar(rs.getInt("product_comment_star"));
				mallOrderDetailVO.setProductComment(rs.getString("product_comment"));
				mallOrderDetailVO.setProductCommentTime(rs.getTimestamp("product_comment_time"));
				list.add(mallOrderDetailVO);
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

	@Override
	public List<MallOrderDetailVO> getProductComments(Integer productId, Integer orderType, Integer limitX, Integer limitY) {
		String orderTypeString = "";
		String limitString = " limit " + limitX + ", " + limitY;
		
		if (limitX == 0 && limitY == 0) {
			limitString = "";
		}
		
		switch(orderType) {
			case 1 :
				orderTypeString = "ORDER BY product_comment_time DESC";
				break;
			case 2 :
				orderTypeString = "ORDER BY product_comment_time";
				break;
			case 3 :
				orderTypeString = "ORDER BY product_comment_star DESC";
				break;
			case 4 :
				orderTypeString = "ORDER BY product_comment_star";
				break;
		}
		
		String sql = "SELECT * FROM mall_order_detail WHERE product_id = " + productId + " AND product_comment_time IS NOT NULL " + orderTypeString + limitString;
		
		// 測試用
		System.out.println(sql);
		
		List<MallOrderDetailVO> list = new ArrayList<MallOrderDetailVO>();
		MallOrderDetailVO mallOrderDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mallOrderDetailVO = new MallOrderDetailVO();
				mallOrderDetailVO.setMallOrderDetailId(rs.getInt("mall_order_detail_id"));
				mallOrderDetailVO.setMallOrderId(rs.getInt("mall_order_id"));
				mallOrderDetailVO.setProductId(rs.getInt("product_id"));
				mallOrderDetailVO.setProductPurchaseQuantity(rs.getInt("product_purchase_quantity"));
				mallOrderDetailVO.setProductPurchasePrice(rs.getInt("product_purchase_price"));
				mallOrderDetailVO.setProductCommentStar(rs.getInt("product_comment_star"));
				mallOrderDetailVO.setProductComment(rs.getString("product_comment"));
				mallOrderDetailVO.setProductCommentTime(rs.getTimestamp("product_comment_time"));
				list.add(mallOrderDetailVO);
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
//		MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
//		MallOrderDetailDAO dao = new MallOrderDetailDAOImpl();

		// insert 測試
//		mallOrderDetailVO.setMallOrderId(1);
//		mallOrderDetailVO.setProductId(4);
//		mallOrderDetailVO.setProductPurchaseQuantity(20);
//		mallOrderDetailVO.setProductPurchasePrice(200);
//		dao.insert(mallOrderDetailVO);

		// update 測試
//		mallOrderDetailVO.setMallOrderDetailId(4);
//		mallOrderDetailVO.setMallOrderId(1);
//		mallOrderDetailVO.setProductId(4);
//		mallOrderDetailVO.setProductPurchaseQuantity(20);
//		mallOrderDetailVO.setProductPurchasePrice(200);
//		mallOrderDetailVO.setProductCommentStar(3);
//		mallOrderDetailVO.setProductComment("還可以");
//		mallOrderDetailVO.setProductCommentTime(new Timestamp(System.currentTimeMillis()));
//		dao.update(mallOrderDetailVO);

		// delete 測試
//		dao.delete(3);

		// findByPrimaryKey 測試
//		mallOrderDetailVO = dao.findByPrimaryKey(4);
//		System.out.println(mallOrderDetailVO.getMallOrderDetailId());
//		System.out.println(mallOrderDetailVO.getMallOrderId());
//		System.out.println(mallOrderDetailVO.getProductId());
//		System.out.println(mallOrderDetailVO.getProductPurchaseQuantity());
//		System.out.println(mallOrderDetailVO.getProductPurchasePrice());
//		System.out.println(mallOrderDetailVO.getProductCommentStar());
//		System.out.println(mallOrderDetailVO.getProductComment());
//		System.out.println(mallOrderDetailVO.getProductCommentTime());
		
		// getAll 測試
//		List<MallOrderDetailVO> list = dao.getAll();
//		for (MallOrderDetailVO m : list) {
//			System.out.println(m.getMallOrderDetailId());
//			System.out.println(m.getMallOrderId());
//			System.out.println(m.getProductId());
//			System.out.println(m.getProductPurchaseQuantity());
//			System.out.println(m.getProductPurchasePrice());
//			System.out.println(m.getProductCommentStar());
//			System.out.println(m.getProductComment());
//			System.out.println(m.getProductCommentTime());
//			System.out.println("==========================");
//		}
//
//	}

}
