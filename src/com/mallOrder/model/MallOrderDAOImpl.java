package com.mallOrder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cart.model.CartVO;
import com.mallOrderDetail.model.MallOrderDetailService;
import com.product.model.ProductService;
import com.product.model.ProductVO;

import util.Util;

public class MallOrderDAOImpl implements MallOrderDAO {

	private static final String INSERT_STMT = "INSERT INTO mall_order(company_id, member_id, mail_order_total_amount, credit_card_num, receiver_name, receiver_phone, receiver_address) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE mall_order SET company_id = ?, member_id = ?, mall_order_status = ?, mall_order_delivery_status = ?, mail_order_total_amount = ?, credit_card_num = ?, receiver_name = ?, receiver_phone = ?, receiver_address = ?, mall_order_completed_time = ? WHERE mall_order_id = ?";
	private static final String DELETE = "DELETE FROM mall_order WHERE mall_order_id = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM mall_order WHERE mall_order_id = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM mall_order";
	private static final String GET_ONE_BY_MEMBERID = "SELECT * FROM mall_order WHERE member_id = ? AND mall_order_id = ?";

	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(MallOrderVO mallOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, mallOrderVO.getCompanyId());
			pstmt.setInt(2, mallOrderVO.getMemberId());
			pstmt.setInt(3, mallOrderVO.getMailOrderTotalAmount());
			pstmt.setString(4, mallOrderVO.getCreditCardNum());
			pstmt.setString(5, mallOrderVO.getReceiverName());
			pstmt.setString(6, mallOrderVO.getReceiverPhone());
			pstmt.setString(7, mallOrderVO.getReceiverAddress());
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
	public Integer insert(MallOrderVO mallOrderVO, List<CartVO> buyList) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer mallOrderId = null;
		MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();
		ProductService productSvc = new ProductService();

		try {
			String[] cols = {"mall_order_id"};
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			con.setAutoCommit(false);

			pstmt.setInt(1, mallOrderVO.getCompanyId());
			pstmt.setInt(2, mallOrderVO.getMemberId());
			pstmt.setInt(3, mallOrderVO.getMailOrderTotalAmount());
			pstmt.setString(4, mallOrderVO.getCreditCardNum());
			pstmt.setString(5, mallOrderVO.getReceiverName());
			pstmt.setString(6, mallOrderVO.getReceiverPhone());
			pstmt.setString(7, mallOrderVO.getReceiverAddress());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				mallOrderId = rs.getInt(1);
			}

			for (int i = 0; i < buyList.size(); i++) {
				CartVO cartVO = buyList.get(i);
				mallOrderDetailSvc.addMallOrderDetailFromOrder(mallOrderId, cartVO.getProductId(),
						cartVO.getProductPurchaseQuantity(), cartVO.getProductPrice(), con);
				ProductVO productVO = productSvc.getOneProduct(cartVO.getProductId());
				Integer newProductInventory = productVO.getProductInventory() - cartVO.getProductPurchaseQuantity();
				Integer newProductSellAllnum = productVO.getProductSellAllnum() + cartVO.getProductPurchaseQuantity();
				productSvc.updateProductInventoryAndProductSellAllnum(cartVO.getProductId(), newProductInventory,
						newProductSellAllnum, con);

			}

