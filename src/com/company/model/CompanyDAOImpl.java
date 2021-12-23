package com.company.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;

import util.Util;

public class CompanyDAOImpl implements CompanyDAO {

	
	private static final String INSERT_STMT = "INSERT INTO company(company_status, head_name, company_name, company_account, company_password, company_email, company_tel, company_bank_account, company_address, company_register_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE company SET company_status = ?, head_name = ?, company_name = ?, company_account = ?, company_password = ?, company_email = ?, company_tel = ?, company_bank_account = ?, company_address = ?, company_register_time = ? WHERE company_id = ?";
	private static final String DELETE_STMT = "DELETE FROM company WHERE company_id = ?";
	private static final String FIND_BY_PK = "SELECT * FROM company WHERE company_id = ?";
	
	private static final String GET_ALL = "SELECT * FROM company";
	private static final String FIND_BY_ACCOUNT_PASSWORD = "SELECT * FROM company WHERE company_account = ? AND company_password = ?";
	private static final String FIND_BY_ACCOUNT = "SELECT * FROM company WHERE company_account = ?";
	// Handle any driver errors
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	public void add(CompanyVO companyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Integer companyId = -1 ;
		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, companyVO.getCompanyStatus());
			pstmt.setString(2, companyVO.getHeadName());
			pstmt.setString(3, companyVO.getCompanyName());
			pstmt.setString(4, companyVO.getCompanyAccount());
			pstmt.setString(5, companyVO.getCompanyPassword());
			pstmt.setString(6, companyVO.getCompanyEmail());
			pstmt.setString(7, companyVO.getCompanyTel());
			pstmt.setString(8, companyVO.getCompanyBankAccount());
			pstmt.setString(9, companyVO.getCompanyAddress());
			pstmt.setTimestamp(10, companyVO.getCompanyRegisterTime());

			pstmt.executeUpdate();
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
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
	public void update(CompanyVO companyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, companyVO.getCompanyStatus());
			pstmt.setString(2, companyVO.getHeadName());
			pstmt.setString(3, companyVO.getCompanyName());
			pstmt.setString(4, companyVO.getCompanyAccount());
			pstmt.setString(5, companyVO.getCompanyPassword());
			pstmt.setString(6, companyVO.getCompanyEmail());
			pstmt.setString(7, companyVO.getCompanyTel());
			pstmt.setString(8, companyVO.getCompanyBankAccount());
			pstmt.setString(9, companyVO.getCompanyAddress());
			pstmt.setTimestamp(10, companyVO.getCompanyRegisterTime());
			pstmt.setInt(11, companyVO.getCompanyId());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
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
	public void delete(Integer companyId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, companyId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
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
	public CompanyVO findByPK(Integer companyId) { // 回傳一個員工的物件
		CompanyVO companyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, companyId);
			rs = pstmt.executeQuery(); // select相關用這個

			while (rs.next()) {
				companyVO = new CompanyVO(); // 物件建立的動作，建立Employee Bean,準備包裝著從資料庫查詢出來的部門資料
				// emp.setEmpno(empno);設定後直接回傳值回去
				companyVO.setCompanyId(rs.getInt("company_id"));
				companyVO.setCompanyStatus(rs.getInt("company_status"));
				companyVO.setHeadName(rs.getString("head_name"));
				companyVO.setCompanyName(rs.getString("company_name"));
				companyVO.setCompanyAccount(rs.getString("company_account"));
				companyVO.setCompanyPassword(rs.getString("company_password"));
				companyVO.setCompanyEmail(rs.getString("company_email"));
				companyVO.setCompanyTel(rs.getString("company_tel"));
				companyVO.setCompanyBankAccount(rs.getString("company_bank_account"));
				companyVO.setCompanyAddress(rs.getString("company_address"));
				companyVO.setCompanyRegisterTime(rs.getTimestamp("company_register_time"));
			}

		} catch (SQLException se) {
			se.printStackTrace();
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

		return companyVO; // 不要忘了這個 要替換成迴圈裡的部門物件
	}

