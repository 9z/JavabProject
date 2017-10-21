import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class Control {
	private DAO dao = new DAO();
	private Random ran = new Random();
	private int num1 = 0, num2 = 0; // 랜덤수 저장
	private LoginState ls = null;
	private Calendar cal = null;
	private int year, mon, day, hour, min, sec;

	public void randomSelect(String[] src) {
		/*
		 * String 배열 src(당일 하나의 업체에 주문한 사람들의 이름 배열) 랜덤으로 한 사람을 뽑아 주문자로 업데이트 랜덤으로
		 * 한 사람을 뽑아 결제자로 업데이트
		 * 
		 */

		if (src.length >= 2) {

			while (num1 == num2) { // 중복되지 않게 랜덤수 2개 반환
				num1 = ran.nextInt(src.length);
				num2 = ran.nextInt(src.length);
			}

			dao.update(src[num1], 2); // num1 >> 주문자
			dao.update(src[num2], 3); // num2 >> 결제자

		}

	}

	// 패스워드 검사하는 메소드
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

	public void martSelect(ArrayList<OrderList> ol) { // 업체별 랜덤

		String result_han = "";
		String result_sin = "";
		String result_mom = "";
		String result_nan = "";
		String result_gub = "";
		String result_joong = "";

		for (int i = 0; i < ol.size(); i++) { // 업체별 주문자

			String mart = ol.get(i).getMartName();

			if (mart.equals("한솥")) {
				result_han += ol.get(i).getName() + " ";
			} else if (mart.equals("신전")) {
				result_sin += ol.get(i).getName() + " ";
			} else if (mart.equals("중국")) {
				result_joong += ol.get(i).getName() + " ";
			} else if (mart.equals("맘")) {
				result_mom += ol.get(i).getName() + " ";
			} else if (mart.equals("굽")) {
				result_gub += ol.get(i).getName() + " ";
			} else if (mart.equals("난타")) {
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

	public void timeChecker() { // 현재 시간 체크

		cal = Calendar.getInstance();

		year = cal.get(Calendar.YEAR);
		mon = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DAY_OF_MONTH);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		min = cal.get(Calendar.MINUTE);
		sec = cal.get(Calendar.SECOND);

	}

	public String getNow() { // 현재 시간 문자열 반환
		return year + "/" + mon + "/" + day + " " + hour + ":" + min + ":" + sec;
	}

	public String getMt() { // 해당 월 반환
		return year + "/" + mon;
	}

	public int getHour() {
		return hour;
	}
	
	public int getMin() {
		return min;
	}

}