			con.commit();
			con.setAutoCommit(true);
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (rs != null) {
				try {
					rs.close();
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
		
		return mallOrderId;
	}

	@Override
	public Integer insert(MallOrderVO mallOrderVO, List<CartVO> buyList, Connection con) {

		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer mallOrderId = null;
		MallOrderDetailService mallOrderDetailSvc = new MallOrderDetailService();
		ProductService productSvc = new ProductService();

		try {
			String[] cols = {"mall_order_id"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			

			pstmt.setInt(1, mallOrderVO.getCompanyId());
			pstmt.setInt(2, mallOrderVO.getMemberId());
			pstmt.setInt(3, mallOrderVO.getMailOrderTotalAmount());
			pstmt.setString(4, mallOrderVO.getCreditCardNum());
			pstmt.setString(5, mallOrderVO.getReceiverName());
			pstmt.setString(6, mallOrderVO.getReceiverPhone());
			pstmt.setString(7, mallOrderVO.getReceiverAddress());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				mallOrderId = rs.getInt(1);
			}

			for (int i = 0; i < buyList.size(); i++) {
				CartVO cartVO = buyList.get(i);
				mallOrderDetailSvc.addMallOrderDetailFromOrder(mallOrderId, cartVO.getProductId(),
						cartVO.getProductPurchaseQuantity(), cartVO.getProductPrice(), con);
				ProductVO productVO = productSvc.getOneProduct(cartVO.getProductId());
				Integer newProductInventory = productVO.getProductInventory() - cartVO.getProductPurchaseQuantity();
				Integer newProductSellAllnum = productVO.getProductSellAllnum() + cartVO.getProductPurchaseQuantity();
				productSvc.updateProductInventoryAndProductSellAllnum(cartVO.getProductId(), newProductInventory,
						newProductSellAllnum, con);

			}

			
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
					mallOrderId = null;
					throw new RuntimeException("訂單主檔成立失敗");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (rs != null) {
				try {
					rs.close();
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
		
		return mallOrderId;
	}
	
	@Override
	public List<Integer> checkout(Integer memberId, String creditCardNum, String receiverName, String receiverPhone,
			String receiverAddress, List<CartVO> buyList) {

		// 存放增加的訂單編號
		List<Integer> mallOrderIdList = new ArrayList<Integer>();
		
		MallOrderDAO mallOrderDAO = new MallOrderDAOImpl();
		Connection con = null;
		
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			// 創造companySet
			Set<Integer> companySet = new TreeSet<Integer>();
			for (CartVO c : buyList) {
				companySet.add(c.getCompanyId());
			}

			// 交易開始
			con.setAutoCommit(false);
			
			// 用set分別成立訂單
			for (Integer companyId : companySet) {
				// 存放增加的主鍵
				Integer mallOrderId = null;

				List<CartVO> newBuyList = new ArrayList<CartVO>();
				Integer mailOrderTotalAmount = 0;

				// 將此廠商的商品存到新的List
				for (int i = 0; i < buyList.size(); i++) {

					CartVO cartVO = buyList.get(i);
					if (companyId.intValue() == cartVO.getCompanyId().intValue()) {
						newBuyList.add(cartVO);
					}
				}

				// 計算此訂單的總金額
				for (int i = 0; i < newBuyList.size(); i++) {
					CartVO cartVO = newBuyList.get(i);
					mailOrderTotalAmount += cartVO.getProductPrice() * cartVO.getProductPurchaseQuantity();
				}

				// 新增訂單主檔、訂單明細、修改商品庫存量及銷量

				MallOrderVO mallOrderVO = new MallOrderVO();

				mallOrderVO.setCompanyId(companyId);
				mallOrderVO.setMemberId(memberId);
				mallOrderVO.setMailOrderTotalAmount(mailOrderTotalAmount);
				mallOrderVO.setCreditCardNum(creditCardNum);
				mallOrderVO.setReceiverName(receiverName);
				mallOrderVO.setReceiverPhone(receiverPhone);
				mallOrderVO.setReceiverAddress(receiverAddress);

				
				mallOrderId = mallOrderDAO.insert(mallOrderVO, newBuyList, con);
				mallOrderIdList.add(mallOrderId);
			}
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			mallOrderIdList = null;
			e.printStackTrace();
//			throw new RuntimeException("訂單成立失敗");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		

		return mallOrderIdList;
	}

	@Override
	public void update(MallOrderVO mallOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, mallOrderVO.getCompanyId());
			pstmt.setInt(2, mallOrderVO.getMemberId());
			pstmt.setInt(3, mallOrderVO.getMallOrderStatus());
			pstmt.setInt(4, mallOrderVO.getMallOrderDeliveryStatus());
			pstmt.setInt(5, mallOrderVO.getMailOrderTotalAmount());
			pstmt.setString(6, mallOrderVO.getCreditCardNum());
			pstmt.setString(7, mallOrderVO.getReceiverName());
			pstmt.setString(8, mallOrderVO.getReceiverPhone());
			pstmt.setString(9, mallOrderVO.getReceiverAddress());
			pstmt.setTimestamp(10, mallOrderVO.getMallOrderCompletedTime());
			pstmt.setInt(11, mallOrderVO.getMallOrderId());
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
	public void delete(Integer mallOrderId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, mallOrderId);
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
	public MallOrderVO findByPrimaryKey(Integer mallOrderId) {
		MallOrderVO mallOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, mallOrderId);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mallOrderVO = new MallOrderVO();

				mallOrderVO.setMallOrderId(rs.getInt("mall_order_id"));
				mallOrderVO.setCompanyId(rs.getInt("company_id"));
				mallOrderVO.setMemberId(rs.getInt("member_id"));
				mallOrderVO.setMallOrderStatus(rs.getInt("mall_order_status"));
				mallOrderVO.setMallOrderDeliveryStatus(rs.getInt("mall_order_delivery_status"));
				mallOrderVO.setMailOrderTotalAmount(rs.getInt("mail_order_total_amount"));
				mallOrderVO.setMallOrderConfirmedTime(rs.getTimestamp("mall_order_confirmed_time"));
				mallOrderVO.setCreditCardNum(rs.getString("credit_card_num"));
				mallOrderVO.setReceiverName(rs.getString("receiver_name"));
				mallOrderVO.setReceiverPhone(rs.getString("receiver_phone"));
				mallOrderVO.setReceiverAddress(rs.getString("receiver_address"));
				mallOrderVO.setMallOrderCompletedTime(rs.getTimestamp("mall_order_completed_time"));
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
		return mallOrderVO;
	}

	@Override
	public List<MallOrderVO> getAll() {
		List<MallOrderVO> list = new ArrayList<MallOrderVO>();
		MallOrderVO mallOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mallOrderVO = new MallOrderVO();

				mallOrderVO.setMallOrderId(rs.getInt("mall_order_id"));
				mallOrderVO.setCompanyId(rs.getInt("company_id"));
				mallOrderVO.setMemberId(rs.getInt("member_id"));
				mallOrderVO.setMallOrderStatus(rs.getInt("mall_order_status"));
				mallOrderVO.setMallOrderDeliveryStatus(rs.getInt("mall_order_delivery_status"));
				mallOrderVO.setMailOrderTotalAmount(rs.getInt("mail_order_total_amount"));
				mallOrderVO.setMallOrderConfirmedTime(rs.getTimestamp("mall_order_confirmed_time"));
				mallOrderVO.setCreditCardNum(rs.getString("credit_card_num"));
				mallOrderVO.setReceiverName(rs.getString("receiver_name"));
				mallOrderVO.setReceiverPhone(rs.getString("receiver_phone"));
				mallOrderVO.setReceiverAddress(rs.getString("receiver_address"));
				mallOrderVO.setMallOrderCompletedTime(rs.getTimestamp("mall_order_completed_time"));
				list.add(mallOrderVO);
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
//		MallOrderVO mallOrderVO = new MallOrderVO();
//		MallOrderDAO dao = new MallOrderDAOImpl();

	// insert 測試
//		mallOrderVO.setCompanyId(2);
//		mallOrderVO.setMemberId(1);
//		mallOrderVO.setMailOrderTotalAmount(4999);
//		mallOrderVO.setCreditCardNum("1234123412341234");
//		mallOrderVO.setReceiverName("蜥蜴");
//		mallOrderVO.setReceiverPhone("25555555");
//		mallOrderVO.setReceiverAddress("台北101");
//		dao.insert(mallOrderVO);

	// update 測試
//		mallOrderVO.setCompanyId(2);
//		mallOrderVO.setMemberId(1);
//		mallOrderVO.setMallOrderStatus(2);
//		mallOrderVO.setMallOrderDeliveryStatus(1);
//		mallOrderVO.setMailOrderTotalAmount(4999);
//		mallOrderVO.setCreditCardNum("1234123412341234");
//		mallOrderVO.setReceiverName("蜥蜴");
//		mallOrderVO.setReceiverPhone("25555555");
//		mallOrderVO.setReceiverAddress("台北101");
//		mallOrderVO.setMallOrderCompletedTime(new Timestamp(System.currentTimeMillis()));
//		mallOrderVO.setMallOrderId(3);
//		dao.update(mallOrderVO);

	// delete 測試
//		dao.delete(6);

	// findByPrimaryKey 測試

//		mallOrderVO = dao.findByPrimaryKey(3);
//		System.out.println(mallOrderVO.getMallOrderId());
//		System.out.println(mallOrderVO.getCompanyId());
//		System.out.println(mallOrderVO.getMemberId());
//		System.out.println(mallOrderVO.getMallOrderStatus());
//		System.out.println(mallOrderVO.getMallOrderDeliveryStatus());
//		System.out.println(mallOrderVO.getMailOrderTotalAmount());
//		System.out.println(mallOrderVO.getMallOrderConfirmedTime());
//		System.out.println(mallOrderVO.getCreditCardNum());
//		System.out.println(mallOrderVO.getReceiverName());
//		System.out.println(mallOrderVO.getReceiverPhone());
//		System.out.println(mallOrderVO.getReceiverAddress());
//		System.out.println(mallOrderVO.getMallOrderCompletedTime());

	// getAll 測試
//		List<MallOrderVO> list = dao.getAll();
//		
//		for (MallOrderVO m : list) {
//			System.out.println(m.getMallOrderId());
//			System.out.println(m.getCompanyId());
//			System.out.println(m.getMemberId());
//			System.out.println(m.getMallOrderStatus());
//			System.out.println(m.getMallOrderDeliveryStatus());
//			System.out.println(m.getMailOrderTotalAmount());
//			System.out.println(m.getMallOrderConfirmedTime());
//			System.out.println(m.getCreditCardNum());
//			System.out.println(m.getReceiverName());
//			System.out.println(m.getReceiverPhone());
//			System.out.println(m.getReceiverAddress());
//			System.out.println(m.getMallOrderCompletedTime());
//			System.out.println("==================================");
//		}
//	}

}
