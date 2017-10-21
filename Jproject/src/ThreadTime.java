import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThreadTime extends Thread {

	private Control cont = new Control();
	private DAO dao = new DAO();
	private ArrayList<OrderList> ol = null;
	private ArrayList<FinalVO> fvoList = null;
	
	private SimpleDateFormat transFormat = null;
	
	private int hour, min;
	
	Calendar cal = null;
	private int state = 0;
	private int state1 = 0;

	public void run() {
		// 5초 단위로 현 시각을 체크
		
		while (true) {
			cont.timeChecker();
			
			hour = cont.getHour();
			min = cont.getMin();
			
			// 12시가 되고 바꾼 기록이 없다면 실행되는 조건
	         if (hour == 12 && state == 0) {
	            ol = dao.getOrderArrayList();
	            cont.martSelect(ol);
	            state = 1;
	         }
	         
	         // 12시 1분이 되면 finalVO 를 얻는 메소드 호출
	         if(hour == 12 && min == 1 && state1 == 0) {
	            this.fvoList = dao.allSave();
	         }

	         // 바꾼 기록이 있고 12시가 아니면 바꾼기록을 초기화
	         if ((state == 1 && state1 == 1) && hour != 12) {
	            state1 = 0;
	            state = 0;
	            dao.resetState();
	         }
	         
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				return;
			}
		}

	}

}
