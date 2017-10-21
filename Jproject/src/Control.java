import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class Control {
	private DAO dao = new DAO();
	private Random ran = new Random();
	private int num1 = 0, num2 = 0; // ������ ����
	private LoginState ls = null;
	private Calendar cal = null;
	private int year, mon, day, hour, min, sec;

	public void randomSelect(String[] src) {
		/*
		 * String �迭 src(���� �ϳ��� ��ü�� �ֹ��� ������� �̸� �迭) �������� �� ����� �̾� �ֹ��ڷ� ������Ʈ ��������
		 * �� ����� �̾� �����ڷ� ������Ʈ
		 * 
		 */

		if (src.length >= 2) {

			while (num1 == num2) { // �ߺ����� �ʰ� ������ 2�� ��ȯ
				num1 = ran.nextInt(src.length);
				num2 = ran.nextInt(src.length);
			}

			dao.update(src[num1], 2); // num1 >> �ֹ���
			dao.update(src[num2], 3); // num2 >> ������

		}

	}

	// �н����� �˻��ϴ� �޼ҵ�
	public boolean isPasswordCorrect(char[] input, char[] inputCheck) {
		boolean isCorrect = true;
		char[] correctPassword = inputCheck;

		if (input.length != correctPassword.length) {
			isCorrect = false;
		} else {
			isCorrect = Arrays.equals(input, correctPassword);
		}

		// Zero out the password.
		Arrays.fill(correctPassword, '0');

		return isCorrect;
	}

	public boolean isPasswordCorrect(char[] input) {
		boolean isCorrect = true;
		char[] correctPassword = { 'p', 'w' };

		if (input.length != correctPassword.length) {
			isCorrect = false;
		} else {
			isCorrect = Arrays.equals(input, correctPassword);
		}

		// Zero out the password.
		Arrays.fill(correctPassword, '0');

		return isCorrect;
	}

	public LoginState getLs() {
		return ls;
	}

	public void setLs(LoginState ls) {
		this.ls = ls;
	}

	public void martSelect(ArrayList<OrderList> ol) { // ��ü�� ����

		String result_han = "";
		String result_sin = "";
		String result_mom = "";
		String result_nan = "";
		String result_gub = "";
		String result_joong = "";

		for (int i = 0; i < ol.size(); i++) { // ��ü�� �ֹ���

			String mart = ol.get(i).getMartName();

			if (mart.equals("�Ѽ�")) {
				result_han += ol.get(i).getName() + " ";
			} else if (mart.equals("����")) {
				result_sin += ol.get(i).getName() + " ";
			} else if (mart.equals("�߱�")) {
				result_joong += ol.get(i).getName() + " ";
			} else if (mart.equals("��")) {
				result_mom += ol.get(i).getName() + " ";
			} else if (mart.equals("��")) {
				result_gub += ol.get(i).getName() + " ";
			} else if (mart.equals("��Ÿ")) {
				result_nan += ol.get(i).getName() + " ";
			} else {

			}
		}

		String[] han = result_han.split(" ");
		String[] sin = result_sin.split(" ");
		String[] joong = result_joong.split(" ");
		String[] mom = result_mom.split(" ");
		String[] gub = result_gub.split(" ");
		String[] nan = result_nan.split(" ");

		randomSelect(han);
		randomSelect(sin);
		randomSelect(joong);
		randomSelect(gub);
		randomSelect(mom);
		randomSelect(nan);

	}

	public void timeChecker() { // ���� �ð� üũ

		cal = Calendar.getInstance();

		year = cal.get(Calendar.YEAR);
		mon = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DAY_OF_MONTH);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		min = cal.get(Calendar.MINUTE);
		sec = cal.get(Calendar.SECOND);

	}

	public String getNow() { // ���� �ð� ���ڿ� ��ȯ
		return year + "/" + mon + "/" + day + " " + hour + ":" + min + ":" + sec;
	}

	public String getMt() { // �ش� �� ��ȯ
		return year + "/" + mon;
	}

	public int getHour() {
		return hour;
	}
	
	public int getMin() {
		return min;
	}

}
