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

	// DB ����
	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection(url, user, pw);
	}

	public void update(String string, int num) {// �ֹ���, ������ �����ؼ� member���̺��� ���¸�
												// ������Ʈ

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

	public void userInsert(String userID, String password, String name, String phoneNum) {// ȸ�����Խ�
																							// ��������
																							// ����.

		try {

			getConnection();

			// DB�� �����Է�
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
		} finally {// ���α׷� ����� ������ ���� ������ ������ �ڵ带 �Է�
			try {// ���α׷��� �ݴٰ� ������ ���� �����Ƿ� try/catch�� ������
					// ���� ���� ������ ���α׷��� �ݴ�� �ݾ��־�� �Ѵ�.
					// ex> con���� psmt�������� ���� psmt�ݰ� con�ݴ´�.

				// nullPointException ó��
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

	public String getPhoneNum(int num) {// ���°� num�� ����� ��ȣ ���

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

	public void resetState() { // ���� 0 �ʱ�ȭ

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

	public void insertOrder(OrderList ol) { // �ֹ��� �ֹ����� ������ �޴������� DB�� ����
		/*
		 * oder ���̺� �ֹ����� ���� member ���̺� �����ؼ� userstate�� 1�� �ٲ�
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
		} finally {// ���α׷� ����� ������ ���� ������ ������ �ڵ带 �Է�
			try {// ���α׷��� �ݴٰ� ������ ���� �����Ƿ� try/catch�� ������
					// ���� ���� ������ ���α׷��� �ݴ�� �ݾ��־�� �Ѵ�.
					// ex> con���� psmt�������� ���� psmt�ݰ� con�ݴ´�.

				// nullPointException ó��
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

	public LoginState start(String userID) {// �α��������� �̸��� ���̵� ������ ����

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

	public Vector todayOrderList(String martname, String dayTime) {// ������ �ֹ���
																	// �޴��̸��� �޴�
																	// ���� ��ȭ (O)
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

	public Vector history(String userID, String monTime) { // ���� �����丮 ��ȸ <O>

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

	public ArrayList<FinalVO> allSave() { // FinalVO >> �������� ���� ���� ������ ���� ���

		// �����͸� ���� ��ü�迭 ��ȯ
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

	public boolean checkID(String inputID) { // ���̵� �ߺ� Ȯ�� �޼ҵ�
	      int checker = 0;
	      try {

	         getConnection();

	         String sql = "select userid from member ";
	         psmt = con.prepareStatement(sql);

	         rs = psmt.executeQuery(); // ���̺������� ����ȭ�� Ŭ���� Ÿ���� ��ȯ
	         // rs.next(); // �� ó�� ����Ű�� Ŀ���� �����͸� �����س��� �� �ٷ� ���� ��ġ(�ϵ��ũ �д� ��� ���)

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
