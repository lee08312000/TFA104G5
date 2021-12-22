package com.member.model;

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

import javax.sql.rowset.serial.SerialBlob;
import util.Util;

public class MemberDAOImpl implements MemberDAO {
	private static final String INSERT_STMT = "INSERT INTO member(member_account_status, member_name, member_account, member_password, member_email, member_address, member_phone, member_pic) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE member SET member_account_status = ?, member_name = ?, member_account = ?, member_password = ?, member_email = ?, member_address = ?, member_phone = ?, member_pic = ? WHERE member_id = ?";
	private static final String DELETE_STMT = "DELETE FROM member WHERE member_id = ?";
	private static final String FIND_BY_PK = "SELECT * FROM member WHERE member_id = ?";
	private static final String GET_ALL = "SELECT * FROM member";
	private static final String LOGIN = "SELECT * FROM member WHERE member_account = ? AND member_password = ?";
	private static final String RESET_PASSWORD_CHECK = "SELECT * FROM member WHERE member_account = ? AND member_email = ?";
	private static final String RESET_PASSWORD = "UPDATE member SET member_account_status = ?, member_name = ?, member_account = ?, member_password = ?, member_email = ?, member_address = ?, member_phone = ?, member_pic = ? WHERE member_password = ?";
	
	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, memberVO.getMemberAccountStatus());
			pstmt.setString(2, memberVO.getMemberName());
			pstmt.setString(3, memberVO.getMemberAccount());
			pstmt.setString(4, memberVO.getMemberPassword());
			pstmt.setString(5, memberVO.getMemberEmail());
			pstmt.setString(6, memberVO.getMemberAddress());
			pstmt.setString(7, memberVO.getMemberPhone());
			pstmt.setBytes(8, memberVO.getMemberPic());

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
	public void update(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, memberVO.getMemberAccountStatus());
			pstmt.setString(2, memberVO.getMemberName());
			pstmt.setString(3, memberVO.getMemberAccount());
			pstmt.setString(4, memberVO.getMemberPassword());
			pstmt.setString(5, memberVO.getMemberEmail());
			pstmt.setString(6, memberVO.getMemberAddress());
			pstmt.setString(7, memberVO.getMemberPhone());
			pstmt.setBytes(8, memberVO.getMemberPic());
			pstmt.setInt(9, memberVO.getMemberId());

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
	public void delete(Integer memberId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, memberId);
			
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
	public MemberVO findByPK(Integer memberId) { //回傳一個員工的物件
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, memberId);
			rs = pstmt.executeQuery(); //select相關用這個

			while (rs.next()) {
				memberVO = new MemberVO(); //物件建立的動作，建立Employee Bean,準備包裝著從資料庫查詢出來的部門資料
				// emp.setEmpno(empno);設定後直接回傳值回去
				memberVO.setMemberId(rs.getInt("member_id"));
				memberVO.setMemberAccountStatus(rs.getInt("member_account_status"));
				memberVO.setMemberName(rs.getString("member_name"));
				memberVO.setMemberAccount(rs.getString("member_account"));
				memberVO.setMemberPassword(rs.getString("member_password"));
				memberVO.setMemberEmail(rs.getString("member_email"));
				memberVO.setMemberAddress(rs.getString("member_address"));
				memberVO.setMemberPhone(rs.getString("member_phone"));
				memberVO.setMemberPic(rs.getBytes("member_pic"));

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

		return memberVO; //不要忘了這個 要替換成迴圈裡的部門物件
	}

	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> memberList = new ArrayList<>();
		MemberVO  memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemberId(rs.getInt("member_id"));
				memberVO.setMemberAccountStatus(rs.getInt("member_account_status"));
				memberVO.setMemberName(rs.getString("member_name"));
				memberVO.setMemberAccount(rs.getString("member_account"));
				memberVO.setMemberPassword(rs.getString("member_password"));
				memberVO.setMemberEmail(rs.getString("member_email"));
				memberVO.setMemberAddress(rs.getString("member_address"));
				memberVO.setMemberPhone(rs.getString("member_phone"));
				memberVO.setMemberPic(rs.getBytes("member_pic"));
				memberList.add(memberVO);
				
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
		return memberList;
	}
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	
	public static void readPicture(byte[] bytes) throws IOException {
		FileOutputStream fos = new FileOutputStream("E:/第五組專案/examplePic/test3.jpg");
		fos.write(bytes);
		fos.flush();
		fos.close();
	}
	
	@SuppressWarnings("resource")
	public MemberVO login(String memberAccount, String memberPassword) {
		MemberVO  memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(LOGIN);
			
			pstmt.setString(1, memberAccount);
			pstmt.setString(2, memberPassword);
			
			rs = pstmt.executeQuery(); //select相關用這個
			
			if (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemberId(rs.getInt("member_id"));
				memberVO.setMemberAccountStatus(rs.getInt("member_account_status"));
				memberVO.setMemberName(rs.getString("member_name"));
				memberVO.setMemberAccount(rs.getString("member_account"));
				memberVO.setMemberPassword(rs.getString("member_password"));
				memberVO.setMemberEmail(rs.getString("member_email"));
				memberVO.setMemberAddress(rs.getString("member_address"));
				memberVO.setMemberPhone(rs.getString("member_phone"));
				memberVO.setMemberPic(rs.getBytes("member_pic"));
				
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
		return memberVO;
	}
	
	@SuppressWarnings("resource")
	public MemberVO resetPasswordCheck(String memberAccount, String memberEmail) {
		MemberVO  memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(RESET_PASSWORD_CHECK);
			
			pstmt.setString(1, memberAccount);
			pstmt.setString(2, memberEmail);
			
			rs = pstmt.executeQuery(); //select相關用這個
			
			if (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemberId(rs.getInt("member_id"));
				memberVO.setMemberAccountStatus(rs.getInt("member_account_status"));
				memberVO.setMemberName(rs.getString("member_name"));
				memberVO.setMemberAccount(rs.getString("member_account"));
				memberVO.setMemberPassword(rs.getString("member_password"));
				memberVO.setMemberEmail(rs.getString("member_email"));
				memberVO.setMemberAddress(rs.getString("member_address"));
				memberVO.setMemberPhone(rs.getString("member_phone"));
				memberVO.setMemberPic(rs.getBytes("member_pic"));
				
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
		return memberVO;
	}
	
//	目前把密碼並到修改會員資料中，不須另寫方法，但先放著
//	@SuppressWarnings("resource")
//	public MemberVO resetPassword(MemberVO memberVO) {
//		MemberVO  memberVO1 = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
//			pstmt = con.prepareStatement(RESET_PASSWORD);
//			
//			pstmt.setString(1, memberPassword);
//			
//			rs = pstmt.executeQuery(); //select相關用這個
//			
//			if (rs.next()) {
//				memberVO1 = new MemberVO();
//				memberVO1.setMemberId(rs.getInt("member_id"));
//				memberVO1.setMemberAccountStatus(rs.getInt("member_account_status"));
//				memberVO1.setMemberName(rs.getString("member_name"));
//				memberVO1.setMemberAccount(rs.getString("member_account"));
//				memberVO1.setMemberPassword(rs.getString("member_password"));
//				memberVO1.setMemberEmail(rs.getString("member_email"));
//				memberVO1.setMemberAddress(rs.getString("member_address"));
//				memberVO1.setMemberPhone(rs.getString("member_phone"));
//				memberVO1.setMemberPic(rs.getBytes("member_pic"));
//				
//			}
//			
//		} catch (SQLException se) {
//			se.printStackTrace();
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return memberVO1;
//	}
	
	public static void main(String[] args) {

	MemberDAOImpl dao = new MemberDAOImpl();
	
//	// 新增
//	MemberVO memberVO01 = new MemberVO();
//	memberVO01.setMemberAccountStatus(1);
//	memberVO01.setMemberName("David");
//	memberVO01.setMemberAccount("Good company");
//	memberVO01.setMemberPassword("a123456789");
//	memberVO01.setMemberEmail("123456");
//	memberVO01.setMemberAddress("a123456789@gmail.com");
//	memberVO01.setMemberPhone("123456789");
//
//	try {
//		byte[] pic = getPictureByteArray("E:/第五組專案/examplePic/1.jpg");	
//		memberVO01.setMemberPic(pic);
//	} catch (IOException e) {
//		System.out.print(e);
//	}
//	
//	dao.add(memberVO01);
//	
//	//更新
//	MemberVO memberVO02 = new MemberVO();
//	memberVO02.setMemberAccountStatus(1);
//	memberVO02.setMemberName("Kevin");
//	memberVO02.setMemberAccount("bad company");
//	memberVO02.setMemberPassword("b987654321");
//	memberVO02.setMemberEmail("654321");
//	memberVO02.setMemberAddress("b987654321@gmail.com");
//	memberVO02.setMemberPhone("987654321");
//	memberVO02.setMemberId(1);
//
//	try {
//		byte[] pic = getPictureByteArray("E:\\第五組專案\\examplePic\\2.jpg");	
//		memberVO02.setMemberPic(pic);
//	} catch (IOException e) {
//		System.out.print(e);
//	}
//		
//	dao.update(memberVO02);
//	
//	//刪除
//	dao.delete(4);
//	
//	//找尋
//	MemberVO memberVO03 = dao.findByPK(7);
//	System.out.print(memberVO03.getMemberId() + ",");
//	System.out.print(memberVO03.getMemberAccountStatus() + ",");
//	System.out.print(memberVO03.getMemberName() + ",");
//	System.out.print(memberVO03.getMemberAccount() + ",");
//	System.out.print(memberVO03.getMemberPassword() + ",");
//	System.out.print(memberVO03.getMemberEmail() + ",");
//	System.out.print(memberVO03.getMemberAddress() + ",");
//	System.out.print(memberVO03.getMemberPhone() + ",");
//	System.out.print(memberVO03.getMemberPic());
//	System.out.println();
//		try {
//		readPicture(memberVO03.getMemberPic());
//	} catch (IOException e) {
//		System.out.print(e);
//	}			
//	
//	//查詢全部
//	List<MemberVO> list = dao.getAll();
//	
//	for (MemberVO test : list) {
//		System.out.print(test.getMemberId() + ",");
//		System.out.print(test.getMemberAccountStatus() + ",");
//		System.out.print(test.getMemberName() + ",");
//		System.out.print(test.getMemberAccount() + ",");
//		System.out.print(test.getMemberPassword() + ",");
//		System.out.print(test.getMemberEmail() + ",");
//		System.out.print(test.getMemberAddress() + ",");
//		System.out.print(test.getMemberPhone() + ",");
//		System.out.print(test.getMemberPic() + ",");
//		System.out.println();
//	}

	}

}
