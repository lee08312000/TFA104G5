package com.product.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/campingParadise?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO product (company_id,product_type_id,product_status,product_name,product_price,product_brand,product_inventory,product_description,shopping_information,product_pic_1,product_pic_2,product_pic_3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT product_id,company_id,product_type_id,product_status,product_name,product_price,product_brand,product_inventory,product_description,shopping_information,product_pic_1,product_pic_2,product_pic_3,product_launched_time,product_commented_allnum,product_comment_allstar,product_sell_allnum FROM product order by product_id";
	private static final String GET_ONE_STMT = "SELECT product_id,company_id,product_type_id,product_status,product_name,product_price,product_brand,product_inventory,product_description,shopping_information,product_pic_1,product_pic_2,product_pic_3,product_launched_time,product_commented_allnum,product_comment_allstar,product_sell_allnum FROM product where product_id = ?";
	private static final String DELETE = "DELETE FROM product where product_id = ?";
	private static final String UPDATE = "UPDATE product set company_id=?, product_type_id=?, product_status=?, product_name=?, product_price=?, product_brand=?, product_inventory=?, product_description=?, shopping_information=?, product_pic_1=?, product_pic_2=?, product_pic_3=?, product_commented_allnum=?, product_comment_allstar=?, product_sell_allnum=? where product_id = ?";
	private static final String GET_PRODUCT_LIST = "SELECT product_id,company_id,product_type_id,product_status,product_name,product_price,product_brand,product_inventory,product_description,shopping_information,product_pic_1,product_pic_2,product_pic_3,product_launched_time,product_commented_allnum,product_comment_allstar,product_sell_allnum FROM product where company_id = ? AND product_name like ?";
	@Override
	public void insert(ProductVO ProductVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, ProductVO.getCompanyId());
			pstmt.setInt(2, ProductVO.getProductTypeId());
			pstmt.setInt(3, ProductVO.getProductStatus());
			pstmt.setString(4, ProductVO.getProductName());
			pstmt.setInt(5, ProductVO.getProductPrice());
			pstmt.setString(6, ProductVO.getProductBrand());
			pstmt.setInt(7, ProductVO.getProductInventory());
			pstmt.setString(8, ProductVO.getProductDescription());
			pstmt.setString(9, ProductVO.getShoppingInformation());
			pstmt.setBytes(10, ProductVO.getProductPic1());
			pstmt.setBytes(11, ProductVO.getProductPic2());
			pstmt.setBytes(12, ProductVO.getProductPic3());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(ProductVO ProductVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, ProductVO.getCompanyId());
			pstmt.setInt(2, ProductVO.getProductTypeId());
			pstmt.setInt(3, ProductVO.getProductStatus());
			pstmt.setString(4, ProductVO.getProductName());
			pstmt.setInt(5, ProductVO.getProductPrice());
			pstmt.setString(6, ProductVO.getProductBrand());
			pstmt.setInt(7, ProductVO.getProductInventory());
			pstmt.setString(8, ProductVO.getProductDescription());
			pstmt.setString(9, ProductVO.getShoppingInformation());
			pstmt.setBytes(10, ProductVO.getProductPic1());
			pstmt.setBytes(11, ProductVO.getProductPic2());
			pstmt.setBytes(12, ProductVO.getProductPic3());
			pstmt.setInt(13, ProductVO.getProductCommentedAllnum());
			pstmt.setInt(14, ProductVO.getProductCommentAllstar());
			pstmt.setInt(15, ProductVO.getProductSellAllnum());
			pstmt.setInt(16, ProductVO.getProductId());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(Integer productId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, productId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public ProductVO findByPrimaryKey(Integer productId) {
		ProductVO ProductVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, productId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				ProductVO = new ProductVO();
				ProductVO.setProductId(rs.getInt("product_id"));
				ProductVO.setCompanyId(rs.getInt("company_id"));
				ProductVO.setProductTypeId(rs.getInt("product_type_id"));
				ProductVO.setProductStatus(rs.getInt("product_status"));
				ProductVO.setProductName(rs.getString("product_name"));
				ProductVO.setProductPrice(rs.getInt("product_price"));
				ProductVO.setProductBrand(rs.getString("product_brand"));
				ProductVO.setProductInventory(rs.getInt("product_inventory"));
				ProductVO.setProductDescription(rs.getString("product_description"));
				ProductVO.setShoppingInformation(rs.getString("shopping_information"));
				ProductVO.setProductPic1(rs.getBytes("product_pic_1"));
				ProductVO.setProductPic2(rs.getBytes("product_pic_2"));
				ProductVO.setProductPic3(rs.getBytes("product_pic_3"));
				ProductVO.setProductLaunchedTime(rs.getTimestamp("product_launched_time"));
				ProductVO.setProductCommentedAllnum(rs.getInt("product_commented_allnum"));
				ProductVO.setProductCommentAllstar(rs.getInt("product_comment_allstar"));
				ProductVO.setProductSellAllnum(rs.getInt("product_sell_allnum"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return ProductVO;
	}

	@Override
	public List<ProductVO> getAll() {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO ProductVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductVO = new ProductVO();
				ProductVO.setProductId(rs.getInt("product_id"));
				ProductVO.setCompanyId(rs.getInt("company_id"));
				ProductVO.setProductTypeId(rs.getInt("product_type_id"));
				ProductVO.setProductStatus(rs.getInt("product_status"));
				ProductVO.setProductName(rs.getString("product_name"));
				ProductVO.setProductPrice(rs.getInt("product_price"));
				ProductVO.setProductBrand(rs.getString("product_brand"));
				ProductVO.setProductInventory(rs.getInt("product_inventory"));
				ProductVO.setProductDescription(rs.getString("product_description"));
				ProductVO.setShoppingInformation(rs.getString("shopping_information"));
				ProductVO.setProductPic1(rs.getBytes("product_pic_1"));
				ProductVO.setProductPic2(rs.getBytes("product_pic_2"));
				ProductVO.setProductPic3(rs.getBytes("product_pic_3"));
				ProductVO.setProductLaunchedTime(rs.getTimestamp("product_launched_time"));
				ProductVO.setProductCommentedAllnum(rs.getInt("product_commented_allnum"));
				ProductVO.setProductCommentAllstar(rs.getInt("product_comment_allstar"));
				ProductVO.setProductSellAllnum(rs.getInt("product_sell_allnum"));
				list.add(ProductVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	// 商城用
	@Override
	public List<ProductVO> getProducts(Integer productTypeId, Integer companyId, Integer orderType, Integer limitX,
			Integer limitY) {
		String productTypeIdString = "";
		String companyIdString = "";
		String orderByString = " ORDER BY ";
		String orderTypeString = "";
		String limitString = " limit " + limitX + ", " + limitY;

		if (productTypeId != 0) {
			productTypeIdString = "AND product_type_id = " + productTypeId;
		}

		if (companyId != 0) {
			companyIdString = " AND company_id = " + companyId;
		}

		if (orderType == 0) {
			orderByString = "";
		}

		if (limitX == 0 && limitY == 0) {
			limitString = "";
		}

		switch (orderType) {
		case 1:
			orderTypeString = "product_sell_allnum DESC";
			break;
		case 2:
			orderTypeString = "product_launched_time DESC";
			break;
		case 3:
			orderTypeString = "IFNULL(ROUND(product_comment_allstar / product_commented_allnum), 0) DESC";
			break;
		case 4:
			orderTypeString = "product_price";
			break;
		case 5:
			orderTypeString = "product_price DESC";
			break;
		}

		String sql = "SELECT * FROM product WHERE product_status = 1 " + productTypeIdString + companyIdString
				+ orderByString + orderTypeString + limitString;
		System.out.println(sql);

		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO ProductVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductVO = new ProductVO();
				ProductVO.setProductId(rs.getInt("product_id"));
				ProductVO.setCompanyId(rs.getInt("company_id"));
				ProductVO.setProductTypeId(rs.getInt("product_type_id"));
				ProductVO.setProductStatus(rs.getInt("product_status"));
				ProductVO.setProductName(rs.getString("product_name"));
				ProductVO.setProductPrice(rs.getInt("product_price"));
				ProductVO.setProductBrand(rs.getString("product_brand"));
				ProductVO.setProductInventory(rs.getInt("product_inventory"));
				ProductVO.setProductDescription(rs.getString("product_description"));
				ProductVO.setShoppingInformation(rs.getString("shopping_information"));
				ProductVO.setProductPic1(rs.getBytes("product_pic_1"));
				ProductVO.setProductPic2(rs.getBytes("product_pic_2"));
				ProductVO.setProductPic3(rs.getBytes("product_pic_3"));
				ProductVO.setProductLaunchedTime(rs.getTimestamp("product_launched_time"));
				ProductVO.setProductCommentedAllnum(rs.getInt("product_commented_allnum"));
				ProductVO.setProductCommentAllstar(rs.getInt("product_comment_allstar"));
				ProductVO.setProductSellAllnum(rs.getInt("product_sell_allnum"));
				list.add(ProductVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	@Override
	public List<ProductVO> getProductsByName(String productName, Integer companyId, Integer orderType, Integer limitX,
			Integer limitY) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		if (productName.trim().isEmpty()) {
			return list;
		}
		
		String productNameString = " AND product_name like " + "'%" + productName.trim() + "%'";
		String companyIdString = "";
		String orderByString = " ORDER BY ";
		String orderTypeString = "";
		String limitString = " limit " + limitX + ", " + limitY;


		if (companyId != 0) {
			companyIdString = " AND company_id = " + companyId;
		}

		if (orderType == 0) {
			orderByString = "";
		}

		if (limitX == 0 && limitY == 0) {
			limitString = "";
		}

		switch (orderType) {
		case 1:
			orderTypeString = "product_sell_allnum DESC";
			break;
		case 2:
			orderTypeString = "product_launched_time DESC";
			break;
		case 3:
			orderTypeString = "IFNULL(ROUND(product_comment_allstar / product_commented_allnum), 0) DESC";
			break;
		case 4:
			orderTypeString = "product_price";
			break;
		case 5:
			orderTypeString = "product_price DESC";
			break;
		}

		String sql = "SELECT * FROM product WHERE product_status = 1 " + productNameString + companyIdString
				+ orderByString + orderTypeString + limitString;
		System.out.println(sql);

		
		ProductVO ProductVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductVO = new ProductVO();
				ProductVO.setProductId(rs.getInt("product_id"));
				ProductVO.setCompanyId(rs.getInt("company_id"));
				ProductVO.setProductTypeId(rs.getInt("product_type_id"));
				ProductVO.setProductStatus(rs.getInt("product_status"));
				ProductVO.setProductName(rs.getString("product_name"));
				ProductVO.setProductPrice(rs.getInt("product_price"));
				ProductVO.setProductBrand(rs.getString("product_brand"));
				ProductVO.setProductInventory(rs.getInt("product_inventory"));
				ProductVO.setProductDescription(rs.getString("product_description"));
				ProductVO.setShoppingInformation(rs.getString("shopping_information"));
				ProductVO.setProductPic1(rs.getBytes("product_pic_1"));
				ProductVO.setProductPic2(rs.getBytes("product_pic_2"));
				ProductVO.setProductPic3(rs.getBytes("product_pic_3"));
				ProductVO.setProductLaunchedTime(rs.getTimestamp("product_launched_time"));
				ProductVO.setProductCommentedAllnum(rs.getInt("product_commented_allnum"));
				ProductVO.setProductCommentAllstar(rs.getInt("product_comment_allstar"));
				ProductVO.setProductSellAllnum(rs.getInt("product_sell_allnum"));
				list.add(ProductVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	// update by Lee 購物車交易用
	@Override
	public void update(ProductVO ProductVO, Connection con) {
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, ProductVO.getCompanyId());
			pstmt.setInt(2, ProductVO.getProductTypeId());
			pstmt.setInt(3, ProductVO.getProductStatus());
			pstmt.setString(4, ProductVO.getProductName());
			pstmt.setInt(5, ProductVO.getProductPrice());
			pstmt.setString(6, ProductVO.getProductBrand());
			pstmt.setInt(7, ProductVO.getProductInventory());
			pstmt.setString(8, ProductVO.getProductDescription());
			pstmt.setString(9, ProductVO.getShoppingInformation());
			pstmt.setBytes(10, ProductVO.getProductPic1());
			pstmt.setBytes(11, ProductVO.getProductPic2());
			pstmt.setBytes(12, ProductVO.getProductPic3());
			pstmt.setInt(13, ProductVO.getProductCommentedAllnum());
			pstmt.setInt(14, ProductVO.getProductCommentAllstar());
			pstmt.setInt(15, ProductVO.getProductSellAllnum());
			pstmt.setInt(16, ProductVO.getProductId());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			se.printStackTrace();
			throw new RuntimeException("商品庫存量及銷量修改失敗");
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
		}
	}
	
	//用商品名查找By ginny
	@Override
	public List<ProductVO> getProductsByLikeName(Integer companyId, String productName) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO ProductVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_PRODUCT_LIST);
			pstmt.setInt(1, companyId);
			pstmt.setString(2, "%"+productName+"%");
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductVO = new ProductVO();
				ProductVO.setProductId(rs.getInt("product_id"));
				ProductVO.setCompanyId(rs.getInt("company_id"));
				ProductVO.setProductTypeId(rs.getInt("product_type_id"));
				ProductVO.setProductStatus(rs.getInt("product_status"));
				ProductVO.setProductName(rs.getString("product_name"));
				ProductVO.setProductPrice(rs.getInt("product_price"));
				ProductVO.setProductBrand(rs.getString("product_brand"));
				ProductVO.setProductInventory(rs.getInt("product_inventory"));
				ProductVO.setProductDescription(rs.getString("product_description"));
				ProductVO.setShoppingInformation(rs.getString("shopping_information"));
				ProductVO.setProductPic1(rs.getBytes("product_pic_1"));
				ProductVO.setProductPic2(rs.getBytes("product_pic_2"));
				ProductVO.setProductPic3(rs.getBytes("product_pic_3"));
				ProductVO.setProductLaunchedTime(rs.getTimestamp("product_launched_time"));
				ProductVO.setProductCommentedAllnum(rs.getInt("product_commented_allnum"));
				ProductVO.setProductCommentAllstar(rs.getInt("product_comment_allstar"));
				ProductVO.setProductSellAllnum(rs.getInt("product_sell_allnum"));
				list.add(ProductVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}

	public static void readPicture(byte[] bytes) throws IOException {
		FileOutputStream fos = new FileOutputStream("C:/example/test3.jpg");
		fos.write(bytes);
		fos.flush();
		fos.close();
	}

	public static void main(String[] args) {

		ProductDAOImpl dao = new ProductDAOImpl();

		// 新增
//		ProductVO ProductVO1 = new ProductVO();
//		ProductVO1.setCompanyId(4);
//		ProductVO1.setProductTypeId(2);
//		ProductVO1.setProductStatus(1);
//		ProductVO1.setProductName("小原木摺疊桌");
//		ProductVO1.setProductPrice(789);
//		ProductVO1.setProductBrand("快樂露營趣");
//		ProductVO1.setProductInventory(10);
//		ProductVO1.setProductDescription("商品尺寸:\r\n" + 
//				"    -展開:61*40*27cm\r\n" + 
//				"    -收納:30*40*4cm\r\n" + 
//				"商品重量:\r\n" + 
//				"    -1.3kg\r\n" + 
//				"商品材質:\r\n" + 
//				"    -鋁合金，MDF竹木紋板");
//		ProductVO1.setShoppingInformation("摺疊桌展開後中間\r\n" + 
//				"稍微突起\r\n" + 
//				"屬正常現象\r\n" + 
//				"為防止桌面放置\r\n" + 
//				"物品下垂所預留\r\n" + 
//				"空間");
//		try {
//		byte[] pic = getPictureByteArray("C:/example/test1.jpg");
//		ProductVO1.setProductPic1(pic);
//		byte[] pic2 = getPictureByteArray("C:/example/test1.jpg");
//		ProductVO1.setProductPic2(pic2);
//		byte[] pic3 = getPictureByteArray("C:/example/test1.jpg");
//		ProductVO1.setProductPic3(pic3);
//		} catch (IOException ie) {
//			System.out.println(ie);
//		}

//		dao.insert(ProductVO1);

//		// 修改
//		ProductVO ProductVO2 = new ProductVO();
//		ProductVO2.setCompanyId(1);
//		ProductVO2.setProductTypeId(1);
//		ProductVO2.setProductStatus(1);
//		ProductVO2.setProductName("大原木方形摺疊桌");
//		ProductVO2.setProductPrice(999);
//		ProductVO2.setProductBrand("露營嗨");
//		ProductVO2.setProductInventory(8);
//		ProductVO2.setProductDescription("修改後");
//		ProductVO2.setShoppingInformation("修改後");
//		try {
//		byte[] pic = getPictureByteArray("C:/example/test2.jpeg");
//		ProductVO2.setProductPic1(pic);
//		byte[] pic2 = getPictureByteArray("C:/example/test2.jpeg");
//		ProductVO2.setProductPic2(pic2);
//		byte[] pic3 = getPictureByteArray("C:/example/test2.jpeg");
//		ProductVO2.setProductPic3(pic3);
//		} catch (IOException ie) {
//			System.out.println(ie);
//		}
//		ProductVO2.setProductCommentedAllnum(1);
//		ProductVO2.setProductCommentAllstar(5);
//		ProductVO2.setProductSellAllnum(2);
//		ProductVO2.setProductId(6);
//		
//		dao.update(ProductVO2);
//
//		// 刪除
//		dao.delete(5);
//
//		// 查詢
		ProductVO ProductVO3 = dao.findByPrimaryKey(6);
		System.out.print(ProductVO3.getProductId() + ",");
		System.out.print(ProductVO3.getCompanyId() + ",");
		System.out.print(ProductVO3.getProductTypeId() + ",");
		System.out.print(ProductVO3.getProductStatus() + ",");
		System.out.print(ProductVO3.getProductName() + ",");
		System.out.print(ProductVO3.getProductPrice() + ",");
		System.out.println(ProductVO3.getProductBrand() + ",");
		System.out.println(ProductVO3.getProductInventory() + ",");
		System.out.println(ProductVO3.getProductDescription() + ",");
		System.out.println(ProductVO3.getShoppingInformation() + ",");
		try {
			readPicture(ProductVO3.getProductPic1());
			readPicture(ProductVO3.getProductPic2());
			readPicture(ProductVO3.getProductPic3());
		} catch (IOException ie) {
			System.out.println(ie);
		}
		System.out.println(ProductVO3.getProductLaunchedTime() + ",");
		System.out.println(ProductVO3.getProductCommentedAllnum() + ",");
		System.out.println(ProductVO3.getProductCommentAllstar() + ",");
		System.out.println(ProductVO3.getProductSellAllnum());
		System.out.println("---------------------");

//		// 查詢
//		List<ProductVO> list = dao.getAll();
//		for (ProductVO ap : list) {
//			System.out.print(ap.getProductId() + ",");
//			System.out.print(ap.getCompanyId() + ",");
//			System.out.print(ap.getProductTypeId() + ",");
//			System.out.print(ap.getProductStatus() + ",");
//			System.out.print(ap.getProductName() + ",");
//			System.out.print(ap.getProductPrice() + ",");
//			System.out.print(ap.getProductBrand() + ",");
//			System.out.print(ap.getProductInventory() + ",");
//			System.out.print(ap.getProductDescription() + ",");
//			System.out.print(ap.getShoppingInformation() + ",");
//			System.out.print(ap.getProductPic1() + ",");
//			System.out.print(ap.getProductPic2() + ",");
//			System.out.print(ap.getProductPic3() + ",");
//			System.out.print(ap.getProductLaunchedTime() + ",");
//			System.out.print(ap.getProductCommentedAllnum() + ",");
//			System.out.print(ap.getProductCommentAllstar() + ",");			
//			System.out.print(ap.getProductSellAllnum());
//			System.out.println();
//		}
	}

	

}