	@Override
	public List<CompanyVO> getAll() {
		List<CompanyVO> companyList = new ArrayList<>();
		CompanyVO companyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				companyVO = new CompanyVO();
				companyVO.setCompanyId(rs.getInt("company_id"));
				companyVO.setCompanyStatus(rs.getInt("company_status"));
				companyVO.setHeadName(rs.getString("head_name"));
				companyVO.setCompanyName(rs.getString("company_name"));
				companyVO.setCompanyAccount(rs.getString("company_account"));
				companyVO.setCompanyPassword(rs.getString("company_password"));
				companyVO.setCompanyEmail(rs.getString("company_email"));
				companyVO.setCompanyTel(rs.getString("company_tel"));
				companyVO.setCompanyBankAccount(rs.getString("company_bank_account"));
				companyVO.setCompanyAddress(rs.getString("company_address"));
				companyVO.setCompanyRegisterTime(rs.getTimestamp("company_register_time"));
				companyList.add(companyVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
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
		return companyList;
	}

	// ginny新增
	@Override
	public CompanyVO findByAccountPassword(String companyAccount, String companyPassword) {
		CompanyVO companyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_ACCOUNT_PASSWORD);
			pstmt.setString(1, companyAccount);
			pstmt.setString(2, companyPassword);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				companyVO = new CompanyVO();

				companyVO.setCompanyId(rs.getInt("company_id"));
				companyVO.setCompanyStatus(rs.getInt("company_status"));
				companyVO.setHeadName(rs.getString("head_name"));
				companyVO.setCompanyName(rs.getString("company_name"));
				companyVO.setCompanyAccount(rs.getString("company_account"));
				companyVO.setCompanyPassword(rs.getString("company_password"));
				companyVO.setCompanyEmail(rs.getString("company_email"));
				companyVO.setCompanyTel(rs.getString("company_tel"));
				companyVO.setCompanyBankAccount(rs.getString("company_bank_account"));
				companyVO.setCompanyAddress(rs.getString("company_address"));
				companyVO.setCompanyRegisterTime(rs.getTimestamp("company_register_time"));
			}

		} catch (SQLException se) {
			se.printStackTrace();
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

		return companyVO;
	}

	@Override
	public CompanyVO findByAccount(String companyAccount) {
		CompanyVO companyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_ACCOUNT);
			pstmt.setString(1, companyAccount);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				companyVO = new CompanyVO();

				companyVO.setCompanyId(rs.getInt("company_id"));
				companyVO.setCompanyStatus(rs.getInt("company_status"));
				companyVO.setHeadName(rs.getString("head_name"));
				companyVO.setCompanyName(rs.getString("company_name"));
				companyVO.setCompanyAccount(rs.getString("company_account"));
				companyVO.setCompanyPassword(rs.getString("company_password"));
				companyVO.setCompanyEmail(rs.getString("company_email"));
				companyVO.setCompanyTel(rs.getString("company_tel"));
				companyVO.setCompanyBankAccount(rs.getString("company_bank_account"));
				companyVO.setCompanyAddress(rs.getString("company_address"));
				companyVO.setCompanyRegisterTime(rs.getTimestamp("company_register_time"));
			}

		} catch (SQLException se) {
			se.printStackTrace();
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

		return companyVO;
	}

//	public static void main(String[] args) {
//	
//	CompanyDAOImpl dao = new CompanyDAOImpl();
//	
//	// 新增
//	CompanyVO companyVO01 = new CompanyVO();
//	companyVO01.setCompanyId(5);
//	companyVO01.setCompanyStatus(1);
//	companyVO01.setHeadName("David");
//	companyVO01.setCompanyName("Good company");
//	companyVO01.setCompanyAccount("a123456789");
//	companyVO01.setCompanyPassword("123456");
//	companyVO01.setCompanyEmail("a123456789@gmail.com");
//	companyVO01.setCompanyTel("123456789");
//	companyVO01.setCompanyBankAccount("123456789");
//	companyVO01.setCompanyAddress("台北市忠孝路一段1號1樓");
//	companyVO01.setCompanyRegisterTime(new Timestamp(System.currentTimeMillis()));
//	
//	dao.add(companyVO01);

	// 更新
//	CompanyVO companyVO02 = new CompanyVO();
//	companyVO02.setCompanyStatus(1);
//	companyVO02.setHeadName("Kevin");
//	companyVO02.setCompanyName("bad company");
//	companyVO02.setCompanyAccount("b987654321");
//	companyVO02.setCompanyPassword("654321");
//	companyVO02.setCompanyEmail("b987654321@gmail.com");
//	companyVO02.setCompanyTel("987654321");
//	companyVO02.setCompanyBankAccount("123-45-678901-2");
//	companyVO02.setCompanyAddress("台北市仁愛路二段2號2樓");
//	companyVO02.setCompanyRegisterTime(new Timestamp(System.currentTimeMillis()));
//	companyVO02.setCompanyId(1);
//	
//	dao.update(companyVO02);
//	
//	//刪除
//	dao.delete(2);
//	
//	//找尋
//	CompanyVO companyVO03 = dao.findByPK(3);
//	System.out.print(companyVO03.getCompanyId() + ",");
//	System.out.print(companyVO03.getCompanyStatus() + ",");
//	System.out.print(companyVO03.getHeadName() + ",");
//	System.out.print(companyVO03.getCompanyName() + ",");
//	System.out.print(companyVO03.getCompanyAccount() + ",");
//	System.out.print(companyVO03.getCompanyPassword() + ",");
//	System.out.print(companyVO03.getCompanyEmail() + ",");
//	System.out.print(companyVO03.getCompanyTel() + ",");
//	System.out.print(companyVO03.getCompanyBankAccount() + ",");
//	System.out.print(companyVO03.getCompanyAddress() + ",");
//	System.out.print(companyVO03.getCompanyRegisterTime());
//	
//	//查詢全部
//	List<CompanyVO> list = dao.getAll();
//	
//	for (CompanyVO test : list) {
//		System.out.print(test.getCompanyId() + ",");
//		System.out.print(test.getCompanyStatus() + ",");
//		System.out.print(test.getHeadName() + ",");
//		System.out.print(test.getCompanyName() + ",");
//		System.out.print(test.getCompanyAccount() + ",");
//		System.out.print(test.getCompanyPassword() + ",");
//		System.out.print(test.getCompanyEmail() + ",");
//		System.out.print(test.getCompanyTel() + ",");
//		System.out.print(test.getCompanyBankAccount() + ",");
//		System.out.print(test.getCompanyAddress() + ",");
//		System.out.print(test.getCompanyRegisterTime());
//		System.out.println();
//	}
//
//	}

}
