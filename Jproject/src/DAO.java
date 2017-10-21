import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class DAO {

	private Connection con = null;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;
	private FinalVO fvo = null;
	private ArrayList<FinalVO> fvoList = null;
	private Control cont = null;
	private ArrayList<OrderList> ol;

	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	private String user = "kang5064";
	private String pw = "kang5064";

	// DB 연동
	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection(url, user, pw);
	}

	public void update(String string, int num) {// 주문자, 결제자 결정해서 member테이블의 상태를
												// 업데이트

		try {
			getConnection();

			String sql = "update member set userstate = ? where username = ?";
			psmt = con.prepareStatement(sql);
			psmt.setString(2, string);
			psmt.setInt(1, num);

			psmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void userInsert(String userID, String password, String name, String phoneNum) {// 회원가입시
																							// 유저정보
																							// 저장.

		try {

			getConnection();

			// DB에 내용입력
			String sql = "insert into member values(?,?,?,?,null,0)";

			psmt = con.prepareStatement(sql);
			psmt.setString(1, userID);
			psmt.setString(2, password);
			psmt.setString(3, name);
			psmt.setString(4, phoneNum);
			psmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {// 프로그램 실행시 오류가 나도 무조건 실행할 코드를 입력
			try {// 프로그램을 닫다가 오류가 날수 있으므로 try/catch로 묶어줌
					// 닫을 때는 실행한 프로그램과 반대로 닫아주어야 한다.
					// ex> con열고 psmt열었으면 먼저 psmt닫고 con닫는다.

				// nullPointException 처리
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getPhoneNum(int num) {// 상태가 num인 사람의 번호 출력

		String phoneNum = "";
		try {
			getConnection();

			String sql = "select userphonenum from member where userstate= ?";
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, num);
			rs = psmt.executeQuery();
			while (rs.next()) {
				phoneNum = rs.getString(1);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return phoneNum;
	}

	public void resetState() { // 상태 0 초기화

		try {

			getConnection();

			String sql = "update member set userstate = 0";
			psmt = con.prepareStatement(sql);

			psmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void insertOrder(OrderList ol) { // 주문시 주문자의 정보와 메뉴정보를 DB에 저장
		/*
		 * oder 테이블에 주문정보 저장 member 테이블에 접근해서 userstate를 1로 바꿈
		 */

		this.ol.add(ol);
		try {

			getConnection();

			String sql = "insert into oder values(?,?,?,?,?,?)";
			psmt = con.prepareStatement(sql);

			psmt.setString(1, ol.getDate());
			psmt.setString(2, ol.getMartName());
			psmt.setString(3, ol.getFoodname());
			psmt.setInt(4, ol.getFoodprice());
			psmt.setString(5, ol.getName());
			psmt.setString(6, ol.getUserID());
			psmt.executeUpdate();

			sql = "update member set userstate =? where userid =?";

			psmt = con.prepareStatement(sql);
			psmt.setInt(1, 1);
			psmt.setString(2, ol.getUserID());

			psmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {// 프로그램 실행시 오류가 나도 무조건 실행할 코드를 입력
			try {// 프로그램을 닫다가 오류가 날수 있으므로 try/catch로 묶어줌
					// 닫을 때는 실행한 프로그램과 반대로 닫아주어야 한다.
					// ex> con열고 psmt열었으면 먼저 psmt닫고 con닫는다.

				// nullPointException 처리
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public LoginState start(String userID) {// 로그인했을때 이름과 아이디를 가지고 시작

		LoginState ls = null;
		try {
			getConnection();

			String sql = "select username from member where userID= ?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, userID);
			psmt.executeQuery();

			String name = rs.getString(1);
			ls = new LoginState();
			ls.setName(name);
			ls.setUserID(userID);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ls;
	}

	public Vector todayOrderList(String martname, String dayTime) {// 오늘의 주문의
																	// 메뉴이름과 메뉴
																	// 개수 반화 (O)
		Vector list = new Vector<>();
		Vector row1 = null;
		int totalPrice = 0;
		int count = 0;
		ArrayList<String> menuName = new ArrayList<String>();
		try {
			getConnection();

			String sql = "select distinct oderfoodname from oder where oderdate like ? and odermart = ?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, dayTime + "%");
			psmt.setString(2, martname);
			rs = psmt.executeQuery();

			while (rs.next()) {
				menuName.add(rs.getString(1));
			}

			for (int i = 0; i < menuName.size(); i++) {
				sql = "select oderfoodname from oder where oderfoodname = ?";
				psmt = con.prepareStatement(sql);
				psmt.setString(1, menuName.get(i));
				rs = psmt.executeQuery();
				while (rs.next()) {
					count++;
				}
				row1 = new Vector<>();
				row1.add(menuName.get(i));
				row1.addElement(count);
				list.add(row1);
				count = 0;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public Vector history(String userID, String monTime) { // 개인 히스토리 조회 <O>

		Vector data = new Vector<>();
		Vector row = null;

		try {
			getConnection();

			String sql = "select oderdate, odermart, username, oderfoodname, oderfoodprice from oder where oderdate like ? and userid = ?";
			psmt = con.prepareStatement(sql);
			psmt.setString(2, userID);

			// java.sql.Date sqlMonTime = new java.sql.Date(monTime.getTime());

			psmt.setString(1, monTime + "%");

			rs = psmt.executeQuery();

			while (rs.next()) {

				row = new Vector<>();
				for (int i = 1; i <= 4; i++) {
					row.add(rs.getString(i));
				}
				row.add(rs.getInt(5));

				data.add(row);
			}

			psmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return data;
	}

	public ArrayList<FinalVO> allSave() { // FinalVO >> 랜덤으로 유저 상태 갱신한 이후 모든

		// 데이터를 갇는 객체배열 반환
		cont = new Control();
		fvoList = new ArrayList<FinalVO>();

		try {
			getConnection();

			String sql = "select userid, username, userstate from member where userstate between 1 and 3";
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {

				fvo = new FinalVO();
				fvo.setUserID(rs.getString(1));
				fvo.setUserName(rs.getString(2));
				fvo.setUserState(rs.getInt(3));

				fvoList.add(fvo);
			}

			sql = "select oderdate, oderfoodname, oderfoodprice, odermart, userid from oder where oderdate = ?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, cont.getMt() + "%");

			rs = psmt.executeQuery();

			while (rs.next()) {
				for (int i = 0; i < fvoList.size(); i++) {
					if (rs.getString(5).equals(fvoList.get(i).getUserID())) {
						fvoList.get(i).setDate(rs.getString(1));
						fvoList.get(i).setFoodName(rs.getString(2));
						fvoList.get(i).setFoodPrice(rs.getInt(3));
						fvoList.get(i).setMart(rs.getString(4));
					}
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fvoList;
	}

	public ArrayList<FinalVO> getFVOList() {
		return fvoList;
	}

	public boolean checkLogin(String inputID, String inputPW) {
		String trueID = null;
		boolean checkID = false;
		boolean checkPW = false;

		try {
			getConnection();

			String sql = "select userid from member where userid= ?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, inputID);
			rs = psmt.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).equals(inputID)) {
					checkID = true;
				} else {
					return false;
				}
			}
			if (checkID) {
				sql = "select userpw from member where userid= ?";
				psmt = con.prepareStatement(sql);
				psmt.setString(1, inputID);
				rs = psmt.executeQuery();
				while (rs.next()) {
					if (rs.getString(1).equals(inputPW)) {
						checkPW = true;
					}
					if (checkPW) {
						return true;
					} else {
						return false;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public ArrayList<OrderList> getOrderArrayList() {

		return ol;
	}

	public boolean checkID(String inputID) { // 아이디 중복 확인 메소드
	      int checker = 0;
	      try {

	         getConnection();

	         String sql = "select userid from member ";
	         psmt = con.prepareStatement(sql);

	         rs = psmt.executeQuery(); // 테이블형태의 구조화된 클래스 타입을 반환
	         // rs.next(); // 맨 처음 가리키는 커서는 데이터를 저장해놓은 줄 바로 위에 위치(하드디스크 읽는 방법 비슷)

	         while (rs.next()) {
	            if(inputID.equals(rs.getString(1))) checker++;
	         }

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (rs != null)
	               rs.close();
	            if (psmt != null)
	               psmt.close();
	            if (con != null)
	               con.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }

	      if(checker == 0) return true;
	      else return false;
	}
}
