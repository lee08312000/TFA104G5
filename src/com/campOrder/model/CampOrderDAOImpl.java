package com.campOrder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.campAreaOrderDetail.model.CampAreaOrderDetailVO;
import com.campBooking.model.CampBookingDAO;
import com.campBooking.model.CampBookingDAOImpl;
import com.campBooking.model.CampBookingVO;

import util.DiffDays;
import util.Util;

public class CampOrderDAOImpl implements CampOrderDAO {
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/campingParadise?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";
	
	private static final String INSERTORDER_STMT = "INSERT INTO camp_order (camp_id, member_id, camp_order_total_amount, camp_check_out_date, camp_check_in_date, credit_card_num,payer_name,payer_phone) values(?,?,?,?,?,?,?,?)";
	private static final String INSERTDETAIL_STMT = "INSERT INTO camp_area_order_detail(camp_area_id,camp_order_id,booking_quantity,camp_area_weekday_price,camp_area_holiday_price,capitation_quantity,per_capitation_fee,booking_weekdays,booking_holidays) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String UPDATEBOOKING_STMT = "UPDATE camp_booking SET booked_camp_area_num=? where  camp_area_id=?  and date=?";

	private static final String UPDATE_STMT = "UPDATE camp_order SET camp_id = ?, member_id = ?, camp_order_status = ?, camp_order_total_amount = ?, camp_check_out_date= ?, camp_check_in_date = ? ,credit_card_num= ? ,payer_name= ? ,payer_phone=?,camp_order_confirmed_time=?,camp_order_completed_time=?,camp_comment_star=?,camp_comment=?,camp_order_comment_time=? WHERE camp_order_id = ?";

	private static final String DELETE_ORDER = "DELETE FROM camp_order WHERE camp_order_id = ?";
	private static final String DELETE_ORDERDETAIL = "DELETE FROM camp_order_detail WHERE camp_order_id = ?";

	private static final String FIND_BY_PK = "SELECT * FROM camp_order WHERE camp_order_id= ?";

	private static final String GET_ALL = "SELECT * FROM camp_order";


	//12/22營地評價查詢
	private static final String  SELECT_STMT_CAMP_COMMENT=  "SELECT camp_order_id,member_id,camp_comment,camp_comment_star,camp_order_comment_time"
			+ " FROM camp_order where 1 = 1 and camp_order_comment_time  between ? and ?";
		

	private static final String GET_ALL2 = "SELECT * FROM camp_order order by ";

	private static final String FIND_HOTCAMP = "SELECT camp_id,(sum(camp_comment_star)/count(*)) as 'avg_star',count(*) as 'compl_ordernum' FROM campingParadise.camp_order where camp_order_completed_time is not null group by camp_id order by compl_ordernum desc,avg_star desc";

	private static final String FIND_BY_PARAMS = "SELECT * FROM camp_order where  camp_order_confirmed_time >=? and camp_order_confirmed_time <= ? and camp_order_status=? ";
	
	private static final String FIND_BY_ORDER="select ce.* , ch.capitation_Quantity,ch.booking_Weekdays,ch.booking_Holidays, cf.camp_name, cg.member_Account,ch.booking_quantity" + 
			" from  campingParadise.camp_order ce " + 
			" join campingParadise.camp_area_order_detail ch on ch.camp_order_id=ce.camp_order_id" + 
			" join campingParadise.member cg on cg.member_id=ce.member_id" + 
			" join  campingParadise.camp cf on cf.camp_id=ce.camp_id  where  ce.camp_order_confirmed_time >=? and ce.camp_order_confirmed_time <= ?  and ce.camp_order_status=? ";
	
	private static final String FIND_BY_ORDER_NO_STATUS="select ce.* , ch.capitation_Quantity,ch.booking_Weekdays,ch.booking_Holidays, cf.camp_name, cg.member_Account,ch.booking_quantity" + 
			" from  campingParadise.camp_order ce " + 
			" join campingParadise.camp_area_order_detail ch on ch.camp_order_id=ce.camp_order_id" + 
			" join campingParadise.member cg on cg.member_id=ce.member_id" + 
			" join  campingParadise.camp cf on cf.camp_id=ce.camp_id  where  ce.camp_order_confirmed_time >=? and ce.camp_order_confirmed_time <= ?  ";
	
	
	private static final String FIND_BY_PARAMS_NO_STATUS = "SELECT * FROM camp_order where  camp_order_confirmed_time >=? and camp_order_confirmed_time <= ? ";

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/David");
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int add(CampOrderVO campOrderVO, List<CampAreaOrderDetailVO> orderdetailList) {
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		int mainkey = 0;
		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			String col[]= {"camp_order_id"};
			pstmt1 = con.prepareStatement(INSERTORDER_STMT,col);
			con.setAutoCommit(false);
//新增訂單
			pstmt1.setInt(1, campOrderVO.getCampId());
			pstmt1.setInt(2, campOrderVO.getMemberId());
			pstmt1.setInt(3, campOrderVO.getCampOrderTotalAmount());
			pstmt1.setDate(4, campOrderVO.getCampCheckOutDate());
			pstmt1.setDate(5, campOrderVO.getCampCheckInDate());
			pstmt1.setString(6, campOrderVO.getCreditCardNum());
			pstmt1.setString(7, campOrderVO.getPayerName());
			pstmt1.setString(8, campOrderVO.getPayerPhone());

			pstmt1.executeUpdate();
//取得自增主鍵
			
			ResultSet rs = pstmt1.getGeneratedKeys();
			if (rs.next()) {
				mainkey = rs.getInt(1);
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
System.out.println("1mainkey="+mainkey);
//新增訂單明細

			pstmt2 = con.prepareStatement(INSERTDETAIL_STMT);
			for (int i = 0; i < orderdetailList.size(); i++) {
				pstmt2.setInt(1, orderdetailList.get(i).getCampAreaId());
				pstmt2.setInt(2, mainkey);
				pstmt2.setInt(3, orderdetailList.get(i).getBookingQuantity());
				pstmt2.setInt(4, orderdetailList.get(i).getCampAreaWeekdayPrice());
				pstmt2.setInt(5, orderdetailList.get(i).getCampAreaHolidayPrice());
				pstmt2.setInt(6, orderdetailList.get(i).getCapitationQuantity());
				pstmt2.setInt(7, orderdetailList.get(i).getPerCapitationFee());
				pstmt2.setInt(8, orderdetailList.get(i).getBookingWeekdays());
				pstmt2.setInt(9, orderdetailList.get(i).getBookingHolidays());
				pstmt2.addBatch();
			}
			pstmt2.executeBatch();

//新增日程表訂位表格，針對每個營地每個營位每個日期做訂位數量修改，判斷當天剩餘空位是否大於訂位帳數
			pstmt3 = con.prepareStatement(UPDATEBOOKING_STMT);
			java.util.Date checkin = new java.util.Date(campOrderVO.getCampCheckInDate().getTime());
			java.util.Date checkout = new java.util.Date(campOrderVO.getCampCheckOutDate().getTime());
			List<java.util.Date> list = DiffDays.getDates(checkin, checkout);

			
			
			

			CampBookingDAO bookdao = new CampBookingDAOImpl();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < orderdetailList.size(); i++) {
				for (int j=0;j<list.size()-1;j++) {
					
					CampBookingVO target = bookdao.findByOneArea(orderdetailList.get(i).getCampAreaId(),
							sdf.format(list.get(j)));
					

					// 這個營位的這一天剩餘空位數
					int lastAreaNum = target.getBookingCampAreaMax() - target.getBookedCampAreaNum();
System.out.println(sdf.format(list.get(j))+","+"剩餘空位"+lastAreaNum+","+"已目前數量"+target.getBookedCampAreaNum()+","+"想要預定數量"+orderdetailList.get(i).getBookingQuantity());


					if (orderdetailList.get(i).getBookingQuantity() <= lastAreaNum) {
						pstmt3.setInt(1, orderdetailList.get(i).getBookingQuantity()+target.getBookedCampAreaNum());
						pstmt3.setInt(2, orderdetailList.get(i).getCampAreaId());
						pstmt3.setDate(3, new java.sql.Date(list.get(j).getTime()));

						pstmt3.addBatch();

					} else {
						throw new Exception();
					}

				}
			}
			pstmt3.executeBatch();
			
			con.commit();
			System.out.println("新增訂單成功");

		} catch (Exception se) {
			try {
				System.out.println("模型發生錯誤");
				con.rollback();
				mainkey=0;
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			se.printStackTrace();

		} finally {
			if (pstmt3 != null) {
				try {
					pstmt3.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt1 != null) {
				try {
					pstmt1.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
					return mainkey;
					
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
		}
		return mainkey;

	}

	@Override
	public void update(CampOrderVO campOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, campOrderVO.getCampId());
			pstmt.setInt(2, campOrderVO.getMemberId());
			pstmt.setInt(3, campOrderVO.getCampOrderStatus());
			pstmt.setInt(4, campOrderVO.getCampOrderTotalAmount());
			pstmt.setDate(5, campOrderVO.getCampCheckOutDate());
			pstmt.setDate(6, campOrderVO.getCampCheckInDate());
			pstmt.setString(7, campOrderVO.getCreditCardNum());
			pstmt.setString(8, campOrderVO.getPayerName());
			pstmt.setString(9, campOrderVO.getPayerPhone());
			pstmt.setTimestamp(10, campOrderVO.getCampOrderConfirmedTime());
			pstmt.setTimestamp(11, campOrderVO.getCampOrderCompletedTime());
			pstmt.setInt(12, campOrderVO.getCampCommentStar());
			pstmt.setString(13, campOrderVO.getCampComment());
			pstmt.setTimestamp(14, campOrderVO.getCampOrderCommentTime());
			pstmt.setInt(15, campOrderVO.getCampOrderId());

			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (SQLException | ClassNotFoundException se) {
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
	public void delete(Integer campOrderId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_ORDERDETAIL);
			pstmt.setInt(1, campOrderId);
			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ORDER);
			pstmt.setInt(1, campOrderId);
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
	public CampOrderVO findByPK(Integer campOrderId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampOrderVO campOrderVO = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, campOrderId);
			rs = pstmt.executeQuery();
			campOrderVO = new CampOrderVO();
			while (rs.next()) {
				campOrderVO.setCampOrderId(rs.getInt(1));
				campOrderVO.setCampId(rs.getInt(2));
				campOrderVO.setMemberId(rs.getInt(3));
				campOrderVO.setCampOrderStatus(rs.getInt(4));
				campOrderVO.setCampOrderTotalAmount(rs.getInt(5));
				campOrderVO.setCampCheckOutDate(rs.getDate(6));
				campOrderVO.setCampCheckInDate(rs.getDate(7));
				campOrderVO.setCreditCardNum(rs.getString(8));
				campOrderVO.setPayerName(rs.getString(9));
				campOrderVO.setPayerPhone(rs.getString(10));
				campOrderVO.setCampOrderConfirmedTime(rs.getTimestamp(11));
				campOrderVO.setCampOrderCompletedTime(rs.getTimestamp(12));
				campOrderVO.setCampCommentStar(rs.getInt(13));
				campOrderVO.setCampComment(rs.getString(14));
				campOrderVO.setCampOrderCommentTime(rs.getTimestamp(15));
			}
		} catch (SQLException | ClassNotFoundException se) {
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

		return campOrderVO;
	}

	@Override
	public List<CampOrderVO> findByParams(int statusnum, Date startDate, Date endDate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CampOrderVO> list = new ArrayList<>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			String sql;
			if (statusnum == -1) {
				sql = FIND_BY_ORDER_NO_STATUS;		
				pstmt = con.prepareStatement(sql);	
			}else {
				pstmt = con.prepareStatement(FIND_BY_ORDER );	
			}								
			java.sql.Date startSqlDate = new java.sql.Date(startDate.getTime());
			pstmt.setDate(1, startSqlDate);
			java.sql.Date endSqlDate = new java.sql.Date(endDate.getTime());
			pstmt.setDate(2, endSqlDate);
			if (statusnum != -1) {
				pstmt.setInt(3, statusnum);
			}

			CampOrderVO campOrderVO = null;
			rs = pstmt.executeQuery();

			while (rs.next()) {
				campOrderVO = new CampOrderVO();
				campOrderVO.setCampOrderId(rs.getInt(1));
				campOrderVO.setCampId(rs.getInt(2));
				campOrderVO.setMemberId(rs.getInt(3));
				campOrderVO.setCampOrderStatus(rs.getInt(4));
				campOrderVO.setCampOrderTotalAmount(rs.getInt(5));
				campOrderVO.setCampCheckOutDate(rs.getDate(6));
				campOrderVO.setCampCheckInDate(rs.getDate(7));
				campOrderVO.setCreditCardNum(rs.getString(8));
				campOrderVO.setPayerName(rs.getString(9));
				campOrderVO.setPayerPhone(rs.getString(10));
				campOrderVO.setCampOrderConfirmedTime(rs.getTimestamp(11));
				campOrderVO.setCampOrderCompletedTime(rs.getTimestamp(12));
				campOrderVO.setCampCommentStar(rs.getInt(13));
				campOrderVO.setCampComment(rs.getString(14));
				campOrderVO.setCampOrderCommentTime(rs.getTimestamp(15));
				campOrderVO.setCapitationQuantity(rs.getInt("ch.capitation_Quantity"));
				campOrderVO.setBookingWeekdays(rs.getInt("ch.booking_Weekdays"));
				campOrderVO.setBookingHolidays(rs.getInt("ch.booking_Holidays"));
				campOrderVO.setCampName(rs.getString("cf.camp_name"));
				campOrderVO.setMemberAccount(rs.getString("cg.member_Account"));
				campOrderVO.setBookingQuantity(rs.getInt("ch.booking_quantity"));
				list.add(campOrderVO);
			}
		} catch (SQLException | ClassNotFoundException se) {
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

		return list;
	}

	@Override
	public List<CampOrderVO> getAll(Integer... sortmethed) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampOrderVO campOrderVO = null;
		List<CampOrderVO> list = new ArrayList<>();
		StringBuffer buf = new StringBuffer();
		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			if (sortmethed.length != 0) {
				for (Integer ordernumber : sortmethed) {
					switch (ordernumber) {
					case 0:
						buf.append("camp_order_id,");
						break;
					case 1:
						buf.append("camp_id,");
						break;
					case 2:
						buf.append("camp_order_status,");
						break;
					case 3:
						buf.append("camp_check_out_date desc,");
						break;
					case 4:
						buf.append("camp_check_in_date desc,");
						break;
					case 5:
						buf.append("camp_order_comment_time desc,");
						break;
					default:
						buf.append("camp_order_id,");
					}

				}
			} else {
				buf.append("camp_order_id,");
			}

			String sorted = buf.toString().substring(0, buf.length() - 1);

			pstmt = con.prepareStatement(GET_ALL2 + "" + sorted);
System.out.println(GET_ALL2 + "" + sorted);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				campOrderVO = new CampOrderVO();
				campOrderVO.setCampOrderId(rs.getInt(1));
				campOrderVO.setCampId(rs.getInt(2));
				campOrderVO.setMemberId(rs.getInt(3));
				campOrderVO.setCampOrderStatus(rs.getInt(4));
				campOrderVO.setCampOrderTotalAmount(rs.getInt(5));
				campOrderVO.setCampCheckOutDate(rs.getDate(6));
				campOrderVO.setCampCheckInDate(rs.getDate(7));
				campOrderVO.setCreditCardNum(rs.getString(8));
				campOrderVO.setPayerName(rs.getString(9));
				campOrderVO.setPayerPhone(rs.getString(10));
				campOrderVO.setCampOrderConfirmedTime(rs.getTimestamp(11));
				campOrderVO.setCampOrderCompletedTime(rs.getTimestamp(12));
				campOrderVO.setCampCommentStar(rs.getInt(13));
				campOrderVO.setCampComment(rs.getString(14));
				campOrderVO.setCampOrderCommentTime(rs.getTimestamp(15));
				list.add(campOrderVO);
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

		return list;
	}

	@Override
	public List<Integer> findhotcamp() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> list = new ArrayList<Integer>();

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(FIND_HOTCAMP);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Integer campId = rs.getInt(1);
				list.add(campId);
			}
		} catch (SQLException se) {
			se.printStackTrace();
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
	public List<CampOrderVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampOrderVO campOrderVO = null;
		List<CampOrderVO> list = new ArrayList<>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				campOrderVO = new CampOrderVO();
				campOrderVO.setCampOrderId(rs.getInt(1));
				campOrderVO.setCampId(rs.getInt(2));
				campOrderVO.setMemberId(rs.getInt(3));
				campOrderVO.setCampOrderStatus(rs.getInt(4));
				campOrderVO.setCampOrderTotalAmount(rs.getInt(5));
				campOrderVO.setCampCheckOutDate(rs.getDate(6));
				campOrderVO.setCampCheckInDate(rs.getDate(7));
				campOrderVO.setCreditCardNum(rs.getString(8));
				campOrderVO.setPayerName(rs.getString(9));
				campOrderVO.setPayerPhone(rs.getString(10));
				campOrderVO.setCampOrderConfirmedTime(rs.getTimestamp(11));
				campOrderVO.setCampOrderCompletedTime(rs.getTimestamp(12));
				campOrderVO.setCampCommentStar(rs.getInt(13));
				campOrderVO.setCampComment(rs.getString(14));
				campOrderVO.setCampOrderCommentTime(rs.getTimestamp(15));
				list.add(campOrderVO);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
	public List<CampOrderVO> selectCampComment(Timestamp startDateTimestamp, Timestamp endDateTimestamp,
			Integer campOrder) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampOrderVO campOrderVO = null;
		List<CampOrderVO> listcomment = new ArrayList<>();
		try {
			
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);				
			String sql = SELECT_STMT_CAMP_COMMENT;

				
			if(campOrder != -1) {
				sql = SELECT_STMT_CAMP_COMMENT+ " and camp_order_id like '%' ? '%'";
			}
			pstmt = con.prepareStatement(SELECT_STMT_CAMP_COMMENT);
			pstmt.setTimestamp(1,startDateTimestamp);
			pstmt.setTimestamp(2,endDateTimestamp);
			if(campOrder != -1) {
				pstmt.setInt(3, campOrder);
			}
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				campOrderVO = new CampOrderVO();
				//camp_order_id,member_id,camp_comment,camp_comment_star,camp_order_comment_time
				campOrderVO.setCampOrderId(rs.getInt(1));
				campOrderVO.setMemberId(rs.getInt(2));
				campOrderVO.setCampComment(rs.getString(3));
				campOrderVO.setCampCommentStar(rs.getInt(4));
				campOrderVO.setCampOrderCommentTime(rs.getTimestamp(5));
				listcomment.add(campOrderVO);
				
			}
		} catch (SQLException | ClassNotFoundException se) {
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

		return listcomment;
	}
}

